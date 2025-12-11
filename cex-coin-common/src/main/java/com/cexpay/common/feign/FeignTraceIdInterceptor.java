package com.cexpay.common.feign;

import com.cexpay.common.utils.TraceIdUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Feign 请求拦截器 - 用于传递 traceId
 * 
 * 功能：
 * 在调用其他微服务时，自动在请求头中添加 traceId
 * 这样可以实现跨微服务的链路追踪
 * 
 * 使用方式：
 * 只需将此拦截器注册为 Spring Bean，Feign 会自动使用
 * 
 * @author cexpay
 * @date 2025-12-06
 */
@Slf4j
@Component
public class FeignTraceIdInterceptor implements RequestInterceptor {

    /**
     * 请求头中 traceId 的 key
     */
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    /**
     * 拦截请求，添加 traceId 到请求头
     */
    @Override
    public void apply(RequestTemplate template) {
        // 从 MDC 中获取当前的 traceId
        String traceId = TraceIdUtil.getTraceId();

        // 将 traceId 添加到请求头
        if (traceId != null && !traceId.isEmpty()) {
            template.header(TRACE_ID_HEADER, traceId);
            log.debug("Add traceId to feign request: {}", traceId);
        }
    }
}
