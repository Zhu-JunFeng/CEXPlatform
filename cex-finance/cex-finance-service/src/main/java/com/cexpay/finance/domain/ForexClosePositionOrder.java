package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 平仓详情
 * @TableName forex_close_position_order
 */
@TableName(value ="forex_close_position_order")
@Data
public class ForexClosePositionOrder {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 交易对ID
     */
    private Long marketId;

    /**
     * 交易对名称
     */
    private String marketName;

    /**
     * 持仓方向：1-买；2-卖
     */
    private Integer type;

    /**
     * 资金账户ID
     */
    private Long accountId;

    /**
     * 委托订单号
     */
    private Long entrustOrderId;

    /**
     * 成交订单号
     */
    private Long orderId;

    /**
     * 成交价
     */
    private BigDecimal price;

    /**
     * 成交数量
     */
    private BigDecimal num;

    /**
     * 关联开仓订单号
     */
    private Long openId;

    /**
     * 平仓盈亏
     */
    private BigDecimal profit;

    /**
     * 返回还保证金
     */
    private BigDecimal unlockMargin;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}