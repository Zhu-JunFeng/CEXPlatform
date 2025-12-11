package com.cexpay.common.task;

import com.cexpay.common.utils.TraceIdUtil;

/**
 * 异步任务 TraceId 传递工具
 * 
 * 用于在异步任务中保持 traceId 的连贯性
 * 
 * 使用方式：
 * ```
 * // 在主线程中获取当前的 traceId
 * String traceId = TraceIdUtil.getTraceId();
 * 
 * // 传入异步任务
 * asyncService.doAsync(() -> {
 *     // 在异步任务开始时设置 traceId
 *     TraceIdContext context = new TraceIdContext(traceId);
 *     context.runWithTraceId(() -> {
 *         // 业务逻辑
 *     });
 * });
 * ```
 * 
 * @author cexpay
 * @date 2025-12-06
 */
public class TraceIdContext {

    private final String traceId;

    /**
     * 构造函数
     * 
     * @param traceId 链路追踪ID
     */
    public TraceIdContext(String traceId) {
        this.traceId = traceId;
    }

    /**
     * 在 traceId 上下文中运行代码块
     * 
     * @param runnable 要执行的代码块
     */
    public void runWithTraceId(Runnable runnable) {
        try {
            TraceIdUtil.setTraceId(traceId);
            runnable.run();
        } finally {
            TraceIdUtil.removeTraceId();
        }
    }

    /**
     * 在 traceId 上下文中执行有返回值的操作
     * 
     * @param callable 要执行的代码块
     * @param <T>      返回类型
     * @return 执行结果
     */
    public <T> T callWithTraceId(java.util.concurrent.Callable<T> callable) {
        try {
            TraceIdUtil.setTraceId(traceId);
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException("执行任务失败", e);
        } finally {
            TraceIdUtil.removeTraceId();
        }
    }

    /**
     * 获取当前的 traceId
     * 
     * @return traceId
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * 为异步任务创建一个包装的 Runnable
     * 在异步任务执行时自动处理 traceId
     * 
     * @param runnable 原始任务
     * @return 包装后的任务
     */
    public static Runnable wrapWithTraceId(Runnable runnable) {
        String traceId = TraceIdUtil.getTraceId();
        return () -> {
            TraceIdContext context = new TraceIdContext(traceId);
            context.runWithTraceId(runnable);
        };
    }

    /**
     * 为异步任务创建一个包装的 Callable
     * 在异步任务执行时自动处理 traceId
     * 
     * @param callable 原始任务
     * @param <T>      返回类型
     * @return 包装后的任务
     */
    public static <T> java.util.concurrent.Callable<T> wrapWithTraceId(java.util.concurrent.Callable<T> callable) {
        String traceId = TraceIdUtil.getTraceId();
        return () -> {
            TraceIdContext context = new TraceIdContext(traceId);
            return context.callWithTraceId(callable);
        };
    }
}
