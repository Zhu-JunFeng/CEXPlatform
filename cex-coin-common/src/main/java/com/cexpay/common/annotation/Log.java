package com.cexpay.common.annotation;

import java.lang.annotation.*;

/**
 * 日志注解 - 用于标记需要记录日志的方法
 * 
 * 功能说明：
 * - 记录方法入参和出参
 * - 记录执行时间
 * - 记录执行异常
 * - 支持自定义日志描述
 * - 支持敏感字段脱敏
 * 
 * 使用示例：
 * @Log(title = "用户管理", desc = "查询用户列表")
 * public PageResult<UserDto> queryUsers(PageQuery pageQuery) {
 *     // ...
 * }
 * 
 * @author cexpay
 * @date 2025-12-06
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作模块名称
     */
    String title() default "";

    /**
     * 操作描述
     */
    String desc() default "";

    /**
     * 是否记录入参（默认记录）
     */
    boolean recordParams() default true;

    /**
     * 是否记录出参（默认记录）
     */
    boolean recordResult() default true;

    /**
     * 是否记录执行时间（默认记录）
     */
    boolean recordTime() default true;

    /**
     * 敏感字段名称，多个用逗号分隔
     * 比如：password,phoneNumber,idCard,token
     */
    String sensitiveFields() default "";

    /**
     * 是否为异步操作（异步操作不记录返回值）
     */
    boolean isAsync() default false;

    /**
     * 日志级别（INFO、DEBUG、WARN等）
     */
    LogLevel level() default LogLevel.INFO;

    /**
     * 日志级别枚举
     */
    enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }
}
