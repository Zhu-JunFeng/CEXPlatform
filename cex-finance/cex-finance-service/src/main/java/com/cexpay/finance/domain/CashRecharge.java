package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 充值表
 * @TableName cash_recharge
 */
@TableName(value ="cash_recharge")
@Data
public class CashRecharge {
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
     * 币种名：cny，人民币；
     */
    private String coinName;

    /**
     * 数量（充值金额）
     */
    private BigDecimal num;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 手续费币种
     */
    private String feecoin;

    /**
     * 成交量（到账金额）
     */
    private BigDecimal mum;

    /**
     * 类型：alipay，支付宝；cai1pay，财易付；bank，银联；
     */
    private String type;

    /**
     * 充值订单号
     */
    private String tradeno;

    /**
     * 第三方订单号
     */
    private String outtradeno;

    /**
     * 充值备注备注
     */
    private String remark;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 当前审核级数
     */
    private Integer step;

    /**
     * 状态：0-待审核；1-审核通过；2-拒绝；3-充值成功；
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 银行卡账户名
     */
    private String name;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCard;

    /**
     * 最后确认到账时间。
     */
    private Date lastTime;
}