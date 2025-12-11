package com.cexpay.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.cexpay.exchange.interceptor")
public class CexExchangeEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CexExchangeEngineApplication.class, args);
    }

}
