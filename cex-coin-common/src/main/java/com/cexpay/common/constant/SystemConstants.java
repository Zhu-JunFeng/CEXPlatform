package com.cexpay.common.constant;

/**
 * 系统常用常量类
 * <p>
 * 定义整个系统中使用的通用常量值，避免使用魔法数字和硬编码字符串。
 * </p>
 */
public class SystemConstants {

    /**
     * 私有构造函数，防止实例化
     */
    private SystemConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }

    // ==================== 系统状态常量 ====================

    /**
     * 启用状态
     */
    public static final Integer STATUS_ENABLED = 1;

    /**
     * 禁用状态
     */
    public static final Integer STATUS_DISABLED = 0;

    /**
     * 删除状态
     */
    public static final Integer STATUS_DELETED = -1;

    // ==================== 布尔常量 ====================

    /**
     * 是
     */
    public static final Integer YES = 1;

    /**
     * 否
     */
    public static final Integer NO = 0;

    // ==================== 分页默认值 ====================

    /**
     * 默认页码
     */
    public static final Integer DEFAULT_PAGE = 1;

    /**
     * 默认每页条数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 20;

    /**
     * 最大每页条数
     */
    public static final Integer MAX_PAGE_SIZE = 1000;

    /**
     * 最小每页条数
     */
    public static final Integer MIN_PAGE_SIZE = 1;

    // ==================== 字符串常量 ====================

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 冒号
     */
    public static final String COLON = ":";

    /**
     * 分号
     */
    public static final String SEMICOLON = ";";

    /**
     * 下划线
     */
    public static final String UNDERSCORE = "_";

    /**
     * 连字符
     */
    public static final String HYPHEN = "-";

    /**
     * 点
     */
    public static final String POINT = ".";

    /**
     * 斜杠
     */
    public static final String SLASH = "/";

    /**
     * 反斜杠
     */
    public static final String BACKSLASH = "\\\\";

    /**
     * 空格
     */
    public static final String SPACE = " ";

    /**
     * TAB制表符
     */
    public static final String TAB = "\\t";

    /**
     * 换行符
     */
    public static final String NEWLINE = "\\n";

    /**
     * 回车符
     */
    public static final String CARRIAGE_RETURN = "\\r";

    // ==================== 时间相关常量（毫秒）====================

    /**
     * 一秒的毫秒数
     */
    public static final long ONE_SECOND = 1000L;

    /**
     * 一分钟的毫秒数
     */
    public static final long ONE_MINUTE = 60 * ONE_SECOND;

    /**
     * 一小时的毫秒数
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 一天的毫秒数
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * 一周的毫秒数
     */
    public static final long ONE_WEEK = 7 * ONE_DAY;

    /**
     * 一个月的毫秒数（按30天计算）
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 一年的毫秒数（按365天计算）
     */
    public static final long ONE_YEAR = 365 * ONE_DAY;

    // ==================== 数值常量 ====================

    /**
     * 零值
     */
    public static final Integer ZERO = 0;

    /**
     * 一
     */
    public static final Integer ONE = 1;

    /**
     * 二
     */
    public static final Integer TWO = 2;

    /**
     * 三
     */
    public static final Integer THREE = 3;

    /**
     * 负一
     */
    public static final Integer NEGATIVE_ONE = -1;

    /**
     * 百
     */
    public static final Integer HUNDRED = 100;

    /**
     * 千
     */
    public static final Integer THOUSAND = 1000;

    /**
     * 万
     */
    public static final Integer TEN_THOUSAND = 10000;

    /**
     * 百万
     */
    public static final Integer MILLION = 1000000;

    // ==================== 长度限制 ====================

    /**
     * 用户名最小长度
     */
    public static final Integer USERNAME_MIN_LENGTH = 3;

    /**
     * 用户名最大长度
     */
    public static final Integer USERNAME_MAX_LENGTH = 32;

    /**
     * 密码最小长度
     */
    public static final Integer PASSWORD_MIN_LENGTH = 6;

    /**
     * 密码最大长度
     */
    public static final Integer PASSWORD_MAX_LENGTH = 128;

    /**
     * 手机号长度
     */
    public static final Integer PHONE_LENGTH = 11;

    /**
     * 身份证长度
     */
    public static final Integer ID_CARD_LENGTH = 18;
}
