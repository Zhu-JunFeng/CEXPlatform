package com.cexpay.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cexpay.common.constant.ErrorCode;
import com.cexpay.common.exception.BusinessException;
import com.cexpay.wallet.domain.Wallet;
import com.cexpay.wallet.mapper.WalletMapper;
import com.cexpay.wallet.service.IWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 钱包业务实现
 */
@Slf4j
@Service
public class WalletServiceImpl implements IWalletService {
    
    @Autowired
    private WalletMapper walletMapper;
    
    @Override
    public Wallet createWallet(Long userId, String currency) {
        // 检查钱包是否已存在
        QueryWrapper<Wallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("currency", currency);
        if (walletMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "钱包已存在");
        }
        
        Wallet wallet = Wallet.builder()
                .userId(userId)
                .currency(currency)
                .availableBalance(BigDecimal.ZERO)
                .frozenBalance(BigDecimal.ZERO)
                .totalBalance(BigDecimal.ZERO)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        
        walletMapper.insert(wallet);
        return wallet;
    }
    
    @Override
    public List<Wallet> getUserWallets(Long userId) {
        QueryWrapper<Wallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return walletMapper.selectList(queryWrapper);
    }
    
    @Override
    public Wallet getWallet(Long userId, String currency) {
        QueryWrapper<Wallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("currency", currency);
        return walletMapper.selectOne(queryWrapper);
    }
    
    @Override
    @Transactional
    public void deposit(Long userId, String currency, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "充值金额必须大于0");
        }
        
        Wallet wallet = getWallet(userId, currency);
        if (wallet == null) {
            wallet = createWallet(userId, currency);
        }
        
        wallet.setAvailableBalance(wallet.getAvailableBalance().add(amount));
        wallet.setTotalBalance(wallet.getTotalBalance().add(amount));
        wallet.setUpdateTime(LocalDateTime.now());
        
        walletMapper.updateById(wallet);
        log.info("用户 {} 充值 {} {}", userId, amount, currency);
    }
    
    @Override
    @Transactional
    public void withdraw(Long userId, String currency, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "提现金额必须大于0");
        }
        
        Wallet wallet = getWallet(userId, currency);
        if (wallet == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "钱包不存在");
        }
        
        if (wallet.getAvailableBalance().compareTo(amount) < 0) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "余额不足");
        }
        
        wallet.setAvailableBalance(wallet.getAvailableBalance().subtract(amount));
        wallet.setTotalBalance(wallet.getTotalBalance().subtract(amount));
        wallet.setUpdateTime(LocalDateTime.now());
        
        walletMapper.updateById(wallet);
        log.info("用户 {} 提现 {} {}", userId, amount, currency);
    }
    
    @Override
    @Transactional
    public void freezeBalance(Long userId, String currency, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "冻结金额必须大于0");
        }
        
        Wallet wallet = getWallet(userId, currency);
        if (wallet == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "钱包不存在");
        }
        
        if (wallet.getAvailableBalance().compareTo(amount) < 0) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "可用余额不足");
        }
        
        wallet.setAvailableBalance(wallet.getAvailableBalance().subtract(amount));
        wallet.setFrozenBalance(wallet.getFrozenBalance().add(amount));
        wallet.setUpdateTime(LocalDateTime.now());
        
        walletMapper.updateById(wallet);
        log.info("用户 {} 冻结 {} {}", userId, amount, currency);
    }
    
    @Override
    @Transactional
    public void unfreezeBalance(Long userId, String currency, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "解冻金额必须大于0");
        }
        
        Wallet wallet = getWallet(userId, currency);
        if (wallet == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "钱包不存在");
        }
        
        if (wallet.getFrozenBalance().compareTo(amount) < 0) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "冻结余额不足");
        }
        
        wallet.setAvailableBalance(wallet.getAvailableBalance().add(amount));
        wallet.setFrozenBalance(wallet.getFrozenBalance().subtract(amount));
        wallet.setUpdateTime(LocalDateTime.now());
        
        walletMapper.updateById(wallet);
        log.info("用户 {} 解冻 {} {}", userId, amount, currency);
    }
}
