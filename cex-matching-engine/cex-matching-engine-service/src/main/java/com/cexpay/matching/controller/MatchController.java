package com.cexpay.matching.controller;

import cn.hutool.core.util.StrUtil;
import com.cexpay.common.model.ApiResponse;
import com.cexpay.matching.disruptor.OrderEvent;
import com.cexpay.matching.disruptor.OrderEventHandler;
import com.cexpay.matching.domain.MergeOrder;
import com.cexpay.matching.domain.OrderBooks;
import com.cexpay.matching.enums.OrderDirection;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private EventHandler<OrderEvent>[] eventHandlers;

    @GetMapping("/order/{symbol}/{direction}")
    public ApiResponse<Map<BigDecimal, MergeOrder>> getTradeData(@PathVariable String symbol, @PathVariable Integer direction) {
        for (EventHandler<OrderEvent> eventHandler : eventHandlers) {
            OrderEventHandler orderEventHandler = (OrderEventHandler) eventHandler;
            if (StrUtil.equals(symbol, orderEventHandler.getSymbol())) {
                OrderBooks orderBooks = orderEventHandler.getOrderBooks();
                Map<BigDecimal, MergeOrder> currentLimitPrices = orderBooks.getCurrentLimitPrices(OrderDirection.getByCode(direction));
                return ApiResponse.success(currentLimitPrices);
            }
        }
        return ApiResponse.fail("未查询到订单数据");
    }

}
