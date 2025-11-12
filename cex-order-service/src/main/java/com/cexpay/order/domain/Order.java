package com.cexpay.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order")
public class Order {
    
    /**
     * 订单ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 交易对（BTC/USDT）
     */
    private String symbol;
    
    /**
     * 订单类型：1-限价买入 2-限价卖出 3-市价买入 4-市价卖出
     */
    private Integer orderType;
    
    /**
     * 订单状态：0-未成交 1-部分成交 2-全部成交 3-已撤销
     */
    private Integer status;
    
    /**
     * 订单价格
     */
    private BigDecimal price;
    
    /**
     * 订单数量
     */
    private BigDecimal quantity;
    
    /**
     * 已成交数量
     */
    private BigDecimal filledQuantity;
    
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
