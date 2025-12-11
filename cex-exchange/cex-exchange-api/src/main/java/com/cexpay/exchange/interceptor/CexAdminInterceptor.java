package com.cexpay.exchange.interceptor;

import com.cexpay.common.model.ApiResponse;
import com.cexpay.exchange.interceptor.impl.CexAdminInterceptorFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "cex-admin-service",contextId = "cex-admin-service",
        fallbackFactory = CexAdminInterceptorFallback.class)
public interface CexAdminInterceptor {

    // /admin/test/hello
    @RequestMapping("/example/users/1")
    Object hello();
}
