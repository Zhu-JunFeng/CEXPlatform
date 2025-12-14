package com.cexpay.exchange.controller;

import com.cexpay.common.model.ApiResponse;
import com.cexpay.exchange.model.OrderMessage;
import com.cexpay.exchange.rocketmq.OrderProducer;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange/api/order")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/add")
    public ApiResponse<Object> placeOrder(@RequestBody OrderMessage order) {
        order.setTimestamp(System.currentTimeMillis());
        Object msgId = orderProducer.sendOrder(order);
        return ApiResponse.success(msgId);
    }
}