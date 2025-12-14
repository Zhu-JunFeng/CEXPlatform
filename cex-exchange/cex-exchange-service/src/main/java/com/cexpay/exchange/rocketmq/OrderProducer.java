package com.cexpay.exchange.rocketmq;

import cn.hutool.json.JSONUtil;
import com.cexpay.exchange.model.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private static final String TOPIC = "ORDER_TOPIC";

    /**
     * 同步发送订单消息（适合交易核心）
     */
    public String sendOrder(OrderMessage order) {
        try {
            SendResult result = rocketMQTemplate.syncSend(
                    TOPIC,
                    MessageBuilder.withPayload(JSONUtil.toJsonStr(order))
                            .setHeader(RocketMQHeaders.KEYS, order.getOrderId())
                            .build()
            );
            if (result.getSendStatus() == SendStatus.SEND_OK) {
                log.info("Order sent successfully, orderId={}, msgId={}", order.getOrderId(), result.getMsgId());
                return result.getMsgId();
            } else {
                log.warn("Order send status not OK, orderId={}, sendStatus={}", order.getOrderId(), result.getSendStatus());
            }
        } catch (Exception e) {
            log.error("Failed to send order, orderId={}", order.getOrderId(), e);
            // 可以放到重试队列或者异步记录到DB
            throw new RuntimeException("SEND_ORDER_FAILED", e);
        }
        return null;
    }

    /**
     * 异步发送订单消息（适合业务不要求强同步场景）
     */
    public void sendOrderAsync(OrderMessage order) {
        rocketMQTemplate.asyncSend(
                TOPIC,
                MessageBuilder.withPayload(JSONUtil.toJsonStr(order))
                        .setHeader(RocketMQHeaders.KEYS, order.getOrderId())
                        .build(),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("Async order sent successfully, orderId={}, msgId={}", order.getOrderId(), sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        log.error("Async order send failed, orderId={}", order.getOrderId(), e);
                        // 异步失败，可写入DB或消息补偿队列
                    }
                }
        );
    }
}