package com.cexpay.matching.service;

import com.cexpay.matching.core.OrderBook;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderBookManager {

    private final Map<String, OrderBook> books = new ConcurrentHashMap<>();

    public OrderBook getOrCreate(String symbol) {
        return books.computeIfAbsent(symbol, OrderBook::new);
    }

    public OrderBook get(String symbol) { return books.get(symbol); }

}
