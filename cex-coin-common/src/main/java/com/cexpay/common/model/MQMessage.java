package com.cexpay.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
public class MQMessage<T> implements Serializable {

    /**
     * 全局唯一消息 ID（幂等）
     */
    private String messageId = UUID.randomUUID().toString();

    /**
     * 业务类型：ORDER_CREATED / TRADE_MATCHED 等
     */
    private String eventType;

    /**
     * 业务主键（orderId / tradeId / symbol）
     */
    private String bizKey;

    /**
     * 实际业务数据
     */
    private T payload;

    /**
     * 消息创建时间
     */
    private long timestamp;

    public MQMessage() {
    }

    public MQMessage(String eventType, String bizKey, T payload) {
        this.messageId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.bizKey = bizKey;
        this.payload = payload;
        this.timestamp = Instant.now().toEpochMilli();
    }

}
