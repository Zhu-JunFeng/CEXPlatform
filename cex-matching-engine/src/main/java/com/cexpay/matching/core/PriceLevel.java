package com.cexpay.matching.core;

import com.cexpay.matching.domain.Order;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class PriceLevel {
    private final BigDecimal price;
    private final Deque<Order> orders = new ArrayDeque<>();

    public PriceLevel(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void addOrder(Order order) {
        orders.addLast(order);
    }

    public Order peek() {
        return orders.peekFirst();
    }

    public Order poll() {
        return orders.pollFirst();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public int size() {
        return orders.size();
    }

    public boolean remove(Order order) {
        // linear search in deque - acceptable per-level
        return orders.remove(order);
    }

    public Iterator<Order> iterator() {
        return orders.iterator();
    }
}