package com.cexpay.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 高精度数值计算工具类
 * <p>
 * 用于处理涉及货币、价格等精度要求高的数值计算。
 * 所有计算都保留8位小数，采用向下取整的方式，避免浮点数精度问题。
 * </p>
 * <p>
 * 特点:
 * - 统一的小数精度（8位）
 * - 统一的舍入模式（向下取整，保护系统利益，避免浮点数精度问题导致的超额返还
 * - 避免BigDecimal比较时的scale不一致问题
 * - 支持null值的安全处理
 * </p>
 */
public class BigDecimalUtil {

    /**
     * 默认的小数位数
     * 用于数字货币，保留8位小数精度
     */
    private static final int DEFAULT_SCALE = 8;

    /**
     * 默认的舍入模式 - 向下取整
     * 用于保护系统利益，避免浮点数精度问题导致的超额返还
     */
    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.DOWN;

    /**
     * 私有构造函数，防止实例化
     */
    private BigDecimalUtil() {
        throw new AssertionError("Cannot instantiate utils class");
    }

    /**
     * 标准化BigDecimal数值
     * <p>
     * 将BigDecimal数值统一格式化为指定的小数位数和舍入模式。
     * 避免比较时出现 0.1000000 != 0.1 这种scale不一致问题。
     * </p>
     *
     * @param v 输入的BigDecimal数值
     * @return 标准化后的BigDecimal数值，如果输入为null则返回null
     */
    private static BigDecimal norm(BigDecimal v) {
        return v == null ? null : v.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 两个数值的加法运算
     * <p>
     * 对两个BigDecimal数值进行加法，结果自动进行标准化处理。
     * </p>
     *
     * @param v1 第一个加数
     * @param v2 第二个加数
     * @return 加法运算结果（标准化后的BigDecimal）
     * @throws NullPointerException 如果任意参数为null
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return norm(norm(v1).add(norm(v2)));
    }

    /**
     * 两个数值的减法运算
     * <p>
     * 对两个BigDecimal数值进行减法，结果自动进行标准化处理。
     * </p>
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 减法运算结果（标准化后的BigDecimal）
     * @throws NullPointerException 如果任意参数为null
     */
    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
        return norm(norm(v1).subtract(norm(v2)));
    }

    /**
     * 两个数值的乘法运算
     * <p>
     * 对两个BigDecimal数值进行乘法，结果自动进行标准化处理。
     * </p>
     *
     * @param v1 第一个乘数
     * @param v2 第二个乘数
     * @return 乘法运算结果（标准化后的BigDecimal）
     * @throws NullPointerException 如果任意参数为null
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        return norm(norm(v1).multiply(norm(v2)));
    }

    /**
     * 两个数值的除法运算
     * <p>
     * 对两个BigDecimal数值进行除法，结果自动进行标准化处理。
     * </p>
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 除法运算结果（标准化后的BigDecimal）
     * @throws NullPointerException 如果任意参数为null
     * @throws ArithmeticException  如果除数为0
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        return norm(norm(v1).divide(norm(v2), DEFAULT_SCALE, DEFAULT_ROUNDING_MODE));
    }

    /**
     * 比较两个数值的大小
     * <p>
     * 先对两个数值进行标准化处理，然后进行比较，避免scale不一致导致的比较错误。
     * </p>
     *
     * @param v1 第一个数值
     * @param v2 第二个数值
     * @return 如果v1 > v2返回1，v1 == v2返回0，v1 < v2返回-1
     * @throws NullPointerException 如果任意参数为null
     */
    public static int compare(BigDecimal v1, BigDecimal v2) {
        return norm(v1).compareTo(norm(v2));
    }

    /**
     * 检查数值是否为正数
     *
     * @param v 要检查的数值
     * @return 如果数值大于0返回true，否则返回false
     * @throws NullPointerException 如果参数为null
     */
    public static boolean isPositive(BigDecimal v) {
        return compare(v, BigDecimal.ZERO) > 0;
    }

    /**
     * 检查数值是否为负数
     *
     * @param v 要检查的数值
     * @return 如果数值小于0返回true，否则返回false
     * @throws NullPointerException 如果参数为null
     */
    public static boolean isNegative(BigDecimal v) {
        return compare(v, BigDecimal.ZERO) < 0;
    }

    /**
     * 检查数值是否为零
     *
     * @param v 要检查的数值
     * @return 如果数值等于0返回true，否则返回false
     * @throws NullPointerException 如果参数为null
     */
    public static boolean isZero(BigDecimal v) {
        return compare(v, BigDecimal.ZERO) == 0;
    }

    /**
     * 获取两个数值中的较大值
     *
     * @param v1 第一个数值
     * @param v2 第二个数值
     * @return 两者中的较大值
     * @throws NullPointerException 如果任意参数为null
     */
    public static BigDecimal max(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2) >= 0 ? v1 : v2;
    }

    /**
     * 获取两个数值中的较小值
     *
     * @param v1 第一个数值
     * @param v2 第二个数值
     * @return 两者中的较小值
     * @throws NullPointerException 如果任意参数为null
     */
    public static BigDecimal min(BigDecimal v1, BigDecimal v2) {
        return compare(v1, v2) <= 0 ? v1 : v2;
    }

    /**
     * 将字符串转换为标准化的BigDecimal
     *
     * @param value 字符串数值
     * @return 标准化后的BigDecimal，如果输入为null或空字符串则返回BigDecimal.ZERO
     */
    public static BigDecimal ofString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return norm(new BigDecimal(value));
    }

    /**
     * 将long转换为标准化的BigDecimal
     *
     * @param value long数值
     * @return 标准化后的BigDecimal
     */
    public static BigDecimal ofLong(long value) {
        return norm(BigDecimal.valueOf(value));
    }

    /**
     * 将double转换为标准化的BigDecimal
     * <p>
     * 注意：使用BigDecimal.valueOf()而不是new BigDecimal(double)
     * 以避免浮点数精度问题
     * </p>
     *
     * @param value double数值
     * @return 标准化后的BigDecimal
     */
    public static BigDecimal ofDouble(double value) {
        return norm(BigDecimal.valueOf(value));
    }
}
