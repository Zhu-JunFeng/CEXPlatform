package com.cexpay.matching.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.openhft.affinity.AffinityThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;

@Configuration
@EnableConfigurationProperties(DisruptorProperties.class)
public class DisruptorAutoConfiguration {

    @Autowired
    private DisruptorProperties disruptorProperties;

    @Bean
    public DisruptorTemplate disruptorTemplate(RingBuffer<OrderEvent> ringBuffer) {
        return new DisruptorTemplate(ringBuffer);
    }

    /**
     * 创建一个ringBuffer
     */
    @Bean
    public RingBuffer<OrderEvent> ringBuffer(EventFactory<OrderEvent> eventFactory,
                                             ThreadFactory threadFactory,
                                             WaitStrategy waitStrategy,
                                             EventHandler<OrderEvent>[] eventHandler) {
        /**
         * EventFactory<T> eventFactory,
         * int ringBufferSize,
         * ThreadFactory threadFactory,
         * ProducerType producerType,
         * WaitStrategy waitStrategy
         */
        Disruptor<OrderEvent> disruptor = null;
        ProducerType producerType = ProducerType.SINGLE;
        if (disruptorProperties.isMultiProducer()) {
            producerType = ProducerType.MULTI;
        }
        disruptor = new Disruptor<>(eventFactory, disruptorProperties.getRingBufferSize(), threadFactory, producerType, waitStrategy);
        disruptor.setDefaultExceptionHandler(new DisruptorHandlerException());
        // 设置消费者，每个消费者对应一个交易对
        // 有多少个交易对就有多少个消费者，事件来了之后，都个消费者并行执行
        disruptor.handleEventsWith(eventHandler);
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        disruptor.start(); // 开始监听
        final Disruptor<OrderEvent> disruptorShutDown = disruptor;

        // 优雅停机
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> disruptorShutDown.shutdown(), "DisruptorShutDownThread"
        ));
        return ringBuffer;
    }


    @Bean
    public EventFactory<OrderEvent> eventFactory() {
        EventFactory<OrderEvent> orderEventEventFactory = new EventFactory<OrderEvent>() {
            @Override
            public OrderEvent newInstance() {
                return new OrderEvent();
            }
        };
        return orderEventEventFactory;
    }

    /**
     * @return 无锁高效的等待策略
     */
    @Bean
    public WaitStrategy waitStrategy() {
        WaitStrategy waitStrategy = new YieldingWaitStrategy();
        return waitStrategy;
    }

    /**
     * @return 抢占cpu的线程工厂
     */
    @Bean
    public ThreadFactory threadFactory() {
        return new AffinityThreadFactory("Matching-Handle:");
    }

}
