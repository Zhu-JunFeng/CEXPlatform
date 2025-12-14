package com.cexpay.exchange.controller;

import com.cexpay.common.annotation.Log;
import com.cexpay.common.model.ApiResponse;
//import com.cexpay.exchange.interceptor.CexAdminInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 */
@Slf4j
@RestController
@ResponseBody
@RequestMapping("/exchange/test")
public class TestController {

//    @Autowired
//    private CexAdminInterceptor cexAdminInterceptor;

    @GetMapping("/exchange-hello")
    @Log(title = "用户管理", desc = "查询用户列表")
    public Object test() {
        return "cexAdminInterceptor.hello()";
    }
}
