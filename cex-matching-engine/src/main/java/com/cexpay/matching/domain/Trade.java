package com.cexpay.matching.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    /**
     * 成交记录唯一ID
     */
    private  long tradeId;

    /**
     * 交易对，如 "BTCUSDT"
     */
    private  String symbol;

    /**
     * 买方订单ID
     */
    private  long buyOrderId;

    /**
     * 卖方订单ID
     */
    private  long sellOrderId;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 成交数量
     */
    private  BigDecimal quantity;

    /**
     * 成交时间戳，单位毫秒
     */
    private  long timestamp;
}
