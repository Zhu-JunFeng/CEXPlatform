package com.cexpay.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 创建订单请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    
    /**
     * 交易对
     */
    private String symbol;
    
    /**
     * 订单类型：1-限价买入 2-限价卖出 3-市价买入 4-市价卖出
     */
    private Integer orderType;
    
    /**
     * 订单价格（市价单可不填）
     */
    private BigDecimal price;
    
    /**
     * 订单数量
     */
    private BigDecimal quantity;
}
