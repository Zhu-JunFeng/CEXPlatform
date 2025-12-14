package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 用户财产记录
 * @TableName account
 */
@TableName(value ="account")
@Data
public class Account {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 币种id
     */
    private Long coinId;

    /**
     * 账号状态：1，正常；2，冻结；
     */
    private Integer status;

    /**
     * 币种可用金额
     */
    private BigDecimal balanceAmount;

    /**
     * 币种冻结金额
     */
    private BigDecimal freezeAmount;

    /**
     * 累计充值金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 累计提现金额
     */
    private BigDecimal withdrawalsAmount;

    /**
     * 净值
     */
    private BigDecimal netValue;

    /**
     * 占用保证金
     */
    private BigDecimal lockMargin;

    /**
     * 持仓盈亏/浮动盈亏
     */
    private BigDecimal floatProfit;

    /**
     * 总盈亏
     */
    private BigDecimal totalProfit;

    /**
     * 充值地址
     */
    private String recAddr;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}