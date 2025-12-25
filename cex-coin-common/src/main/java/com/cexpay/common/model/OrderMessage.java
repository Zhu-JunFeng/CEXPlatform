package com.cexpay.common.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderMessage implements Serializable {

    /**
     * 委托单id
     */
    private String orderId;

    /**
     * 用户/会员id
     */
    private String userId;

    /**
     * 支持的币币交易对
     */
    private String symbol;

    /**
     * 买入或卖出数量
     */
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 成交量
     */
    private BigDecimal tradedAmount = BigDecimal.ZERO;

    /**
     * 成交额
     */
    private BigDecimal turnover = BigDecimal.ZERO;

    /**
     * 币单位
     */
    private String coinSymbol;

    /**
     * 结算单位
     */
    private String baseSymbol;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 订单方向
     */
    private Integer orderDirection;

    /**
     * 挂单价格
     */
    private BigDecimal price = BigDecimal.ZERO;

    private long timestamp;
}