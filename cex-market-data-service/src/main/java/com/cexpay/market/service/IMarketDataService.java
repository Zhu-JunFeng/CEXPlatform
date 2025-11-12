package com.cexpay.market.service;

import com.cexpay.market.dto.KLineData;

import java.util.List;

/**
 * 市场数据业务接口
 */
public interface IMarketDataService {
    
    /**
     * 获取K线数据
     */
    List<KLineData> getKLines(String symbol, String period, Long startTime, Long endTime);
    
    /**
     * 获取最新行情
     */
    Object getLatestTicker(String symbol);
    
    /**
     * 获取深度数据
     */
    Object getDepth(String symbol);
}
