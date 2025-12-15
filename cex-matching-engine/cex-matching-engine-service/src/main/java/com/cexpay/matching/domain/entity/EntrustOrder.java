package com.cexpay.matching.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 委托订单信息
 *
 * @TableName entrust_order
 */
@TableName(value = "entrust_order")
@Data
public class EntrustOrder {
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 市场ID
     */
    private Long marketId;

    /**
     * 市场类型（1：币币交易，2：创新交易）
     */
    private Integer marketType;

    /**
     * 交易对标识符
     */
    private String symbol;

    /**
     * 交易市场
     */
    private String marketName;

    /**
     * 委托价格
     */
    private BigDecimal price;

    /**
     * 合并深度价格1
     */
    private BigDecimal mergeLowPrice;

    /**
     * 合并深度价格2
     */
    private BigDecimal mergeHighPrice;

    /**
     * 委托数量
     */
    private BigDecimal volume;

    /**
     * 委托总额
     */
    private BigDecimal amount;

    /**
     * 手续费比率
     */
    private BigDecimal feeRate;

    /**
     * 交易手续费
     */
    private BigDecimal fee;

    /**
     * 合约单位
     */
    private Integer contractUnit;

    /**
     * 成交数量
     */
    private BigDecimal deal;

    /**
     * 冻结量
     */
    private BigDecimal freeze;

    /**
     * 保证金比例
     */
    private BigDecimal marginRate;

    /**
     * 基础货币对（USDT/BTC）兑换率
     */
    private BigDecimal baseCoinRate;

    /**
     * 报价货币对（USDT/BTC)兑换率
     */
    private BigDecimal priceCoinRate;

    /**
     * 占用保证金
     */
    private BigDecimal lockMargin;

    /**
     * 价格类型：1-市价；2-限价
     */
    private Integer priceType;

    /**
     * 交易类型：1-开仓；2-平仓
     */
    private Integer tradeType;

    /**
     * 买卖类型：1-买入；2-卖出
     */
    private Integer type;

    /**
     * 平仓委托关联单号
     */
    private Long openOrderId;

    /**
     * status
     * 0未成交
     * 1已成交
     * 2已取消
     * 4异常单
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}