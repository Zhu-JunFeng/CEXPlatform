package com.cexpay.matching.rocket;

import cn.hutool.json.JSONUtil;
import com.cexpay.matching.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "ORDER_TOPIC",
        consumerGroup = "${spring.cloud.rocketmq.consumer.group:matching-engine-consumer-group}",
        selectorExpression = "*"
)
public class OrderConsumer implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(String msg) {
        try {
            Order order = JSONUtil.toBean(msg, Order.class);
            log.info("OrderConsumer order = {}", JSONUtil.toJsonStr(order));
            // TODO: 调用 matching engine 处理订单
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 设置最大重试次数
        consumer.setMaxReconsumeTimes(3);
        // 如下，设置其它consumer相关属性
        consumer.setPullBatchSize(16);
    }
}