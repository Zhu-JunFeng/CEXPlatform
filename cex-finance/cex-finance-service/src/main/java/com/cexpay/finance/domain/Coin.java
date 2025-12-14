package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 币种配置信息
 * @TableName coin
 */
@TableName(value ="coin")
@Data
public class Coin {
    /**
     * 币种ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 币种名称
     */
    private String name;

    /**
     * 币种标题
     */
    private String title;

    /**
     * 币种logo
     */
    private String img;

    /**
     * xnb：人民币
default：比特币系列
ETH：以太坊
ethToken：以太坊代币


     */
    private String type;

    /**
     * rgb：认购币
qbb：钱包币

     */
    private String wallet;

    /**
     * 小数位数
     */
    private Integer round;

    /**
     * 最小提现单位
     */
    private BigDecimal baseAmount;

    /**
     * 单笔最小提现数量
     */
    private BigDecimal minAmount;

    /**
     * 单笔最大提现数量
     */
    private BigDecimal maxAmount;

    /**
     * 当日最大提现数量
     */
    private BigDecimal dayMaxAmount;

    /**
     * status=1：启用
0：禁用
     */
    private Integer status;

    /**
     * 自动转出数量
     */
    private Double autoOut;

    /**
     * 手续费率
     */
    private Double rate;

    /**
     * 最低收取手续费个数
     */
    private BigDecimal minFeeNum;

    /**
     * 提现开关
     */
    private Integer withdrawFlag;

    /**
     * 充值开关
     */
    private Integer rechargeFlag;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}