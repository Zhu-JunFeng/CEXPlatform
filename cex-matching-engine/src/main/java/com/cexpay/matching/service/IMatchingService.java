package com.cexpay.matching.service;

import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.Trade;

import java.util.List;

/**
 * 撮合引擎接口
 */
public interface IMatchingService {

    /**
     * 处理订单撮合（基于已存在订单ID）
     */
    void matchOrder(Long orderId, String symbol);

    /**
     * 提交一笔订单进行撮合（下单接口）
     * 返回成交列表（可能为空）
     */
    List<Trade> submitOrder(Order order);

    /**
     * 撤单
     */
    boolean cancelOrder(Long orderId, String symbol);

    /**
     * 获取订单簿快照
     */
    Object getOrderBook(String symbol);
}
