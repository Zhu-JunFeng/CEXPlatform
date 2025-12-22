package com.cexpay.matching.match;

import com.cexpay.common.model.ExchangeTrade;
import com.cexpay.matching.model.Order;
import com.cexpay.matching.model.OrderBooks;

import java.util.List;

/**
 * 撮合/交易
 */
public interface MatchService {

    /**
     * 订单撮合交易
     *
     * @param order
     */
    void match(OrderBooks orderBooks, Order order);

    /**
     * 发送撮合完成的订单
     *
     * @param completedOrders 撮合完成的订单
     */
    void handlerCompletedOrders(List<Order> completedOrders);

    /**
     * 发送撮合成功的成交记录
     *
     * @param exchangeTradeList 撮合成功的成交记录
     */
    void handlerExchangeTrades(List<ExchangeTrade> exchangeTradeList);


    /**
     * 进行委托单的匹配撮合交易
     *
     * @param taker 吃单
     * @param maker 挂单
     * @return ExchangeTrade 交易记录
     */
    ExchangeTrade processMath(Order taker, Order maker, OrderBooks orderBooks);

}
