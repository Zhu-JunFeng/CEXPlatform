package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName user_account_freeze
 */
@TableName(value ="user_account_freeze")
@Data
public class UserAccountFreeze {
    /**
     * 
     */
    @TableId
    private Long userId;

    /**
     * 
     */
    private BigDecimal freeze;
}