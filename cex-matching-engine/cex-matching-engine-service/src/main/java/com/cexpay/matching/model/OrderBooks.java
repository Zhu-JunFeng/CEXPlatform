package com.cexpay.matching.model;

import cn.hutool.core.util.StrUtil;
import com.cexpay.common.enums.OrderDirection;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 没个交易对一个OrderBooks
 */
@Slf4j
@Data
public class OrderBooks {

    /**
     * 买入的现价交易，价格从高到低
     * MergeOrder 价格相同订单 ，按照时间排序
     */
    private TreeMap<BigDecimal, MergeOrder> buyLimitPrice;

    /**
     * 卖出的现价交易，价格从低到高
     * MergeOrder 价格相同订单 ，按照时间排序
     */
    private TreeMap<BigDecimal, MergeOrder> sellLimitPrice;

    /**
     * 交易的币种
     */
    private String symbol;

    /**
     * 交易币种的精度
     */
    private int coinScale;

    /**
     * 基币的精度
     */
    private int baseCoinScale;

    /**
     * 买方的盘口数据
     */
    private TradePlate buyTradePlate;

    /**
     * 卖方的盘口数据
     */
    private TradePlate sellTradePlate;

    private SimpleDateFormat dateFormat;

    public OrderBooks(String symbol) {
        this(symbol, 4, 4);
    }

    public OrderBooks(String symbol, int coinScale, int baseCoinScale) {
        this.symbol = symbol;
        this.coinScale = coinScale;
        this.baseCoinScale = baseCoinScale;
        initialize();
    }


    /**
     * 初始化队列
     */
    public void initialize() {
        log.info("Initializing OrderBooks symbol = {}", symbol);
        buyLimitPrice = new TreeMap<>(Comparator.reverseOrder()); // 价格从大到小
        sellLimitPrice = new TreeMap<>(Comparator.naturalOrder()); // 价格从小到大
        buyTradePlate = new TradePlate(OrderDirection.BUY, symbol); // 买方盘口数据
        sellTradePlate = new TradePlate(OrderDirection.SELL, symbol); // 卖方盘口数据
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 获取但前的交易队列
     */
    public Map<BigDecimal, MergeOrder> getCurrentLimitPrices(OrderDirection direction) {
        return direction == OrderDirection.BUY ? buyLimitPrice : sellLimitPrice;
    }

    /**
     * 获取迭代器
     */
    public Iterator<Map.Entry<BigDecimal, MergeOrder>> getCurrentLimitPricesIterator(OrderDirection direction) {
        return getCurrentLimitPrices(direction).entrySet().iterator();
    }

    /**
     * 添加订单
     */
    public void addOrder(Order order) {
        OrderDirection orderDirection = order.getOrderDirection();
        Map<BigDecimal, MergeOrder> currentLimitPrices = getCurrentLimitPrices(orderDirection);
        MergeOrder mergeOrder = currentLimitPrices.get(order.getPrice());
        if (mergeOrder == null) {
            mergeOrder = new MergeOrder();
            currentLimitPrices.put(order.getPrice(), mergeOrder);
        }
        mergeOrder.addOrder(order);
    }

    /**
     * 取消订单
     */
    public void cancelOrder(Order order) {
        OrderDirection orderDirection = order.getOrderDirection();
        Map<BigDecimal, MergeOrder> currentLimitPrices = getCurrentLimitPrices(orderDirection);
        MergeOrder mergeOrder = currentLimitPrices.get(order.getPrice());
        if (mergeOrder != null) {
            Iterator<Order> iterator = mergeOrder.iterator();
            while (iterator.hasNext()) {
                Order next = iterator.next();
                if (StrUtil.equals(next.getOrderId(), order.getOrderId())) {
                    iterator.remove();
                }
            }
            if (mergeOrder.size() == 0) {
                currentLimitPrices.remove(order.getPrice());
            }
        }
    }

    /**
     * 获取排在队列的第一数据
     */
    public Map.Entry<BigDecimal, MergeOrder> getBastSuitOrder(OrderDirection direction) {
        return direction == OrderDirection.BUY ? buyLimitPrice.firstEntry() : sellLimitPrice.firstEntry();
    }

}
