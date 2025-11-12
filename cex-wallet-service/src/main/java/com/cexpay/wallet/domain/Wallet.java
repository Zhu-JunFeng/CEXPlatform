package com.cexpay.wallet.domain;

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
 * 钱包实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_wallet")
public class Wallet {
    
    /**
     * 钱包ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 币种（BTC, ETH, USDT等）
     */
    private String currency;
    
    /**
     * 可用余额
     */
    private BigDecimal availableBalance;
    
    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;
    
    /**
     * 总余额
     */
    private BigDecimal totalBalance;
    
    /**
     * 钱包地址
     */
    private String address;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
