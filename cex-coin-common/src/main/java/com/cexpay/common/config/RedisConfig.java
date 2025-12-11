//package com.cexpay.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * Redis配置类
// * <p>
// * 用于配置Redis相关的参数和连接信息。
// * </p>
// */
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        // 在这里可以添加自定义的Redis配置，例如序列化器等
//        StringRedisSerializer serializer = new StringRedisSerializer();
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();template.setKeySerializer(serializer);
//
//        template.setValueSerializer(genericJackson2JsonRedisSerializer);
//        template.setHashKeySerializer(serializer);
//        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
//        return template;
//    }
//}
