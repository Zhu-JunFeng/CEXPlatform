package com.cexpay.wallet.service;

import com.cexpay.wallet.domain.Wallet;

import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包业务接口
 */
public interface IWalletService {
    
    /**
     * 创建钱包
     */
    Wallet createWallet(Long userId, String currency);
    
    /**
     * 获取用户钱包列表
     */
    List<Wallet> getUserWallets(Long userId);
    
    /**
     * 获取用户特定币种的钱包
     */
    Wallet getWallet(Long userId, String currency);
    
    /**
     * 充值
     */
    void deposit(Long userId, String currency, BigDecimal amount);
    
    /**
     * 提现
     */
    void withdraw(Long userId, String currency, BigDecimal amount);
    
    /**
     * 冻结资金
     */
    void freezeBalance(Long userId, String currency, BigDecimal amount);
    
    /**
     * 解冻资金
     */
    void unfreezeBalance(Long userId, String currency, BigDecimal amount);
}
