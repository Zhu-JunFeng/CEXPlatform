//package com.cexpay.common.config;
//
//import com.cexpay.common.interceptor.TraceIdInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * Web MVC 配置
// *
// * 功能：
// * 1. 注册 TraceId 拦截器
// * 2. 拦截所有请求，自动处理 traceId
// *
// * @author cexpay
// * @date 2025-12-06
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private TraceIdInterceptor traceIdInterceptor;
//
//    /**
//     * 注册拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(traceIdInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/doc.html",
//                        "/swagger-ui.html",
//                        "/swagger-resources/**",
//                        "/v2/api-docs",
//                        "/v3/api-docs",
//                        "/webjars/**"
//                );
//    }
//}
