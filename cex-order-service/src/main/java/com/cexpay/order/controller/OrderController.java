package com.cexpay.order.controller;

import com.cexpay.common.dto.ApiResponse;
import com.cexpay.order.domain.Order;
import com.cexpay.order.dto.CreateOrderRequest;
import com.cexpay.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@Tag(name = "Order Management", description = "订单管理相关接口")
public class OrderController {
    
    @Autowired
    private IOrderService orderService;
    
    /**
     * 创建订单
     */
    @PostMapping("/create")
    @Operation(summary = "创建订单", description = "创建新的交易订单")
    public ApiResponse<Order> createOrder(@RequestParam Long userId, @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(userId, request);
        return ApiResponse.success("订单创建成功", order);
    }
    
    /**
     * 撤销订单
     */
    @PostMapping("/cancel/{orderId}")
    @Operation(summary = "撤销订单", description = "撤销未成交的订单")
    public ApiResponse<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ApiResponse.success("订单已撤销", null);
    }
    
    /**
     * 获取用户订单列表
     */
    @GetMapping("/list/{userId}")
    @Operation(summary = "获取订单列表", description = "获取用户的所有订单")
    public ApiResponse<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ApiResponse.success(orders);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/{orderId}")
    @Operation(summary = "获取订单详情", description = "获取单个订单的详细信息")
    public ApiResponse<Order> getOrderDetail(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetail(orderId);
        return ApiResponse.success(order);
    }
}
