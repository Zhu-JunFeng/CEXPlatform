package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 提现表
 * @TableName cash_withdrawals
 */
@TableName(value ="cash_withdrawals")
@Data
public class CashWithdrawals {
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
     * 币种ID
     */
    private Long coinId;

    /**
     * 资金账户ID
     */
    private Long accountId;

    /**
     * 数量（提现金额）
     */
    private BigDecimal num;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 到账金额
     */
    private BigDecimal mum;

    /**
     * 开户人
     */
    private String truename;

    /**
     * 银行名称
     */
    private String bank;

    /**
     * 银行所在省
     */
    private String bankProv;

    /**
     * 银行所在市
     */
    private String bankCity;

    /**
     * 开户行
     */
    private String bankAddr;

    /**
     * 银行账号
     */
    private String bankCard;

    /**
     * 备注
     */
    private String remark;

    /**
     * 当前审核级数
     */
    private Integer step;

    /**
     * 状态：0-待审核；1-审核通过；2-拒绝；3-提现成功；
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
     * 最后确认提现到账时间
     */
    private Date lastTime;
}