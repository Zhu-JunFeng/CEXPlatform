package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 持仓账户流水
 * @TableName forex_account_detail
 */
@TableName(value ="forex_account_detail")
@Data
public class ForexAccountDetail {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 持仓账户ID
     */
    private Long accountId;

    /**
     * 收支类型：开仓；2-平仓；
     */
    private Integer type;

    /**
     * 持仓量
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date created;
}