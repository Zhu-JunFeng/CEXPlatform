package com.cexpay.wallet.controller;

import com.cexpay.common.dto.ApiResponse;
import com.cexpay.wallet.domain.Wallet;
import com.cexpay.wallet.service.IWalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/wallet")
@Tag(name = "Wallet Management", description = "钱包管理相关接口")
public class WalletController {
    
    @Autowired
    private IWalletService walletService;
    
    /**
     * 获取用户钱包列表
     */
    @GetMapping("/list/{userId}")
    @Operation(summary = "获取钱包列表", description = "获取用户的所有钱包")
    public ApiResponse<List<Wallet>> getWallets(@PathVariable Long userId) {
        List<Wallet> wallets = walletService.getUserWallets(userId);
        return ApiResponse.success(wallets);
    }
    
    /**
     * 获取用户特定币种的钱包
     */
    @GetMapping("/{userId}/{currency}")
    @Operation(summary = "获取特定币种钱包", description = "获取用户特定币种的钱包信息")
    public ApiResponse<Wallet> getWallet(@PathVariable Long userId, @PathVariable String currency) {
        Wallet wallet = walletService.getWallet(userId, currency);
        return ApiResponse.success(wallet);
    }
    
    /**
     * 创建钱包
     */
    @PostMapping("/create")
    @Operation(summary = "创建钱包", description = "为用户创建新的钱包")
    public ApiResponse<Wallet> createWallet(@RequestParam Long userId, @RequestParam String currency) {
        Wallet wallet = walletService.createWallet(userId, currency);
        return ApiResponse.success("钱包创建成功", wallet);
    }
    
    /**
     * 充值
     */
    @PostMapping("/deposit")
    @Operation(summary = "充值", description = "向钱包充值")
    public ApiResponse<Void> deposit(@RequestParam Long userId, @RequestParam String currency, 
                                     @RequestParam BigDecimal amount) {
        walletService.deposit(userId, currency, amount);
        return ApiResponse.success("充值成功", null);
    }
    
    /**
     * 提现
     */
    @PostMapping("/withdraw")
    @Operation(summary = "提现", description = "从钱包提现")
    public ApiResponse<Void> withdraw(@RequestParam Long userId, @RequestParam String currency, 
                                      @RequestParam BigDecimal amount) {
        walletService.withdraw(userId, currency, amount);
        return ApiResponse.success("提现成功", null);
    }
}
