package com.cexpay.matching.core;

import com.cexpay.matching.domain.Order;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderBook {

    private final String symbol;

    private final NavigableMap<BigDecimal, PriceLevel> bids = new TreeMap<>(Comparator.reverseOrder());

    private final NavigableMap<BigDecimal, PriceLevel> asks = new TreeMap<>();

    // global quick lookup
    private final Map<Long, Order> orderIndex = new ConcurrentHashMap<>();

    public OrderBook(String symbol) { this.symbol = symbol; }
    public String getSymbol() { return symbol; }

    public synchronized void addOrder(Order order) {
        Map<BigDecimal, PriceLevel> book = order.getSide() == Order.Side.BUY ? bids : asks;
        BigDecimal price = order.getPrice();
        PriceLevel level = book.computeIfAbsent(price, PriceLevel::new);
        level.addOrder(order);
        orderIndex.put(order.getOrderId(), order);
    }

    public synchronized boolean removeOrder(Order order) {
        Map<BigDecimal, PriceLevel> book = order.getSide() == Order.Side.BUY ? bids : asks;
        PriceLevel level = book.get(order.getPrice());
        if (level == null) return false;
        boolean removed = level.remove(order);
        if (removed) {
            order.cancel();
            orderIndex.remove(order.getOrderId());
            if (level.isEmpty()) book.remove(order.getPrice());
        }
        return removed;
    }

    public Optional<Order> peekBestBid() {
        Map.Entry<BigDecimal, PriceLevel> e = bids.firstEntry();
        if (e == null) return Optional.empty();
        return Optional.ofNullable(e.getValue().peek());
    }

    public Optional<Order> peekBestAsk() {
        Map.Entry<BigDecimal, PriceLevel> e = asks.firstEntry();
        if (e == null) return Optional.empty();
        return Optional.ofNullable(e.getValue().peek());
    }

    public NavigableMap<BigDecimal, PriceLevel> getBids() { return bids; }
    public NavigableMap<BigDecimal, PriceLevel> getAsks() { return asks; }

    public Order findOrder(long orderId) { return orderIndex.get(orderId); }

    public synchronized void addOrderDirect(Order order) { addOrder(order); }

    @Override
    public String toString() {
        return "OrderBook{" + "symbol='" + symbol + '\'' +
                ", bids=" + snapshotLevels(bids, 5) +
                ", asks=" + snapshotLevels(asks, 5) + '}';
    }

    private String snapshotLevels(NavigableMap<BigDecimal, PriceLevel> map, int depth) {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        for (Map.Entry<BigDecimal, PriceLevel> e : map.entrySet()) {
            if (i++ >= depth) break;
            sb.append("(").append(e.getKey()).append(":").append(e.getValue().size()).append("),");
        }
        sb.append("]");
        return sb.toString();
    }
}