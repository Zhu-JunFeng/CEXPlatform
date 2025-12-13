package com.cexpay.matching.rocket;

import cn.hutool.json.JSONUtil;
import com.cexpay.matching.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        nameServer = "${spring.cloud.rocketmq.name-server:43.138.23.51:9876}",
        topic = "${spring.cloud.rocketmq.name-server:ORDER_TOPIC}",
        consumerGroup = "${spring.cloud.rocketmq.consumer.group:matching-engine-consumer-group}",
        selectorExpression = "*"
)
public class OrderConsumer implements RocketMQListener<String> {

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
}