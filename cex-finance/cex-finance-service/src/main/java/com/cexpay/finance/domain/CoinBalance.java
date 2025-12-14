package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 币种余额
 * @TableName coin_balance
 */
@TableName(value ="coin_balance")
@Data
public class CoinBalance {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 币种ID
     */
    private Long coinId;

    /**
     * 币种名称
     */
    private String coinName;

    /**
     * 系统余额（根据充值提币计算）
     */
    private BigDecimal systemBalance;

    /**
     * 币种类型
     */
    private String coinType;

    /**
     * 归集账户余额
     */
    private BigDecimal collectAccountBalance;

    /**
     * 钱包账户余额
     */
    private BigDecimal loanAccountBalance;

    /**
     * 手续费账户余额(eth转账需要手续费)
     */
    private BigDecimal feeAccountBalance;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 
     */
    private BigDecimal rechargeAccountBalance;
}