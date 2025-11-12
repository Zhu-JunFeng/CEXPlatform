package com.cexpay.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cexpay.common.constant.ErrorCode;
import com.cexpay.common.exception.BusinessException;
import com.cexpay.order.domain.Order;
import com.cexpay.order.dto.CreateOrderRequest;
import com.cexpay.order.mapper.OrderMapper;
import com.cexpay.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单业务实现
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Override
    @Transactional
    public Order createOrder(Long userId, CreateOrderRequest request) {
        // 参数验证
        if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "订单数量必须大于0");
        }
        
        if ((request.getOrderType() == 1 || request.getOrderType() == 2) && 
            (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "限价单价格必须大于0");
        }
        
        Order order = Order.builder()
                .userId(userId)
                .symbol(request.getSymbol())
                .orderType(request.getOrderType())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .filledQuantity(BigDecimal.ZERO)
                .status(0)
                .fee(BigDecimal.ZERO)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        
        orderMapper.insert(order);
        log.info("订单创建成功: userId={}, symbol={}, quantity={}", userId, request.getSymbol(), request.getQuantity());
        
        return order;
    }
    
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "订单不存在");
        }
        
        if (order.getStatus() == 2 || order.getStatus() == 3) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "订单已成交或已撤销，无法撤销");
        }
        
        order.setStatus(3);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);
        
        log.info("订单已撤销: orderId={}", orderId);
    }
    
    @Override
    public List<Order> getUserOrders(Long userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("create_time");
        return orderMapper.selectList(queryWrapper);
    }
    
    @Override
    public Order getOrderDetail(Long orderId) {
        return orderMapper.selectById(orderId);
    }
}
