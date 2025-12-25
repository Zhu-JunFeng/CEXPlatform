package com.cexpay.common.model;

import lombok.Data;

import com.cexpay.common.enums.OrderDirection;

import java.math.BigDecimal;

/**
 * 成交纪录，只要成交一次，就产生一个记录
 */
@Data
public class ExchangeTrade {

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 订单方向
     */
    private OrderDirection direction;

    /**
     * 本次交易的价格
     */
    private BigDecimal price;

    /**
     * 本次交易的数量
     */
    private BigDecimal amount;

    /**
     * 本次交易买方id
     */
    private String buyOrderId;

    /**
     * 本次交易卖方id
     */
    private String sellOrderId;

    /**
     * 买方的成交额
     */
    private BigDecimal buyTurnOver;

    /**
     * 卖方的成交额
     */
    private BigDecimal sellTurnOver;

    /**
     * 成交时间
     */
    private Long time;
}
