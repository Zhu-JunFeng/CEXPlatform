package com.cexpay.matching;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Matching Engine 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.cexpay.matching.mapper")
public class CexMatchingEngineApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CexMatchingEngineApplication.class, args);
    }
}
