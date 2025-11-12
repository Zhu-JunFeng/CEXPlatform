package com.cexpay.user.service;

import com.cexpay.user.dto.LoginRequest;
import com.cexpay.user.dto.LoginResponse;
import com.cexpay.user.dto.RegisterRequest;
import com.cexpay.user.domain.User;

/**
 * 用户业务接口
 */
public interface IUserService {
    
    /**
     * 用户注册
     */
    User register(RegisterRequest request);
    
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);
    
    /**
     * 根据ID查询用户
     */
    User getUserById(Long userId);
}
