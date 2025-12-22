package com.cexpay.common.model;

import com.cexpay.common.enums.OrderDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * 交易的盘口数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradePlate {

    /**
     * 盘口数据详情
     */
    private LinkedList<TradePlateItem> items;

    /**
     * 最大支持的深度
     */
    private int maxDepth = 100;

    /**
     * 订单的方向 买入盘口/卖出盘口
     */
    private OrderDirection direction;

    /**
     * 交易对
     */
    private String symbol;

    public TradePlate(OrderDirection direction, String symbol) {
        this.direction = direction;
        this.symbol = symbol;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TradePlateItem {

        /**
         * 交易的价格
         */
        private BigDecimal price;

        /**
         * 交易的数量
         */
        private BigDecimal amount;
    }

}
