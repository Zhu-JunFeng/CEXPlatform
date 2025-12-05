package com.cexpay.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
    /**
     * 标准化BigDecimal数值，保留8位小数，向下取整
     *
     * @param v 输入的BigDecimal数值
     * @return 标准化后的BigDecimal数值
     */
    private static BigDecimal norm(BigDecimal v) {
        return v == null ? null : v.setScale(8, RoundingMode.DOWN);
    }

// 避免 BigDecimal 比较时出现 0.1000000 != 0.1 这种 scale 不一致问题。
    /**
     * 对两个BigDecimal数值进行加法运算，结果保留8位小数，向下取整
     *
     * @param v1 第一个加数
     * @param v2 第二个加数
     * @return 加法运算结果
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return norm(v1).add(norm(v2));
    }

}
