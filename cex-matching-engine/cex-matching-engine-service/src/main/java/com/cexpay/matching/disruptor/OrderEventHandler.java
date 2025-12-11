package com.cexpay.matching.disruptor;

import cn.hutool.json.JSONUtil;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 有多少个OrderEventHandler 由有多少个交易对觉得
 */
@Slf4j
public class OrderEventHandler implements EventHandler<OrderEvent> {

    /**
     * 接受到某个消息
     * @param orderEvent
     * @param l
     * @param b
     * @throws Exception
     */
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        log.info("接受订单事件 OrderEventHandler received order event: {}" , JSONUtil.toJsonStr(orderEvent));

        // todo

        log.info("订单事件处理完成 ");
    }
}
