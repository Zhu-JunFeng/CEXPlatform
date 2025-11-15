package com.cexpay.matching.service;

import com.cexpay.matching.core.OrderBook;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理多交易对的 OrderBook，线程安全
 */
@Component
public class OrderBookManager {

    private final Map<String, OrderBook> books = new ConcurrentHashMap<>();

    public OrderBook getOrCreate(String symbol) {
        return books.computeIfAbsent(symbol, OrderBook::new);
    }

    public OrderBook get(String symbol) { return books.get(symbol); }

}
