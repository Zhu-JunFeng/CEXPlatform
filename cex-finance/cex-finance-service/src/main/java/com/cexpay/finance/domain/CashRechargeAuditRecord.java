package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 充值审核记录
 * @TableName cash_recharge_audit_record
 */
@TableName(value ="cash_recharge_audit_record")
@Data
public class CashRechargeAuditRecord {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 充值订单号
     */
    private Long orderId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 审核备注
     */
    private String remark;

    /**
     * 当前审核级数
     */
    private Integer step;

    /**
     * 审核人ID
     */
    private Long auditUserId;

    /**
     * 审核人
     */
    private String auditUserName;

    /**
     * 创建时间
     */
    private Date created;
}