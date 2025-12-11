package com.cexpay.common.aspect;

import com.alibaba.fastjson2.JSON;
import com.cexpay.common.annotation.Log;
import com.cexpay.common.utils.SensitiveUtil;
import com.cexpay.common.utils.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

/**
 * 企业级日志切面 - 环绕通知
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(com.cexpay.common.annotation.Log)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);

        // 初始化日志上下文
        LogContext context = new LogContext();
        context.traceId = TraceIdUtil.getTraceId();
        context.spanId = UUID.randomUUID().toString().substring(0, 8);
        context.level = logAnnotation.level().name();
        context.service = getServiceName(joinPoint.getTarget().getClass().getName());
        context.method = method.getName();
        context.module = logAnnotation.title();
        context.desc = logAnnotation.desc();
        context.startTime = System.currentTimeMillis();

        extractHttpInfo(context);

        if (logAnnotation.recordParams()) {
            Object[] args = joinPoint.getArgs();
            context.params = args.length == 1 
                ? SensitiveUtil.desensitize(args[0], logAnnotation.sensitiveFields())
                : Arrays.stream(args).map(arg -> SensitiveUtil.desensitize(arg, logAnnotation.sensitiveFields())).toArray();
        }

        Object result = null;
        Throwable exception = null;

        try {
            result = joinPoint.proceed();
            if (logAnnotation.recordResult() && !logAnnotation.isAsync() && result != null) {
                context.result = SensitiveUtil.desensitize(result, logAnnotation.sensitiveFields());
            }
            return result;
        } catch (Throwable e) {
            exception = e;
            context.exception = e;
            throw e;
        } finally {
            context.duration = System.currentTimeMillis() - context.startTime;
            outputLog(context, logAnnotation, exception);
        }
    }

    private void extractHttpInfo(LogContext context) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                context.url = request.getRequestURI();
                context.httpMethod = request.getMethod();
                context.clientIp = getClientIp(request);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String[] headers = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip.contains(",") ? ip.split(",")[0].trim() : ip;
            }
        }
        return request.getRemoteAddr();
    }

    private String getServiceName(String className) {
        return className.contains(".") ? className.substring(className.lastIndexOf(".") + 1) : className;
    }

    private void outputLog(LogContext context, Log logAnnotation, Throwable exception) {
        String operation = StringUtils.isNotBlank(context.module) 
            ? context.module + "-" + context.desc 
            : context.desc;
        
        String paramsStr = context.params != null ? truncate(JSON.toJSONString(context.params), 200) : "-";
        String resultStr = context.result != null ? truncate(JSON.toJSONString(context.result), 200) : "-";
        String exceptionStr = exception != null ? exception.getClass().getSimpleName() + ":" + truncate(exception.getMessage(), 100) : "success";

        String logMsg = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
            context.traceId,
            context.spanId,
            context.level,
            context.service,
            context.method,
            operation,
            paramsStr,
            resultStr,
            context.duration,
            context.url + " " + context.httpMethod + " " + context.clientIp,
            exceptionStr
        );

        switch (logAnnotation.level()) {
            case DEBUG -> log.debug(logMsg);
            case WARN -> log.warn(logMsg);
            case ERROR -> {
                if (exception != null) log.error(logMsg, exception);
                else log.error(logMsg);
            }
            default -> {
                if (exception != null) log.error(logMsg, exception);
                else log.info(logMsg);
            }
        }
    }

    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        return str.length() > maxLength ? str.substring(0, maxLength) + "..." : str;
    }

    static class LogContext {
        String traceId;
        String spanId;
        String level;
        String service;
        String method;
        String module;
        String desc;
        String url;
        String httpMethod;
        String clientIp;
        Object params;
        Object result;
        Throwable exception;
        long startTime;
        long duration;
    }
}