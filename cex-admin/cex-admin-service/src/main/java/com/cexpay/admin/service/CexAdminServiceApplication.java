package com.cexpay.admin.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cexpay.common")
public class CexAdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CexAdminServiceApplication.class, args);
    }

}
