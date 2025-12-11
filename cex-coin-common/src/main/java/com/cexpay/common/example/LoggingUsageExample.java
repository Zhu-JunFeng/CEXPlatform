package com.cexpay.common.example;

import com.cexpay.common.annotation.Log;
import com.cexpay.common.model.ApiResponse;
import com.cexpay.common.task.TraceIdContext;
import com.cexpay.common.utils.TraceIdUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 日志系统完整使用示例
 * 
 * 包含：
 * 1. 基础使用 - 同步方法
 * 2. 异步使用 - 异步任务
 * 3. 微服务调用 - Feign 客户端
 * 4. 手动管理 - 自定义 traceId
 * 
 * @author cexpay
 * @date 2025-12-06
 */
@Slf4j
@RestController
@RequestMapping("/example")
public class LoggingUsageExample {

//    @Autowired
//    private AsyncService asyncService;

    // ==================== 示例1: 基础同步方法 ====================

    /**
     * 示例1.1: 最简单的使用方式
     * 
     * 功能：
     * - 自动记录入参和出参
     * - 自动记录执行时间
     * - 自动生成 traceId（如果没有则从请求头获取）
     * 
     * 日志输出：
     * 2025-12-06 10:30:45.123 [abc123def456] [main] INFO LoggingUsageExample - 
     * ╔══════════════════════════════════════════════════════════════╗
     * ║                        操作日志记录                           ║
     * ║ [TraceID]       abc123def456                                 ║
     * ║ [操作描述]      查询用户信息                                  ║
     * ...
     */
    @Log(title = "用户管理", desc = "查询用户信息")
    @GetMapping("/users/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
        log.info("开始查询用户，ID: {}", id);
        
        // 这里可以获取当前的 traceId
        String traceId = TraceIdUtil.getTraceId();
        log.info("当前 TraceId: {}", traceId);
        
        UserDto user = new UserDto();
        user.setId(id);
        user.setName("张三");
        user.setEmail("zhangsan@example.com");
        
        return ApiResponse.success(user);
    }

    /**
     * 示例1.2: 自定义敏感字段脱敏
     * 
     * 功能：
     * - password 字段显示为 ***
     * - phoneNumber 字段显示为 ***
     * - idCard 字段显示为 ***
     * 
     * 请求体（脱敏前）：
     * {
     *   "username": "zhangsan",
     *   "password": "123456",
     *   "phoneNumber": "13800138000",
     *   "idCard": "110101199003071234"
     * }
     * 
     * 日志输出（脱敏后）：
     * [入参] {"username":"zhangsan","password":"***","phoneNumber":"***","idCard":"***"}
     */
    @Log(title = "用户管理", desc = "创建用户",
         sensitiveFields = "password,phoneNumber,idCard")
    @PostMapping("/users")
    public ApiResponse<Long> createUser(@RequestBody UserCreateRequest request) {
        log.info("用户创建请求处理中");
        return ApiResponse.success(1L);
    }

    /**
     * 示例1.3: DEBUG 日志级别
     * 
     * 功能：
     * - 只在开发环境输出（dev 环境为 DEBUG）
     * - 包含详细的调试信息
     */
    @Log(title = "用户管理", desc = "用户登录",
         level = Log.LogLevel.DEBUG)
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        log.debug("执行登录逻辑");
        
        LoginResponse response = new LoginResponse();
        response.setUserId(1L);
        response.setToken("token_abc123");
        response.setExpiresIn(3600L);
        
        return ApiResponse.success(response);
    }

    /**
     * 示例1.4: WARN 日志级别 - 重要操作
     * 
     * 功能：
     * - 用于重要但不是错误的操作
     * - 可能需要人工审核的操作
     */
    @Log(title = "系统管理", desc = "修改系统配置",
         level = Log.LogLevel.WARN)
    @PutMapping("/config")
    public ApiResponse<Void> updateConfig(@RequestBody ConfigUpdateRequest request) {
        log.warn("系统配置被修改");
        return ApiResponse.success("配置更新成功");
    }

    /**
     * 示例1.5: ERROR 日志级别 - 关键操作
     * 
     * 功能：
     * - 用于关键操作（如删除）
     * - 所有异常也会以此级别记录
     */
    @Log(title = "权限管理", desc = "删除用户",
         level = Log.LogLevel.ERROR)
    @DeleteMapping("/users/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        log.error("用户被删除，ID: {}", id);
        return ApiResponse.success("用户已删除");
    }

    /**
     * 示例1.6: 禁用入参记录 - 处理大数据量
     * 
     * 功能：
     * - 当入参非常大时，禁用记录以减少日志体积
     * - 适用于文件上传等场景
     */
    @Log(title = "数据处理", desc = "批量导入数据",
         recordParams = false)
    @PostMapping("/batch-import")
    public ApiResponse<ImportResult> batchImport(@RequestBody byte[] fileContent) {
        log.info("批量导入开始，文件大小: {} bytes", fileContent.length);
        
        ImportResult result = new ImportResult();
        result.setSuccessCount(100);
        result.setFailureCount(0);
        
        return ApiResponse.success(result);
    }

    /**
     * 示例1.7: 禁用出参记录 - 返回敏感信息
     * 
     * 功能：
     * - 当返回值包含大量敏感信息时使用
     * - 只记录入参，不记录出参
     */
    @Log(title = "用户管理", desc = "导出用户列表",
         recordResult = false)
    @PostMapping("/export")
    public void exportUsers(@RequestBody ExportRequest request) {
        log.info("开始导出用户数据");
        // 返回文件流等
    }

    // ==================== 示例2: 异步方法 ====================

    /**
     * 示例2.1: 异步操作 - isAsync = true
     * 
     * 功能：
     * - 用于异步操作（如发送邮件、短信）
     * - 不记录返回值
     * 
     * 日志输出：
     * [traceId] 邮件发送请求收到
     * [traceId] 异步任务提交成功
     */
    @Log(title = "通知管理", desc = "发送验证码邮件", isAsync = true)
    @PostMapping("/send-email")
    public ApiResponse<Void> sendVerificationEmail(@RequestParam String email) {
        log.info("邮件发送请求收到: {}", email);
        
        // 提交异步任务
//        asyncService.sendEmail(email, TraceIdUtil.getTraceId());
        
        return ApiResponse.success("邮件正在发送中");
    }

    /**
     * 示例2.2: 异步服务实现
     * 
     * 使用 @Async 注解的异步方法
     * 需要在异步方法中保持 traceId
     */
    @Async
    public void sendEmailAsync(String email, String traceId) {
        // 方式1：使用 TraceIdContext 包装异步逻辑
        TraceIdContext context = new TraceIdContext(traceId);
        context.runWithTraceId(() -> {
            log.info("开始发送邮件到: {}", email);
            
            try {
                // 模拟发送邮件（耗时3秒）
                Thread.sleep(3000);
                log.info("邮件发送成功");
            } catch (InterruptedException e) {
                log.error("邮件发送失败", e);
            }
        });
    }

    /**
     * 示例2.3: 线程池中使用 traceId
     * 
     * 在自定义线程池中执行异步任务时，需要传递 traceId
     */
    public void processDataInThreadPool(String data) {
        // 在主线程中获取当前的 traceId
        String traceId = TraceIdUtil.getTraceId();
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // 方式1：使用 wrapWithTraceId
        executor.execute(TraceIdContext.wrapWithTraceId(() -> {
            log.info("线程池任务执行中，处理数据: {}", data);
        }));
        
        // 方式2：手动设置和清除
        executor.execute(() -> {
            try {
                TraceIdUtil.setTraceId(traceId);
                log.info("线程池任务执行中");
            } finally {
                TraceIdUtil.removeTraceId();
            }
        });
    }

    // ==================== 示例3: 微服务调用 ====================

    /**
     * 示例3.1: Feign 客户端调用
     * 
     * 功能：
     * - 自动在请求头中添加 traceId
     * - 实现跨微服务的链路追踪
     * 
     * 日志输出：
     * [订单服务] [traceId] 创建订单
     * [订单服务] [traceId] 调用用户服务
     * [用户服务] [traceId] 查询用户
     * [订单服务] [traceId] 创建订单完成
     */
    @Log(title = "订单管理", desc = "创建订单")
    @PostMapping("/orders")
    public ApiResponse<OrderDto> createOrder(@RequestBody OrderCreateRequest request) {
        log.info("开始创建订单");
        
        // 调用用户服务（会自动添加 traceId 到请求头）
        // ApiResponse<UserDto> userResp = userServiceClient.getUserById(request.getUserId());
        
        OrderDto order = new OrderDto();
        order.setId(1L);
        order.setUserId(request.getUserId());
        order.setAmount(request.getAmount());
        
        log.info("订单创建成功");
        return ApiResponse.success(order);
    }

    // ==================== 示例4: 手动管理 traceId ====================

    /**
     * 示例4.1: 手动生成特殊前缀的 traceId
     * 
     * 功能：
     * - 为不同类型的操作生成不同前缀的 traceId
     * - 便于日志查询和分析
     */
    @GetMapping("/batch-job")
    public ApiResponse<Void> executeBatchJob() {
        // 生成带前缀的 traceId
        String traceId = TraceIdUtil.generateTraceIdWithPrefix("batch_job");
        TraceIdUtil.setTraceId(traceId);
        
        log.info("批量处理任务开始");
        // 业务逻辑...
        log.info("批量处理任务完成");
        
        // 清除 traceId
        TraceIdUtil.removeTraceId();
        
        return ApiResponse.success("任务执行完成");
    }

    /**
     * 示例4.2: 检查 traceId 是否存在
     */
    @GetMapping("/check-traceid")
    public ApiResponse<String> checkTraceId() {
        boolean exists = TraceIdUtil.hasTraceId();
        String traceId = TraceIdUtil.getTraceId();
        
        log.info("TraceId 存在: {}, 值: {}", exists, traceId);
        
        return ApiResponse.success("TraceId: " + traceId);
    }

    /**
     * 示例4.3: 清除所有 MDC 数据
     * 
     * 警告：谨慎使用，会清除所有 MDC 中的数据
     */
    @PostMapping("/clear-mdc")
    public ApiResponse<Void> clearMdc() {
        log.info("清除 MDC 前: traceId = {}", TraceIdUtil.getTraceId());
        
        TraceIdUtil.clearMDC();
        
        log.info("清除 MDC 后: traceId = {}", TraceIdUtil.getTraceId());
        
        return ApiResponse.success("MDC 已清除");
    }

    // ==================== 示例5: 异常处理 ====================

    /**
     * 示例5.1: 异常自动记录
     * 
     * 功能：
     * - 异常会被自动捕获
     * - 异常堆栈会被完整记录
     * - 日志级别为 ERROR
     */
    @Log(title = "用户管理", desc = "异常处理演示")
    @PostMapping("/test-exception")
    public ApiResponse<Void> testException() {
        log.info("开始执行可能异常的操作");
        
        // 这个异常会被切面捕获并记录
        int result = 1 / 0;
        
        return ApiResponse.success("正常完成");
    }

    /**
     * 示例5.2: 手动记录异常
     */
    @Log(title = "用户管理", desc = "手动异常处理")
    @PostMapping("/handle-exception")
    public ApiResponse<String> handleException() {
        try {
            // 可能抛出异常的代码
            int result = 1 / 0;
        } catch (ArithmeticException e) {
            log.error("计算异常: {}", e.getMessage(), e);
            return ApiResponse.success("异常已处理");
        }
        
        return ApiResponse.success("正常完成");
    }

    // ==================== 数据类定义 ====================

    @Data
    public static class UserDto {
        private Long id;
        private String name;
        private String email;
    }

    @Data
    public static class UserCreateRequest {
        private String username;
        private String password;
        private String email;
        private String phoneNumber;
        private String idCard;
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    public static class LoginResponse {
        private Long userId;
        private String token;
        private Long expiresIn;
    }

    @Data
    public static class ConfigUpdateRequest {
        private String key;
        private String value;
    }

    @Data
    public static class ImportResult {
        private Integer successCount;
        private Integer failureCount;
    }

    @Data
    public static class ExportRequest {
        private String format;
    }

    @Data
    public static class OrderCreateRequest {
        private Long userId;
        private Double amount;
    }

    @Data
    public static class OrderDto {
        private Long id;
        private Long userId;
        private Double amount;
    }

    // 异步服务接口

//    public interface AsyncService {
//        @Async
//        void sendEmail(String email, String traceId);
//    }
}
