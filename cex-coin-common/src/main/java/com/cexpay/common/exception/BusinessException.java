package com.cexpay.common.exception;

import com.cexpay.common.constant.ErrorCode;

/**
 * 业务异常类
 * <p>
 * 用于表示业务逻辑处理中发生的异常。与普通异常不同，业务异常需要返回给客户端
 * 一个合适的错误码和错误消息，而不是在服务器中处理。
 * </p>
 * <p>
 * 典型用途:
 * - 余额不足
 * - 账户已冻结
 * - 数据验证失败
 * - 业务规则违反
 * </p>
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误代码
     * 用于标识具体的业务错误类型
     */
    private Integer code;

    /**
     * 错误消息
     * 用于描述业务错误的详细内容
     */
    private String message;

    /**
     * 构造函数 - 仅指定错误消息
     * <p>
     * 使用默认的业务错误码 {@link ErrorCode#BUSINESS_ERROR}
     * </p>
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ErrorCode.BUSINESS_ERROR;
        this.message = message;
    }

    /**
     * 构造函数 - 指定错误码和错误消息
     * <p>
     * 用于精确指定业务错误的类型
     * </p>
     *
     * @param code    错误代码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数 - 包含原始异常
     * <p>
     * 用于包装其他异常为业务异常，便于异常链的追踪
     * </p>
     *
     * @param message 错误消息
     * @param cause   原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ErrorCode.BUSINESS_ERROR;
        this.message = message;
    }

    /**
     * 构造函数 - 包含错误码和原始异常
     *
     * @param code    错误代码
     * @param message 错误消息
     * @param cause   原始异常
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误代码
     *
     * @return 错误代码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置错误代码
     *
     * @param code 错误代码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 设置错误消息
     *
     * @param message 错误消息
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
