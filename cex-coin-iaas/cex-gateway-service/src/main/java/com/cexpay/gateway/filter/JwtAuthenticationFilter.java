package com.cexpay.gateway.filter;

import com.cexpay.common.constant.ErrorCode;
import com.cexpay.common.dto.ApiResponse;
import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * JWT验证全局过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    
    @Value("${jwt.secret:your-secret-key-must-be-at-least-32-characters-long-here}")
    private String jwtSecret;
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    /**
     * 无需认证的路径
     */
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/user-service/auth/**",
            "/user-service/api/v1/user/register",
            "/user-service/api/v1/user/login",
            "/user-service/api/v1/user/logout",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**"
    );
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        
        // 检查是否需要跳过认证
        if (isExcludePath(path)) {
            return chain.filter(exchange);
        }
        
        try {
            String token = extractToken(request);
            if (token == null) {
                return unauthorizedResponse(exchange, "Missing authorization token");
            }
            
            // 验证JWT
            validateToken(token);
            
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("JWT验证失败: {}", e.getMessage());
            return unauthorizedResponse(exchange, "Invalid token");
        }
    }
    
    /**
     * 检查路径是否在排除列表中
     */
    private boolean isExcludePath(String path) {
        return EXCLUDE_PATHS.stream()
                .anyMatch(excludePath -> pathMatcher.match(excludePath, path));
    }
    
    /**
     * 从请求头中提取JWT token
     */
    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
    
    /**
     * 验证JWT token
     */
    private void validateToken(String token) {
        Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
    }
    
    /**
     * 返回未授权响应
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        ApiResponse<?> apiResponse = ApiResponse.fail(ErrorCode.UNAUTHORIZED, message);
        byte[] bytes = JSON.toJSONString(apiResponse).getBytes(StandardCharsets.UTF_8);
        
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
    
    @Override
    public int getOrder() {
        return -100;
    }
}
