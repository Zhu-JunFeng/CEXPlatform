package com.cexpay.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cexpay.common.constant.ErrorCode;
import com.cexpay.common.exception.BusinessException;
import com.cexpay.user.domain.User;
import com.cexpay.user.dto.LoginRequest;
import com.cexpay.user.dto.LoginResponse;
import com.cexpay.user.dto.RegisterRequest;
import com.cexpay.user.mapper.UserMapper;
import com.cexpay.user.service.IUserService;
import com.cexpay.user.util.JwtUtil;
import com.cexpay.user.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户业务实现
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public User register(RegisterRequest request) {
        // 参数验证
        if (StringUtils.isBlank(request.getUsername()) || StringUtils.isBlank(request.getPassword())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户名和密码不能为空");
        }
        
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "两次输入的密码不一致");
        }
        
        // 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        if (userMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "用户名已存在");
        }
        
        // 创建用户
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(PasswordUtil.encrypt(request.getPassword()))
                .authStatus(0)
                .status(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        
        int result = userMapper.insert(user);
        if (result <= 0) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "用户注册失败");
        }
        
        log.info("用户注册成功: {}", request.getUsername());
        return user;
    }
    
    @Override
    public LoginResponse login(LoginRequest request) {
        // 参数验证
        if (StringUtils.isBlank(request.getUsername()) || StringUtils.isBlank(request.getPassword())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户名和密码不能为空");
        }
        
        // 查询用户
        User user = getUserByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }
        
        // 验证密码
        if (!PasswordUtil.verify(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }
        
        // 检查用户状态
        if (user.getStatus() != 0) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "用户已被冻结或加入黑名单");
        }
        
        // 生成JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        log.info("用户登录成功: {}", request.getUsername());
        
        return LoginResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .token(token)
                .expiresIn(24 * 60 * 60L)
                .build();
    }
    
    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }
    
    @Override
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }
}
