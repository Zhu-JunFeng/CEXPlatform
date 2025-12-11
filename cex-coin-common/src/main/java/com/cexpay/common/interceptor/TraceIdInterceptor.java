package com.cexpay.common.interceptor;

import com.cexpay.common.utils.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * TraceId HTTP 拦截器
 * 
 * 功能：
 * 1. 请求进入时，从请求头中获取 traceId（如果有）
 * 2. 如果请求头中没有 traceId，则生成新的 traceId
 * 3. 将 traceId 设置到 MDC 中
 * 4. 请求完成后，清除 MDC 中的 traceId
 * 
 * 使用方式：
 * 在 WebConfig 中配置这个拦截器即可自动处理
 * 
 * 客户端可以通过请求头传递 traceId：
 * GET /api/users
 * X-Trace-Id: abc123def456
 * 
 * @author cexpay
 * @date 2025-12-06
 */
@Slf4j
@Component
public class TraceIdInterceptor implements HandlerInterceptor {

    /**
     * 请求头中 traceId 的 key
     */
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    /**
     * 请求前处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从请求头中获取 traceId
        String traceId = request.getHeader(TRACE_ID_HEADER);

        // 2. 如果请求头中没有 traceId，则生成新的
        if (traceId == null || traceId.isEmpty()) {
            traceId = TraceIdUtil.generateTraceId();
        }

        // 3. 将 traceId 设置到 MDC
        TraceIdUtil.setTraceId(traceId);

        // 4. 将 traceId 添加到响应头（便于客户端追踪）
        response.setHeader(TRACE_ID_HEADER, traceId);

        log.debug("Set traceId: {}", traceId);

        return true;
    }

    /**
     * 请求完成后处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) {
        // 清除 MDC 中的 traceId
        TraceIdUtil.removeTraceId();
    }
}
