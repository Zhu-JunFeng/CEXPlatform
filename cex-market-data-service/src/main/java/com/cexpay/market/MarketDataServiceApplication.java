package com.cexpay.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Market Data Service 启动类
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class MarketDataServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MarketDataServiceApplication.class, args);
    }
}
