package com.cexpay.exchange.interceptor.impl;

import com.cexpay.common.model.ApiResponse;
import com.cexpay.exchange.interceptor.CexAdminInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CexAdminInterceptorFallback implements FallbackFactory<CexAdminInterceptor> {

    @Override
    public CexAdminInterceptor create(Throwable cause) {

        // 打印具体异常原因，否则排查困难
        log.error("cex-admin-service 调用失败: {}", cause.getMessage(), cause);

        // 返回一个“降级实现”对象
        return new CexAdminInterceptor() {

            @Override
            public Object hello() {
                log.error("触发 cex-admin-service /admin/test/hello 降级逻辑");
                return ApiResponse.fail("cex-admin-service 不可用，触发 fallback。原因: " + cause.getMessage());
            }
        };
    }
}