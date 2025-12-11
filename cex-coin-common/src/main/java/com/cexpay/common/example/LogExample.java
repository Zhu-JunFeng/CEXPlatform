package com.cexpay.common.example;

import com.cexpay.common.annotation.Log;
import com.cexpay.common.model.ApiResponse;
import org.springframework.web.bind.annotation.*;

/**
 * 日志使用示例
 */
@RestController
@RequestMapping("/api")
public class LogExample {

    /**
     * 示例1: 基础使用
     */
    @Log(title = "用户管理", desc = "查询用户")
    @GetMapping("/users/{id}")
    public ApiResponse<UserDto> getUser(@PathVariable Long id) {
        return ApiResponse.success(new UserDto(id, "张三"));
    }

    /**
     * 示例2: 脱敏敏感字段
     */
    @Log(title = "用户管理", desc = "创建用户",
         sensitiveFields = "password,phoneNumber,idCard")
    @PostMapping("/users")
    public ApiResponse<Long> createUser(@RequestBody UserCreateRequest req) {
        return ApiResponse.success(1L);
    }

    /**
     * 示例3: DEBUG 级别
     */
    @Log(level = Log.LogLevel.DEBUG)
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest req) {
        return ApiResponse.success("token");
    }

    /**
     * 示例4: WARN 级别
     */
    @Log(level = Log.LogLevel.WARN)
    @PutMapping("/config")
    public ApiResponse<Void> updateConfig(@RequestBody String config) {
        return ApiResponse.success("success");
    }

    /**
     * 示例5: ERROR 级别
     */
    @Log(level = Log.LogLevel.ERROR)
    @DeleteMapping("/users/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        return ApiResponse.success("deleted");
    }

    /**
     * 示例6: 不记录入参（大文件）
     */
    @Log(recordParams = false)
    @PostMapping("/upload")
    public ApiResponse<String> upload(@RequestBody byte[] file) {
        return ApiResponse.success("ok");
    }

    /**
     * 示例7: 不记录出参
     */
    @Log(recordResult = false)
    @PostMapping("/export")
    public void export() {
        // 导出逻辑
    }

    /**
     * 示例8: 异步操作
     */
    @Log(isAsync = true)
    @PostMapping("/send-email")
    public ApiResponse<Void> sendEmail(@RequestParam String email) {
        // 异步发送
        return ApiResponse.success("sending");
    }

    // ==================== DTO ====================

    static class UserDto {
        Long id;
        String name;

        UserDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class UserCreateRequest {
        String username;
        String password;
        String phoneNumber;
        String idCard;
    }

    static class LoginRequest {
        String username;
        String password;
    }
}
