package com.cexpay.matching.disruptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent implements Serializable {

    /**
     * 时间戳
     */
    private final long timestamp = System.currentTimeMillis();

    /**
     * 事件携带的数据对象
     */
    protected transient Object source;

}
