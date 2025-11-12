package com.cexpay.order.service;

import com.cexpay.order.domain.Order;
import com.cexpay.order.dto.CreateOrderRequest;

import java.util.List;

/**
 * 订单业务接口
 */
public interface IOrderService {
    
    /**
     * 创建订单
     */
    Order createOrder(Long userId, CreateOrderRequest request);
    
    /**
     * 撤销订单
     */
    void cancelOrder(Long orderId);
    
    /**
     * 获取用户订单列表
     */
    List<Order> getUserOrders(Long userId);
    
    /**
     * 获取订单详情
     */
    Order getOrderDetail(Long orderId);
}
