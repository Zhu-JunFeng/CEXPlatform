package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 创新交易币种表
 * @TableName forex_coin
 */
@TableName(value ="forex_coin")
@Data
public class ForexCoin {
    /**
     * 
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
     * 排序
     */
    private Integer sort;

    /**
     * 状态: 0禁用 1启用
     */
    private Integer status;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}