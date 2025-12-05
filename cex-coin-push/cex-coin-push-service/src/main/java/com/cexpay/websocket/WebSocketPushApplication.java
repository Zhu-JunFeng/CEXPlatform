package com.cexpay.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * WebSocket Push Service 启动类
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class WebSocketPushApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WebSocketPushApplication.class, args);
    }
}
