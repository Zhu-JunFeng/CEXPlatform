package com.cexpay.matching.rocketmq;

import cn.hutool.json.JSONUtil;
import com.cexpay.common.model.MQMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RocketMQProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public String sendSync(MQMessage<?> msg, String topic) {
        try {
            Message<String> mqMsg = MessageBuilder
                    .withPayload(JSONUtil.toJsonStr(msg))
                    .setHeader(RocketMQHeaders.KEYS, msg.getMessageId())
                    .setHeader("eventType", msg.getEventType())
                    .setHeader("bizKey", msg.getBizKey())
                    .build();

            SendResult result = rocketMQTemplate.syncSend(
                    topic,
                    mqMsg,
                    1000   // ⬅️ 强制 1s 超时（非常重要）
            );

            if (result.getSendStatus() == SendStatus.SEND_OK) {
                log.info(
                        "MQ send ok, topic={}, msgId={}, bizKey={}",
                        topic, msg.getMessageId(), msg.getBizKey()
                );
                return msg.getMessageId(); // ⚠️ 返回业务 messageId
            }

            log.warn(
                    "MQ send not OK, topic={}, msgId={}, status={}",
                    topic, msg.getMessageId(), result.getSendStatus()
            );
            throw new RuntimeException("MQ_SEND_STATUS_NOT_OK");

        } catch (Exception e) {
            log.error(
                    "MQ send failed, topic={}, msgId={}, bizKey={}",
                    topic, msg.getMessageId(), msg.getBizKey(), e
            );
            throw new RuntimeException("SEND_ORDER_FAILED", e);
        }
    }


    /**
     * 异步发送 MQ 消息（非强一致场景）
     */
    public void sendAsync(MQMessage<?> msg, String topic) {

        Message<String> mqMsg = MessageBuilder
                .withPayload(JSONUtil.toJsonStr(msg))
                .setHeader(RocketMQHeaders.KEYS, msg.getMessageId())
                .setHeader("eventType", msg.getEventType())
                .setHeader("bizKey", msg.getBizKey())
                .build();

        rocketMQTemplate.asyncSend(
                topic,
                mqMsg,
                new SendCallback() {

                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info(
                                "MQ async send ok, topic={}, msgId={}, bizKey={}",
                                topic, msg.getMessageId(), msg.getBizKey()
                        );
                    }

                    @Override
                    public void onException(Throwable e) {
                        log.error(
                                "MQ async send failed, topic={}, msgId={}, bizKey={}",
                                topic, msg.getMessageId(), msg.getBizKey(), e
                        );

                        // ❗必须做：消息补偿
                        // 1. 落库（outbox / mq_fail 表）
                        // 2. 或丢到本地重试队列
                        // 3. 或上报告警
//                        saveFailMessage(msg, topic, e);
                    }
                }
        );
    }

}