package com.cexpay.matching.match.impl;

import cn.hutool.core.collection.CollUtil;
import com.cexpay.common.enums.OrderDirection;
import com.cexpay.common.model.ExchangeTrade;
import com.cexpay.matching.model.MergeOrder;
import com.cexpay.matching.model.Order;
import com.cexpay.matching.model.OrderBooks;
import com.cexpay.matching.match.MatchService;
import com.cexpay.matching.match.MatchServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.cexpay.common.enums.MatchStrategy.LIMIT_PRICE;

@Slf4j
@Service
public class LimitPriceMatchServiceImpl implements MatchService, InitializingBean {
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

        Integer orderDirection = order.getOrderDirection();
        Iterator<Map.Entry<BigDecimal, MergeOrder>> makerQueueIterator = null;
        if (Objects.equals(orderDirection, OrderDirection.BUY.getCode())) {
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
            if (Objects.equals(orderDirection, OrderDirection.BUY.getCode()) && order.getPrice().compareTo(makerPrice) < 0) {
                break;
            }
            if (Objects.equals(orderDirection, OrderDirection.SELL.getCode()) && order.getPrice().compareTo(makerPrice) > 0) {
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

    }

    @Override
    public void handlerExchangeTrades(List<ExchangeTrade> exchangeTradeList) {

    }

    @Override
    public ExchangeTrade processMath(Order taker, Order maker, OrderBooks orderBooks) {
        return null;
    }
}
