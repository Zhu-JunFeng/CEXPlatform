package com.cexpay.matching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Matching Engine 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MatchingEngineApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MatchingEngineApplication.class, args);
    }
}
