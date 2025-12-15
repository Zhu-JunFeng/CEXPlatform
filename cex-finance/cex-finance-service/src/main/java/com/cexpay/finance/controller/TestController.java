package com.cexpay.finance.controller;

import com.cexpay.common.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/finance")
public class TestController {

    @GetMapping("/test")
    public ApiResponse test(){
        return ApiResponse.success("aaa");
    }
}
