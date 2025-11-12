package com.cexpay.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Wallet Service 启动类
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class WalletServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication.class, args);
    }
}
