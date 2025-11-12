package com.cexpay.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * K线数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KLineData {
    
    /**
     * 交易对
     */
    private String symbol;
    
    /**
     * 时间周期（1m, 5m, 15m, 1h等）
     */
    private String period;
    
    /**
     * 开盘价
     */
    private BigDecimal open;
    
    /**
     * 最高价
     */
    private BigDecimal high;
    
    /**
     * 最低价
     */
    private BigDecimal low;
    
    /**
     * 收盘价
     */
    private BigDecimal close;
    
    /**
     * 成交量
     */
    private BigDecimal volume;
    
    /**
     * 时间戳
     */
    private Long timestamp;
}
