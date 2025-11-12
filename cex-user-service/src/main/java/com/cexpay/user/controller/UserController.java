package com.cexpay.user.controller;

import com.cexpay.common.dto.ApiResponse;
import com.cexpay.user.domain.User;
import com.cexpay.user.dto.LoginRequest;
import com.cexpay.user.dto.LoginResponse;
import com.cexpay.user.dto.RegisterRequest;
import com.cexpay.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Management", description = "用户管理相关接口")
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册接口")
    public ApiResponse<User> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ApiResponse.success("注册成功", user);
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，返回JWT token")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ApiResponse.success("登录成功", response);
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/info/{userId}")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户信息")
    public ApiResponse<User> getUserInfo(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ApiResponse.success(user);
    }
    
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出接口")
    public ApiResponse<Void> logout() {
        return ApiResponse.success("登出成功", null);
    }
}
