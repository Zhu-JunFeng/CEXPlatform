//package com.cexpay.common.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.TimeZone;
//
///**
// * Jackson配置类
// * <p>
// * 用于配置Jackson的序列化和反序列化行为，以满足系统对JSON处理的需求。
// * </p>
// */
//@Configuration
//public class JacksonConfig {
//    // 这里可以添加自定义的Jackson配置，例如日期格式化、属性命名策略等
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 在这里添加自定义配置，例如：
//        objectMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); // 设置日期格式
//        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 设置时区为上海时间
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(Long.class, ToStringSerializer.instance);
//        objectMapper.registerModule(module); // 支持Long转String，防止前端精度丢失
//        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8日期时间类型
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY); // 设置所有属性可见
//        return objectMapper;
//    }
//
//}
