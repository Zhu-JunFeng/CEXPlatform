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
public class Order {

    /**
     * BUY → 买入
     * SELL → 卖出
     */
    public enum Side {BUY, SELL}

    /**
     * LIMIT → 限价单，有价格。
     * MARKET → 市价单，按市场最优价成交，price 可为 null。
     */
    public enum Type {LIMIT, MARKET}

    /**
     * NEW: 新订单
     * PARTIAL: 部分成交
     * FILLED: 全部成交
     * CANCELED: 撤销
     */
    public enum Status {NEW, PARTIAL, FILLED, CANCELED}

    private long orderId;

    private long userId;

    /**
     * 交易对，如 "BTCUSDT"
     */
    private String symbol;

    private Side side;

    private Type type;

    /**
     * 订单价格，市价单可为 null
     */
    private BigDecimal price;

    /**
     * 订单数量
     */
    private BigDecimal quantity;

    /**
     * 已成交数量
     */
    private BigDecimal filledQuantity;

    /**
     * 订单状态
     */
    private Status status;

    /**
     * 订单创建时间戳
     */
    private long timestamp;

    /**
     * 剩余数量
     *
     * @return 剩余数量
     */
    public BigDecimal remaining() {
        return quantity.subtract(filledQuantity);
    }

    /**
     * 是否有剩余数量
     *
     * @return 是否有剩余数量
     */
    public boolean hasRemaining() {
        return remaining().compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 成交指定数量
     *
     * @param amount 成交数量
     */
    public void fill(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) return;
        filledQuantity = filledQuantity.add(amount);
        if (filledQuantity.compareTo(quantity) >= 0) {
            status = Status.FILLED;
            filledQuantity = quantity;
        } else {
            status = Status.PARTIAL;
        }
    }

    /**
     * 撤销订单
     */
    public void cancel() {
        status = Status.CANCELED;
    }
}
