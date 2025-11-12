package com.cexpay.matching.service;

import com.cexpay.matching.core.TradeLog;

/**
 * 撮合引擎接口
 */
public interface IMatchingService {
    
    /**
     * 处理订单撮合
     */
    void matchOrder(Long orderId, String symbol);
    
    /**
     * 获取订单簿
     */
    Object getOrderBook(String symbol);
}
