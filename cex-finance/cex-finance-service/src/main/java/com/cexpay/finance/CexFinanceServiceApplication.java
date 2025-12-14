package com.cexpay.finance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cexpay.finance.mapper")
@SpringBootApplication
public class CexFinanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CexFinanceServiceApplication.class, args);
    }

}
