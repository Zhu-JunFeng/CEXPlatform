package com.cexpay.matching.match.impl;

import cn.hutool.core.collection.CollUtil;
import com.cexpay.common.enums.OrderDirection;
import com.cexpay.common.model.ExchangeTrade;
import com.cexpay.common.model.MQMessage;
import com.cexpay.matching.model.MergeOrder;
import com.cexpay.matching.model.Order;
import com.cexpay.matching.model.OrderBooks;
import com.cexpay.matching.match.MatchService;
import com.cexpay.matching.match.MatchServiceFactory;
import com.cexpay.matching.rocketmq.RocketMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.cexpay.common.enums.MatchStrategy.LIMIT_PRICE;

@Slf4j
@Service
public class LimitPriceMatchServiceImpl implements MatchService, InitializingBean {

    @Autowired
    private RocketMQProducer rocketMQProducer;


    @Override
    public void afterPropertiesSet() throws Exception {
        MatchServiceFactory.addMatchService(LIMIT_PRICE, this);
    }

    @Override
    public void match(OrderBooks orderBooks, Order order) {
        // 数据校验
        if (order.isCompleted()) {
            orderBooks.cancelOrder(order);
            // todo 发送取消订单
            return;
        }
        if (order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        OrderDirection orderDirection = order.getOrderDirection();
        Iterator<Map.Entry<BigDecimal, MergeOrder>> makerQueueIterator = null;
        if (orderDirection == OrderDirection.BUY) {
            makerQueueIterator = orderBooks.getCurrentLimitPricesIterator(OrderDirection.SELL);
        } else {
            makerQueueIterator = orderBooks.getCurrentLimitPricesIterator(OrderDirection.BUY);
        }


        // 产生的交易记录
        List<ExchangeTrade> exchangeTradeList = new ArrayList<>();
        // 已经完成的订单
        List<Order> completedOrders = new ArrayList<>();


        //是否退出循环
        boolean exitLoop = false;
        while (makerQueueIterator.hasNext() && !exitLoop) {
            Map.Entry<BigDecimal, MergeOrder> makerOrderEntity = makerQueueIterator.next();
            BigDecimal makerPrice = makerOrderEntity.getKey(); // 买盘最高价 或 卖盘最低价
            MergeOrder makerMergeOrder = makerOrderEntity.getValue();
            if (orderDirection == OrderDirection.BUY && order.getPrice().compareTo(makerPrice) < 0) {
                break;
            }
            if (orderDirection == OrderDirection.SELL && order.getPrice().compareTo(makerPrice) > 0) {
                break;
            }
            Iterator<Order> iterator = makerMergeOrder.iterator();
            while (iterator.hasNext()) {
                Order maker = iterator.next();

                ExchangeTrade exchangeTrade = this.processMath(order, maker, orderBooks);
                exchangeTradeList.add(exchangeTrade);

                if (maker.isCompleted()) {
                    completedOrders.add(maker);
                }

                if (order.isCompleted()) {
                    exitLoop = true;
                    completedOrders.add(order);
                    break;
                }

            }
            if (makerMergeOrder.size() == 0) {
                makerQueueIterator.remove();
            }

        }

        if (order.getAmount().compareTo(order.getTradedAmount()) > 0) {
            orderBooks.addOrder(order);
        }

        if (CollUtil.isNotEmpty(completedOrders)) {
            // todo 发送撮合完成的订单
            this.handlerCompletedOrders(completedOrders);
        }

        if (CollUtil.isNotEmpty(exchangeTradeList)) {
            // todo 发送撮合成功的成交记录
            this.handlerExchangeTrades(exchangeTradeList);
        }

    }

    @Override
    public void handlerCompletedOrders(List<Order> completedOrders) {
        MQMessage<List<Order>> mqMessage = new MQMessage<>();
        mqMessage.setTimestamp(System.currentTimeMillis());
        mqMessage.setPayload(completedOrders);
        rocketMQProducer.sendAsync(mqMessage,"completed_orders_out");
    }

    @Override
    public void handlerExchangeTrades(List<ExchangeTrade> exchangeTradeList) {
        MQMessage<List<ExchangeTrade>> mqMessage = new MQMessage<>();
        mqMessage.setTimestamp(System.currentTimeMillis());
        mqMessage.setPayload(exchangeTradeList);
        rocketMQProducer.sendAsync(mqMessage,"exchange_trades_out");
    }

    @Override
    public ExchangeTrade processMath(Order taker, Order maker, OrderBooks orderBooks) {

        // 成交的价格
        BigDecimal dealPrice = maker.getPrice();

        // 成交的数量
        BigDecimal turnoverAmount = BigDecimal.ZERO;
        // 本次需要的数量
        BigDecimal needAmount = calcTradeAmount(taker);
        // 本次提供给你的数量
        BigDecimal providerAmount = calcTradeAmount(maker);

        turnoverAmount = needAmount.compareTo(providerAmount) >= 0 ? providerAmount : needAmount;

        if (turnoverAmount.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        // 设置本次吃单的成交数据
        taker.setTradedAmount(taker.getTradedAmount().add(turnoverAmount));
        BigDecimal takerTurnover = turnoverAmount.multiply(dealPrice).setScale(orderBooks.getCoinScale(), RoundingMode.HALF_UP); // 成交额
        taker.setTurnover(takerTurnover);

        // 设置本次挂单的成交数据
        maker.setTradedAmount(maker.getTradedAmount().add(turnoverAmount));
        BigDecimal markerTurnover = turnoverAmount.multiply(dealPrice).setScale(orderBooks.getBaseCoinScale(), RoundingMode.HALF_UP);
        maker.setTurnover(markerTurnover);

        ExchangeTrade exchangeTrade = new ExchangeTrade();
        exchangeTrade.setPrice(dealPrice);
        exchangeTrade.setAmount(turnoverAmount);
        exchangeTrade.setSymbol(orderBooks.getSymbol());
        exchangeTrade.setTime(System.currentTimeMillis());
        exchangeTrade.setDirection(taker.getOrderDirection());
        exchangeTrade.setBuyTurnOver(takerTurnover);
        exchangeTrade.setBuyOrderId(taker.getOrderId());
        exchangeTrade.setSellOrderId(maker.getOrderId());
        exchangeTrade.setSellTurnOver(markerTurnover);

        /**
         * 处理盘口:
         *  我们的委托单肯定是: 将挂单的数据做了一部分消耗
         */
        if (maker.getOrderDirection() == OrderDirection.BUY) {
            orderBooks.getBuyTradePlate().remove(maker, turnoverAmount);
        } else {
            orderBooks.getSellTradePlate().remove(maker, turnoverAmount);
        }

        return exchangeTrade;
    }

    private BigDecimal calcTradeAmount(Order order) {
        return order.getAmount().subtract(order.getTradedAmount());
    }
}
