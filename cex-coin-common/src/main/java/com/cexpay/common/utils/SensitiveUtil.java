package com.cexpay.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 敏感信息脱敏工具类
 * 
 * 支持脱敏的敏感字段：
 * - 密码、PIN码、支付密码
 * - 手机号、身份证号、银行卡号
 * - Token、AccessKey、SecretKey
 * - 验证码、邮箱、家庭住址
 * 
 * @author cexpay
 * @date 2025-12-06
 */
public class SensitiveUtil {

    /**
     * 默认脱敏字段集合
     */
    private static final Set<String> DEFAULT_SENSITIVE_FIELDS = new HashSet<>();

    static {
        // 密码相关
        DEFAULT_SENSITIVE_FIELDS.add("password");
        DEFAULT_SENSITIVE_FIELDS.add("passwd");
        DEFAULT_SENSITIVE_FIELDS.add("pwd");
        DEFAULT_SENSITIVE_FIELDS.add("payPassword");
        DEFAULT_SENSITIVE_FIELDS.add("pinCode");

        // 身份信息
        DEFAULT_SENSITIVE_FIELDS.add("phoneNumber");
        DEFAULT_SENSITIVE_FIELDS.add("phone");
        DEFAULT_SENSITIVE_FIELDS.add("mobileNumber");
        DEFAULT_SENSITIVE_FIELDS.add("mobile");
        DEFAULT_SENSITIVE_FIELDS.add("idCard");
        DEFAULT_SENSITIVE_FIELDS.add("identityCard");
        DEFAULT_SENSITIVE_FIELDS.add("bankCardNo");
        DEFAULT_SENSITIVE_FIELDS.add("cardNumber");
        DEFAULT_SENSITIVE_FIELDS.add("email");
        DEFAULT_SENSITIVE_FIELDS.add("mailbox");

        // Token和密钥
        DEFAULT_SENSITIVE_FIELDS.add("token");
        DEFAULT_SENSITIVE_FIELDS.add("accessToken");
        DEFAULT_SENSITIVE_FIELDS.add("refreshToken");
        DEFAULT_SENSITIVE_FIELDS.add("accessKey");
        DEFAULT_SENSITIVE_FIELDS.add("secretKey");
        DEFAULT_SENSITIVE_FIELDS.add("secret");

        // 验证码
        DEFAULT_SENSITIVE_FIELDS.add("verifyCode");
        DEFAULT_SENSITIVE_FIELDS.add("smsCode");
        DEFAULT_SENSITIVE_FIELDS.add("captcha");
        DEFAULT_SENSITIVE_FIELDS.add("code");

        // 其他
        DEFAULT_SENSITIVE_FIELDS.add("address");
        DEFAULT_SENSITIVE_FIELDS.add("apiKey");
    }

    /**
     * 脱敏对象（递归处理）
     * 
     * @param data 待脱敏数据
     * @param sensitiveFields 敏感字段，为空则使用默认集合
     * @return 脱敏后的数据
     */
    public static Object desensitize(Object data, String sensitiveFields) {
        if (data == null) {
            return null;
        }

        Set<String> fields = parseFields(sensitiveFields);
        if (fields.isEmpty()) {
            fields = DEFAULT_SENSITIVE_FIELDS;
        }

        return desensitizeObject(data, fields);
    }

    /**
     * 脱敏对象（使用默认敏感字段）
     * 
     * @param data 待脱敏数据
     * @return 脱敏后的数据
     */
    public static Object desensitize(Object data) {
        return desensitize(data, "");
    }

    /**
     * 递归脱敏对象
     */
    @SuppressWarnings("unchecked")
    private static Object desensitizeObject(Object obj, Set<String> sensitiveFields) {
        if (obj == null) {
            return null;
        }

        // 如果是字符串，直接返回
        if (obj instanceof String) {
            return obj;
        }

        // 如果是JSONObject，处理每个字段
        if (obj instanceof JSONObject) {
            JSONObject json = (JSONObject) obj;
            for (String key : json.keySet()) {
                if (sensitiveFields.contains(key.toLowerCase())) {
                    json.put(key, "***");
                } else {
                    Object value = json.get(key);
                    if (isComplexType(value)) {
                        json.put(key, desensitizeObject(value, sensitiveFields));
                    }
                }
            }
            return json;
        }

        // 如果是Map，处理每个条目
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            for (String key : map.keySet()) {
                if (sensitiveFields.contains(key.toLowerCase())) {
                    map.put(key, "***");
                } else {
                    Object value = map.get(key);
                    if (isComplexType(value)) {
                        map.put(key, desensitizeObject(value, sensitiveFields));
                    }
                }
            }
            return map;
        }

        // 对于其他对象，转换为JSON处理
        try {
            String jsonStr = JSON.toJSONString(obj);
            JSONObject json = JSON.parseObject(jsonStr);
            return desensitizeObject(json, sensitiveFields);
        } catch (Exception e) {
            return obj;
        }
    }

    /**
     * 判断是否为复杂类型
     */
    private static boolean isComplexType(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof Map || obj instanceof JSONObject;
    }

    /**
     * 解析敏感字段字符串
     */
    private static Set<String> parseFields(String sensitiveFields) {
        Set<String> fields = new HashSet<>();
        if (StringUtils.isBlank(sensitiveFields)) {
            return fields;
        }

        String[] fieldArray = sensitiveFields.split(",");
        for (String field : fieldArray) {
            if (StringUtils.isNotBlank(field)) {
                fields.add(field.trim().toLowerCase());
            }
        }
        return fields;
    }

    /**
     * 脱敏字符串 - 手机号
     * 显示格式：138****6666
     */
    public static String desensitizePhone(String phone) {
        if (StringUtils.isBlank(phone) || phone.length() < 7) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 脱敏字符串 - 身份证号
     * 显示格式：110101****0000
     */
    public static String desensitizeIdCard(String idCard) {
        if (StringUtils.isBlank(idCard) || idCard.length() < 8) {
            return idCard;
        }
        return idCard.replaceAll("(?<=^.{6}).*(?=.{4}$)", "****");
    }

    /**
     * 脱敏字符串 - 银行卡号
     * 显示格式：6222 **** **** 6666
     */
    public static String desensitizeBankCard(String cardNo) {
        if (StringUtils.isBlank(cardNo) || cardNo.length() < 8) {
            return cardNo;
        }
        return cardNo.replaceAll("(?<=^.{4}).*(?=.{4}$)", "****");
    }

    /**
     * 脱敏字符串 - 邮箱
     * 显示格式：ab****@example.com
     */
    public static String desensitizeEmail(String email) {
        if (StringUtils.isBlank(email) || !email.contains("@")) {
            return email;
        }
        int index = email.indexOf("@");
        if (index <= 2) {
            return email;
        }
        String prefix = email.substring(0, 2);
        String suffix = email.substring(index);
        return prefix + "****" + suffix;
    }

    /**
     * 脱敏字符串 - 默认格式
     * 只保留首尾各一个字符
     */
    public static String desensitizeDefault(String text) {
        if (StringUtils.isBlank(text) || text.length() <= 2) {
            return text;
        }
        return text.charAt(0) + "****" + text.charAt(text.length() - 1);
    }
}
