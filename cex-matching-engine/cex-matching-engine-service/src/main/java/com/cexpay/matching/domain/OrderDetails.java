package com.cexpay.matching.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 已成交的订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 成交数量
     */
    private BigDecimal amount;

    /**
     * 成交额
     */
    private BigDecimal turnover;

    /**
     * 费率
     */
    private BigDecimal fee;

    /**
     * 成功时间
     */
    private Long dealTime;

}
