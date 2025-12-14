package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 创新交易持仓信息
 * @TableName forex_account
 */
@TableName(value ="forex_account")
@Data
public class ForexAccount {
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
     * 交易对
     */
    private String marketName;

    /**
     * 持仓方向：1-买；2-卖
     */
    private Integer type;

    /**
     * 持仓量
     */
    private BigDecimal amount;

    /**
     * 冻结持仓量
     */
    private BigDecimal lockAmount;

    /**
     * 状态：1-有效；2-锁定；
     */
    private Integer status;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}