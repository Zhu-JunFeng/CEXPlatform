package com.cexpay.matching.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成交记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeLog {
    
    /**
     * 交易ID
     */
    private Long tradeId;
    
    /**
     * 买方订单ID
     */
    private Long buyOrderId;
    
    /**
     * 卖方订单ID
     */
    private Long sellOrderId;
    
    /**
     * 交易对
     */
    private String symbol;
    
    /**
     * 成交价格
     */
    private BigDecimal price;
    
    /**
     * 成交数量
     */
    private BigDecimal quantity;
    
    /**
     * 成交时间
     */
    private LocalDateTime tradeTime;
}
