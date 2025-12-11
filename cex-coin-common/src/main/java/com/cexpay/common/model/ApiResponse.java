package com.cexpay.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API响应数据传输对象
 * <p>
 * 用于标准化系统内所有REST API的响应格式，包含响应状态码、消息、数据和时间戳。
 * 支持泛型，可以包装任意类型的响应数据。
 * </p>
 *
 * @param <T> 响应数据类型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * 响应状态码
     * 200: 成功
     * 4xx: 客户端错误
     * 5xx: 服务器错误
     * 6xx: 业务错误
     */
    private Integer code;

    /**
     * 响应消息
     * 通常用于错误说明或成功提示
     */
    private String message;

    /**
     * 响应数据体
     * 成功时包含业务数据，失败时可为null
     */
    private T data;

    /**
     * 响应时间戳（毫秒）
     * 用于追踪和审计
     */
    private Long timestamp;

    /**
     * 构建成功响应（仅包含数据）
     * <p>
     * 使用默认消息"success"、状态码200
     * </p>
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return ApiResponse对象
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message("success")
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 构建成功响应（包含自定义消息）
     * <p>
     * 使用自定义消息、状态码200、响应数据
     * </p>
     *
     * @param message 自定义消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return ApiResponse对象
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 构建成功响应（仅返回消息）
     * <p>
     * 用于无数据的成功操作，如删除、更新等
     * </p>
     *
     * @param message 消息内容
     * @param <T>     数据类型
     * @return ApiResponse对象
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .code(200)
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 构建失败响应（包含状态码和消息）
     * <p>
     * 用于业务错误或异常情况
     * </p>
     *
     * @param code    错误状态码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return ApiResponse对象
     */
    public static <T> ApiResponse<T> fail(Integer code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 构建失败响应（仅包含消息）
     * <p>
     * 使用默认状态码500（服务器错误）
     * </p>
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return ApiResponse对象
     */
    public static <T> ApiResponse<T> fail(String message) {
        return ApiResponse.<T>builder()
                .code(500)
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 判断响应是否成功
     *
     * @return true表示成功（状态码为200），false表示失败
     */
    public boolean isSuccess() {
        return 200 == this.code;
    }
}
