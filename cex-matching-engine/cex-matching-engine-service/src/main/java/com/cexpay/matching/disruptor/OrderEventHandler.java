package com.cexpay.matching.disruptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.OrderBooks;
import com.cexpay.matching.enums.MatchStrategy;
import com.cexpay.matching.match.MatchServiceFactory;
import com.lmax.disruptor.EventHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 有多少个OrderEventHandler 由有多少个交易对决定
 * 针对某一个OrderEventHandler 同一时间只有一个线程执行它
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventHandler implements EventHandler<OrderEvent> {

    private OrderBooks orderBooks;

    private String symbol;

    public OrderEventHandler(OrderBooks orderBooks) {
        this.orderBooks = orderBooks;
        this.symbol = orderBooks.getSymbol();
    }


    /**
     * 接受到某个消息
     *
     * @param orderEvent
     * @param l
     * @param b
     */
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) {
        // 从ringBuffer中接受数据
        Order order = (Order) orderEvent.getSource();
        if (!StrUtil.equals(this.symbol, order.getSymbol())) {
            return;
        }

        MatchServiceFactory.getMatchServiceMap(MatchStrategy.LIMIT_PRICE).match(this.orderBooks, order);

        log.info("订单事件处理完成 ");
    }
}
