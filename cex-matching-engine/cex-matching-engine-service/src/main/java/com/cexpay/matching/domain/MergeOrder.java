package com.cexpay.matching.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 相同价格的订单，按照时间升序
 */
@Data
public class MergeOrder {

    private LinkedList<Order> orders = new LinkedList<>();

    // 添加时 加到最后
    public void addOrder(Order order) {
        orders.addLast(order);
    }

    // 取的时候 从头取
    public Order get() {
        return orders.get(0);
    }

    // 获取MergeOrder的总金额
    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Order order : orders) {
            totalAmount = totalAmount.add(order.getAmount());
        }
        return totalAmount;
    }

    public Iterator<Order> iterator() {
        return orders.iterator();
    }

    public int size() {
        return orders.size();
    }

}
