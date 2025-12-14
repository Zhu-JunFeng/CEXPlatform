package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName user_coin_freeze
 */
@TableName(value ="user_coin_freeze")
@Data
public class UserCoinFreeze {
    /**
     * 
     */
    @TableId
    private Long userId;

    /**
     * 
     */
    private Long coinId;

    /**
     * 
     */
    private Integer freeze;
}