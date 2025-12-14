package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 数字货币充值记录
 * @TableName coin_recharge
 */
@TableName(value ="coin_recharge")
@Data
public class CoinRecharge {
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
     * 币种名称
     */
    private String coinName;

    /**
     * 币种类型
     */
    private String coinType;

    /**
     * 钱包地址
     */
    private String address;

    /**
     * 充值确认数
     */
    private Integer confirm;

    /**
     * 状态：0-待入帐；1-充值失败，2到账失败，3到账成功；
     */
    private Integer status;

    /**
     * 交易id
     */
    private String txid;

    /**
     * 
     */
    private BigDecimal amount;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}