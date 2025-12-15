package com.cexpay.matching.rocket;

import cn.hutool.json.JSONUtil;
import com.cexpay.matching.disruptor.DisruptorTemplate;
import com.cexpay.matching.domain.Order;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Slf4j
@Configuration
public class RocketMqConsumerConfig {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Value("${rocketmq.consumer.tag:*}")
    private String tag;

    @Value("${rocketmq.consumer.consume-thread-min:4}")
    private int consumeThreadMin;

    @Value("${rocketmq.consumer.consume-thread-max:16}")
    private int consumeThreadMax;

    @Value("${rocketmq.consumer.consume-batch-size:1}")
    private int consumeBatchSize;

    @Value("${rocketmq.consumer.max-reconsume-times:16}")
    private int maxReconsumeTimes;

    private DefaultMQPushConsumer consumer;

    @Autowired
    private DisruptorTemplate disruptorTemplate;

    @PostConstruct
    public void startConsumer() {
        try {
            consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setNamesrvAddr(nameServer);

            // ▶ 消费位点策略（生产环境明确指定）
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

            // ▶ 并发与吞吐控制
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setConsumeMessageBatchMaxSize(consumeBatchSize);

            // ▶ 重试与失败策略
            consumer.setMaxReconsumeTimes(maxReconsumeTimes);

            // ▶ 订阅
            consumer.subscribe(topic, tag);

            // ▶ 注册监听器
            consumer.registerMessageListener(
                    (MessageListenerConcurrently) this::consume
            );

            consumer.start();

            log.info("""
                            ✅ RocketMQ Consumer 启动成功
                            ├─ group: {}
                            ├─ topic: {}
                            ├─ tag: {}
                            ├─ namesrv: {}
                            ├─ thread: {} ~ {}
                            ├─ batchSize: {}
                            └─ maxRetry: {}
                            """,
                    consumerGroup, topic, tag, nameServer,
                    consumeThreadMin, consumeThreadMax,
                    consumeBatchSize, maxReconsumeTimes
            );

        } catch (Exception e) {
            log.error("❌ RocketMQ Consumer 启动失败（直接中断服务启动）", e);
            throw new IllegalStateException("RocketMQ Consumer 启动失败", e);
        }
    }

    private ConsumeConcurrentlyStatus consume(
            List<MessageExt> msgs,
            ConsumeConcurrentlyContext context) {

        long start = System.nanoTime();

        for (MessageExt msg : msgs) {
            try {
                String body = new String(msg.getBody(), StandardCharsets.UTF_8);

                log.debug("消费消息 msgId={}, keys={}, reconsumeTimes={}",
                        msg.getMsgId(),
                        msg.getKeys(),
                        msg.getReconsumeTimes()
                );

                // ▶ 幂等入口（必须有）
                if (isDuplicate(msg)) {
                    log.warn("重复消息已忽略 msgId={}", msg.getMsgId());
                    continue;
                }

                // ▶ 业务处理
                handleBusiness(msg, body);

            } catch (Exception e) {
                log.error("消息消费失败 msgId={}, 已重试 {} 次",
                        msg.getMsgId(),
                        msg.getReconsumeTimes(),
                        e
                );
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }

        long costMs = Duration.ofNanos(System.nanoTime() - start).toMillis();
        if (costMs > 500) {
            log.warn("消费耗时较长 {} ms, batchSize={}", costMs, msgs.size());
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * 幂等校验（示例，生产请接 Redis / DB）
     */
    private boolean isDuplicate(MessageExt msg) {
        // 示例：基于 msgId / keys
        return false;
    }

    /**
     * 实际业务处理入口
     */
    private void handleBusiness(MessageExt msg, String body) {
        log.info("📩 消费成功 msgId={}, body={}", msg.getMsgId(), body);
        Order bean = JSONUtil.toBean(body, Order.class);
        disruptorTemplate.onData(bean);
        // TODO 撮合 状态流转
    }

    @PreDestroy
    public void shutdown() {
        if (consumer != null) {
            consumer.shutdown();
            log.info("RocketMQ Consumer 已安全关闭");
        }
    }
}