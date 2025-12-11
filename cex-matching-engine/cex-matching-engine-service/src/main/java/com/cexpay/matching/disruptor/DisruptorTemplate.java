package com.cexpay.matching.disruptor;

import com.cexpay.matching.domain.Order;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class DisruptorTemplate {

    private static final EventTranslatorOneArg<OrderEvent, Order> translator = new EventTranslatorOneArg<OrderEvent, Order>() {
        public void translateTo(OrderEvent event, long sequence, Order order) {
            event.setSource(order);
        }
    };
    private final RingBuffer<OrderEvent> ringBuffer;

    public DisruptorTemplate(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Order input) {
        ringBuffer.publishEvent(translator, input);
    }
}
