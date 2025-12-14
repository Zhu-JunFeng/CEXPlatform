package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 币种类型
 * @TableName coin_type
 */
@TableName(value ="coin_type")
@Data
public class CoinType {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 代码
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：0-无效；1-有效；
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
}