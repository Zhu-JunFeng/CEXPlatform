package com.cexpay.matching.service;

import com.cexpay.matching.core.Matcher;
import com.cexpay.matching.core.OrderBook;
import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.Trade;

import java.util.List;

public class MatchingEngineService {

    private final OrderBookManager bookManager;
    private final Matcher matcher;

    public MatchingEngineService(OrderBookManager bookManager) {
        this.bookManager = bookManager;
        this.matcher = new Matcher();
    }

    /**
     * Entry point for new orders - in production this would be called from a Kafka consumer
     */
    public List<Trade> submitOrder(Order order) {
        OrderBook book = bookManager.getOrCreate(order.getSymbol());
        synchronized (book) {
            List<Trade> trades = matcher.match(book, order);
            // publish trades, persist, etc -- hooks for integration
            trades.forEach(t -> System.out.println("TRADE: " + t));
            System.out.println("OrderBook after match: " + book);
            return trades;
        }
    }

    public boolean cancelOrder(String symbol, long orderId) {
        OrderBook book = bookManager.get(symbol);
        if (book == null) return false;
        Order ord = book.findOrder(orderId);
        if (ord == null) return false;
        synchronized (book) {
            boolean removed = book.removeOrder(ord);
            if (removed) System.out.println("Canceled: " + ord);
            return removed;
        }
    }
}

