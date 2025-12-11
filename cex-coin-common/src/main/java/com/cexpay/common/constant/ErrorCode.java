package com.cexpay.common.constant;

/**
 * 错误码常量类
 * <p>
 * 定义系统中统一的错误码标准，用于标识不同类型的错误和异常情况。
 * 遵循HTTP状态码规范，扩展业务相关的错误码。
 * </p>
 */
public class ErrorCode {

    // ==================== 客户端错误 (4xx) ====================

    /**
     * 参数错误 - 请求参数不合法或格式不正确
     */
    public static final Integer PARAM_ERROR = 400;

    /**
     * 未授权 - 请求未提供有效的身份认证凭证
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * 无权限 - 用户已认证但无权访问该资源
     */
    public static final Integer FORBIDDEN = 403;

    /**
     * 资源不存在 - 请求的资源不存在
     */
    public static final Integer NOT_FOUND = 404;

    /**
     * 请求冲突 - 业务逻辑冲突（如余额不足等）
     */
    public static final Integer CONFLICT = 409;

    // ==================== 服务器错误 (5xx) ====================

    /**
     * 服务器内部错误 - 系统发生未预期的错误
     */
    public static final Integer SERVER_ERROR = 500;

    /**
     * 服务不可用 - 服务器暂时无法处理请求
     */
    public static final Integer SERVICE_UNAVAILABLE = 503;

    // ==================== 业务错误 (6xx) ====================

    /**
     * 业务错误 - 业务逻辑处理失败
     */
    public static final Integer BUSINESS_ERROR = 600;

    /**
     * 用户不存在 - 指定的用户不存在
     */
    public static final Integer USER_NOT_FOUND = 601;

    /**
     * 账户异常 - 账户状态异常（如被冻结、被禁用等）
     */
    public static final Integer ACCOUNT_ABNORMAL = 602;

    /**
     * 余额不足 - 账户余额不足以完成操作
     */
    public static final Integer INSUFFICIENT_BALANCE = 603;

    /**
     * 交易失败 - 交易处理失败
     */
    public static final Integer TRANSACTION_FAILED = 604;

    /**
     * 数据验证失败 - 数据校验不通过
     */
    public static final Integer DATA_VALIDATION_FAILED = 605;

    /**
     * 私有构造函数，防止实例化
     */
    private ErrorCode() {
        throw new AssertionError("Cannot instantiate error code class");
    }
}
