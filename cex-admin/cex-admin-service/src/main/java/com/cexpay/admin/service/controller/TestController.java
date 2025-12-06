package com.cexpay.admin.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 */
@RestController
@ResponseBody
@RequestMapping("/admin/test")
public class TestController {

    @GetMapping("/hello")
    public String test() {
        return "hello";
    }
}
