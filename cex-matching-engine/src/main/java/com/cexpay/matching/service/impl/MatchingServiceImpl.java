package com.cexpay.matching.service.impl;

import com.cexpay.matching.core.Matcher;
import com.cexpay.matching.core.OrderBook;
import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.Trade;
import com.cexpay.matching.service.IMatchingService;
import com.cexpay.matching.service.OrderBookManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 撮合引擎实现（内存撮合，基于 OrderBookManager）
 */
@Slf4j
@Service
public class MatchingServiceImpl implements IMatchingService {

    private final Matcher matcher = new Matcher();
    private final AtomicLong orderIdSeq = new AtomicLong(1);

    @Autowired
    private OrderBookManager orderBookManager;

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void matchOrder(Long orderId, String symbol) {
        log.info("触发已有订单撮合: orderId={}, symbol={}", orderId, symbol);
        OrderBook book = orderBookManager.get(symbol);
        if (book == null) {
            log.warn("未找到订单簿: {}", symbol);
            return;
        }

        Order order = book.findOrder(orderId);
        if (order == null) {
            log.warn("未找到订单 id={} 在 {} 中", orderId, symbol);
            return;
        }

        List<Trade> trades;
        synchronized (book) {
            trades = matcher.match(book, order);
        }

        publishTrades(trades);
    }

    @Override
    public List<Trade> submitOrder(Order order) {
        // 参数校验与初始化
        Objects.requireNonNull(order, "order must not be null");

        if (order.getQuantity() == null || order.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        if (order.getSymbol() == null || order.getSymbol().isBlank()) {
            throw new IllegalArgumentException("symbol required");
        }
        if (order.getSide() == null) throw new IllegalArgumentException("side required");
        if (order.getType() == null) order.setType(Order.Type.LIMIT);

        if (order.getOrderId() == 0L) {
            order.setOrderId(orderIdSeq.getAndIncrement());
        }
        if (order.getTimestamp() == 0L) {
            order.setTimestamp(System.currentTimeMillis());
        }
        if (order.getFilledQuantity() == null) order.setFilledQuantity(BigDecimal.ZERO);
        order.setStatus(Order.Status.NEW);

        OrderBook book = orderBookManager.getOrCreate(order.getSymbol());

        List<Trade> trades;
        synchronized (book) {
            trades = matcher.match(book, order);
            // 如果撮合后仍有剩余且为限价单，matcher 已经将其加入到book
        }

        publishTrades(trades);
        log.info("提交订单完成: id={}, symbol={}, side={}, type={}, qty={}, trades={}",
                order.getOrderId(), order.getSymbol(), order.getSide(), order.getType(), order.getQuantity(), trades.size());
        return trades;
    }

    @Override
    public boolean cancelOrder(Long orderId, String symbol) {
        if (symbol == null) return false;
        OrderBook book = orderBookManager.get(symbol);
        if (book == null) return false;
        Order order = book.findOrder(orderId);
        if (order == null) return false;
        synchronized (book) {
            return book.removeOrder(order);
        }
    }

    @Override
    public Object getOrderBook(String symbol) {
        OrderBook book = orderBookManager.get(symbol);
        if (book == null) return Collections.emptyMap();

        // 返回深度快照（前 10 档）
        Map<String, Object> snapshot = new HashMap<>();

        snapshot.put("symbol", symbol);
        snapshot.put("bids", snapshotLevels(book.getBids(), 10));
        snapshot.put("asks", snapshotLevels(book.getAsks(), 10));
        return snapshot;
    }

    private List<Map<String, Object>> snapshotLevels(NavigableMap<java.math.BigDecimal, com.cexpay.matching.core.PriceLevel> map, int depth) {
        List<Map<String, Object>> levels = new ArrayList<>();
        int i = 0;
        for (Map.Entry<java.math.BigDecimal, com.cexpay.matching.core.PriceLevel> e : map.entrySet()) {
            if (i++ >= depth) break;
            Map<String, Object> m = new HashMap<>();
            m.put("price", e.getKey());
            m.put("orders", e.getValue().size());
            // 可扩展：计算该价位总量
            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            for (java.util.Iterator<com.cexpay.matching.domain.Order> it = e.getValue().iterator(); it.hasNext(); ) {
                com.cexpay.matching.domain.Order o = it.next();
                total = total.add(o.getQuantity().subtract(o.getFilledQuantity() == null ? java.math.BigDecimal.ZERO : o.getFilledQuantity()));
            }
            m.put("totalQuantity", total);
            levels.add(m);
        }
        return levels;
    }

    private void publishTrades(List<Trade> trades) {
        if (trades == null || trades.isEmpty()) return;
        if (kafkaTemplate == null) {
            log.debug("KafkaTemplate not configured, skip publishing trades, count={}", trades.size());
            return;
        }
        for (Trade t : trades) {
            try {
                kafkaTemplate.send("matching.trades", t.getSymbol(), t);
            } catch (Exception e) {
                log.error("Failed to publish trade to kafka: {}", e.getMessage(), e);
            }
        }
    }
}
