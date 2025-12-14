//package com.cexpay.common.utils;
//
//import org.slf4j.MDC;
//import java.util.UUID;
//
///**
// * TraceId 工具类
// *
// * 用于在整个请求链路中传递唯一的追踪ID
// * 支持跨线程、跨服务的链路追踪
// *
// * 使用方式：
// * 1. 在请求入口处生成或获取 traceId
// * 2. 将 traceId 存入 MDC
// * 3. logback 会自动在日志中输出 traceId
// * 4. 在请求结束时清除 MDC
// *
// * @author cexpay
// * @date 2025-12-06
// */
//public class TraceIdUtil {
//
//    /**
//     * MDC 中 traceId 的 key
//     */
//    public static final String TRACE_ID_KEY = "traceId";
//
//    /**
//     * 生成新的 traceId
//     * 格式: UUID
//     *
//     * @return traceId
//     */
//    public static String generateTraceId() {
//        return UUID.randomUUID().toString().replace("-", "");
//    }
//
//    /**
//     * 生成指定前缀的 traceId
//     *
//     * @param prefix 前缀
//     * @return traceId
//     */
//    public static String generateTraceIdWithPrefix(String prefix) {
//        return prefix + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
//    }
//
//    /**
//     * 将 traceId 设置到 MDC
//     *
//     * @param traceId 链路追踪ID
//     */
//    public static void setTraceId(String traceId) {
//        MDC.put(TRACE_ID_KEY, traceId);
//    }
//
//    /**
//     * 获取当前 MDC 中的 traceId
//     * 如果不存在则生成新的
//     *
//     * @return traceId
//     */
//    public static String getTraceId() {
//        String traceId = MDC.get(TRACE_ID_KEY);
//        if (traceId == null) {
//            traceId = generateTraceId();
//            setTraceId(traceId);
//        }
//        return traceId;
//    }
//
//    /**
//     * 清除 MDC 中的 traceId
//     */
//    public static void removeTraceId() {
//        MDC.remove(TRACE_ID_KEY);
//    }
//
//    /**
//     * 清除所有 MDC 数据
//     */
//    public static void clearMDC() {
//        MDC.clear();
//    }
//
//    /**
//     * 检查是否已设置 traceId
//     *
//     * @return true 如果已设置
//     */
//    public static boolean hasTraceId() {
//        return MDC.get(TRACE_ID_KEY) != null;
//    }
//}
