package com.cexpay.common.utils;

import com.cexpay.common.exception.BusinessException;

/**
 * 断言工具类
 * <p>
 * 用于在业务逻辑中进行条件判断，当条件不满足时抛出业务异常。
 * 简化业务代码中的参数验证和业务规则验证。
 * </p>
 * <p>
 * 用法示例：
 * <pre>
 *   AssertUtil.notNull(user, "用户不存在");
 *   AssertUtil.isTrue(balance.compareTo(amount) >= 0, ErrorCode.INSUFFICIENT_BALANCE, "余额不足");
 *   AssertUtil.notEmpty(username, "用户名不能为空");
 * </pre>
 * </p>
 */
public class AssertUtil {

    /**
     * 私有构造函数，防止实例化
     */
    private AssertUtil() {
        throw new AssertionError("Cannot instantiate utils class");
    }

    /**
     * 断言对象不为null，否则抛出业务异常
     *
     * @param obj     要检查的对象
     * @param message 异常消息
     * @throws BusinessException 当对象为null时抛出
     */
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言对象不为null，否则抛出业务异常（指定错误码）
     *
     * @param obj     要检查的对象
     * @param code    错误码
     * @param message 异常消息
     * @throws BusinessException 当对象为null时抛出
     */
    public static void notNull(Object obj, Integer code, String message) {
        if (obj == null) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言字符串不为空或null，否则抛出业务异常
     *
     * @param str     要检查的字符串
     * @param message 异常消息
     * @throws BusinessException 当字符串为null或空时抛出
     */
    public static void notEmpty(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言字符串不为空或null，否则抛出业务异常（指定错误码）
     *
     * @param str     要检查的字符串
     * @param code    错误码
     * @param message 异常消息
     * @throws BusinessException 当字符串为null或空时抛出
     */
    public static void notEmpty(String str, Integer code, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言条件为真，否则抛出业务异常
     *
     * @param condition 条件
     * @param message   异常消息
     * @throws BusinessException 当条件为false时抛出
     */
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言条件为真，否则抛出业务异常（指定错误码）
     *
     * @param condition 条件
     * @param code      错误码
     * @param message   异常消息
     * @throws BusinessException 当条件为false时抛出
     */
    public static void isTrue(boolean condition, Integer code, String message) {
        if (!condition) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言条件为假，否则抛出业务异常
     *
     * @param condition 条件
     * @param message   异常消息
     * @throws BusinessException 当条件为true时抛出
     */
    public static void isFalse(boolean condition, String message) {
        if (condition) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言条件为假，否则抛出业务异常（指定错误码）
     *
     * @param condition 条件
     * @param code      错误码
     * @param message   异常消息
     * @throws BusinessException 当条件为true时抛出
     */
    public static void isFalse(boolean condition, Integer code, String message) {
        if (condition) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言两个对象相等，否则抛出业务异常
     *
     * @param actual   实际值
     * @param expected 期望值
     * @param message  异常消息
     * @throws BusinessException 当两个对象不相等时抛出
     */
    public static void equals(Object actual, Object expected, String message) {
        if ((actual == null && expected != null) || (actual != null && !actual.equals(expected))) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言两个对象不相等，否则抛出业务异常
     *
     * @param actual   实际值
     * @param expected 期望值
     * @param message  异常消息
     * @throws BusinessException 当两个对象相等时抛出
     */
    public static void notEquals(Object actual, Object expected, String message) {
        if ((actual == null && expected == null) || (actual != null && actual.equals(expected))) {
            throw new BusinessException(message);
        }
    }
}
