package com.cexpay.market.controller;

import com.cexpay.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 市场数据控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/market")
@Tag(name = "Market Data", description = "市场数据相关接口")
public class MarketDataController {
    
    /**
     * 获取交易对列表
     */
    @GetMapping("/symbols")
    @Operation(summary = "获取交易对列表", description = "获取平台支持的所有交易对")
    public ApiResponse<Object> getSymbols() {
        return ApiResponse.success("获取成功", null);
    }
    
    /**
     * 获取深度数据
     */
    @GetMapping("/depth/{symbol}")
    @Operation(summary = "获取深度数据", description = "获取交易对的深度数据（买卖盘）")
    public ApiResponse<Object> getDepth(@PathVariable String symbol) {
        return ApiResponse.success(null);
    }
    
    /**
     * 获取最新行情
     */
    @GetMapping("/ticker/{symbol}")
    @Operation(summary = "获取最新行情", description = "获取交易对的最新行情")
    public ApiResponse<Object> getTicker(@PathVariable String symbol) {
        return ApiResponse.success(null);
    }
}
