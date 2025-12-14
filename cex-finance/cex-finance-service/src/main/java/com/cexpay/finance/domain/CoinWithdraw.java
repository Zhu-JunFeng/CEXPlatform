package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 数字货币提现记录
 * @TableName coin_withdraw
 */
@TableName(value ="coin_withdraw")
@Data
public class CoinWithdraw {
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
     * 交易id
     */
    private String txid;

    /**
     * 提现量
     */
    private BigDecimal num;

    /**
     * 手续费()
     */
    private BigDecimal fee;

    /**
     * 实际提现
     */
    private BigDecimal mum;

    /**
     * 0站内1其他2手工提币
     */
    private Integer type;

    /**
     * 链上手续费花费
     */
    private BigDecimal chainFee;

    /**
     * 区块高度
     */
    private Integer blockNum;

    /**
     * 后台审核人员提币备注备注
     */
    private String remark;

    /**
     * 钱包提币备注备注
     */
    private String walletMark;

    /**
     * 当前审核级数
     */
    private Integer step;

    /**
     * 状态：0-审核中；1-成功；2-拒绝；3-撤销；4-审核通过；5-打币中；
     */
    private Integer status;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}