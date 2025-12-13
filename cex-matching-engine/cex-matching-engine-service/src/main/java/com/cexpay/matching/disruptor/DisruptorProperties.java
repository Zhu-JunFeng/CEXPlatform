package com.cexpay.matching.disruptor;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.disruptor")
public class DisruptorProperties {

    /**
     * 是否启用 Disruptor
     */
    private boolean enabled = true;

    /**
     * RingBuffer 大小（必须是 2 的幂）
     */
    private Integer ringBufferSize = 1024 * 1024;

    /**
     * 是否多生产者
     */
    private boolean multiProducer = false;

    /**
     * 等待策略
     * blocking | yielding | busySpin
     */
    private String waitStrategy = "yielding";

    /**
     * 是否在异常时 fail-fast
     */
    private boolean failFast = true;

    @PostConstruct
    public void validate() {
        if ((ringBufferSize & (ringBufferSize - 1)) != 0) {
            throw new IllegalArgumentException(
                    "ringBufferSize must be power of 2, current=" + ringBufferSize
            );
        }
    }
}
