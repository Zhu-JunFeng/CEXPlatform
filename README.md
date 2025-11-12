# CEX Platform - 中心化交易所微服务平台

## 项目概述

CEX Platform是一个完整的微服务架构的中心化加密资产交易平台，采用Spring Cloud + Spring Boot + MyBatis-Plus技术栈，包含网关、用户、钱包、订单、撮合引擎、市场数据、WebSocket推送等核心模块。

### 技术栈

| 组件 | 版本 | 用途 |
|------|------|------|
| **JDK** | 17 | Java开发环境 |
| **Spring Boot** | 3.2.0 | Web框架 |
| **Spring Cloud** | 2023.0.0 | 微服务框架 |
| **Spring Cloud Alibaba** | 2023.0.0.0 | 阿里微服务方案 |
| **Nacos** | 2.x | 配置中心+服务注册中心 |
| **MyBatis-Plus** | 3.5.4 | ORM框架 |
| **PostgreSQL** | 43.138.23.51 | 关系数据库 |
| **Redis** | 127.0.0.1:6379 | 缓存+消息队列 |
| **Kafka** | 127.0.0.1:9092 | 消息队列 |
| **Maven** | 3.x | 项目管理工具 |

### 项目架构

```
CEXPlatform (父模块)
├── cex-common                    # 通用模块（工具类、常数、异常等）
├── cex-gateway                   # API网关（路由、认证、限流）
├── cex-user-service             # 用户服务（注册、登录、认证、API Key）
├── cex-wallet-service           # 钱包服务（余额管理、充提转）
├── cex-order-service            # 订单服务（订单创建、查询、撤销）
├── cex-matching-engine          # 撮合引擎（订单匹配、成交记录）
├── cex-market-data-service      # 市场数据服务（行情、K线、深度）
├── cex-websocket-push           # WebSocket推送服务（实时数据推送）
└── docs                         # 文档（SQL、API文档等）
```

## 快速开始

### 前置条件

1. **JDK 17** - 安装Java 17 JDK
2. **Maven 3.6+** - 项目构建工具
3. **PostgreSQL** - 数据库
   - 地址：43.138.23.51
   - 用户名：root
   - 密码：root
   - 数据库名：cex_platform

4. **Nacos** - 服务注册中心和配置中心
   - 地址：127.0.0.1:8848
   - 用户名：nacos
   - 密码：nacos

5. **Redis** - 缓存和会话存储
   - 地址：127.0.0.1:6379

6. **Kafka** - 消息队列
   - 地址：127.0.0.1:9092

### 环境准备

#### 1. 创建PostgreSQL数据库

```bash
# 连接到PostgreSQL
psql -h 43.138.23.51 -U root -d postgres

# 创建数据库
CREATE DATABASE cex_platform;

# 使用SQL脚本初始化表结构
\c cex_platform
\i docs/sql/init.sql
```

#### 2. 启动Nacos服务

```bash
# 下载Nacos (https://github.com/alibaba/nacos/releases)
# 解压后启动
cd nacos/bin
./startup.sh -m standalone  # 单机模式

# 访问Nacos控制台
# http://127.0.0.1:8848/nacos/
# 用户名/密码: nacos/nacos
```

#### 3. 启动Redis

```bash
redis-server
# 或使用Docker
docker run -d -p 6379:6379 redis:latest
```

#### 4. 启动Kafka

```bash
# 启动Zookeeper (如果使用KRaft可跳过)
bin/zookeeper-server-start.sh config/zookeeper.properties &

# 启动Kafka
bin/kafka-server-start.sh config/server.properties &
```

### 编译和启动

#### 1. 编译项目

```bash
cd /Users/zhujf/Documents/code/java/CEXPlatform

# 编译父项目和所有子模块
mvn clean install -DskipTests

# 或编译单个模块
mvn clean install -pl cex-gateway -DskipTests
```

#### 2. 启动各个微服务

**顺序很重要：先启动网关和基础服务**

```bash
# 1. 启动网关（必须首先启动）
cd cex-gateway
mvn spring-boot:run

# 2. 启动用户服务（新终端）
cd cex-user-service
mvn spring-boot:run

# 3. 启动钱包服务（新终端）
cd cex-wallet-service
mvn spring-boot:run

# 4. 启动订单服务（新终端）
cd cex-order-service
mvn spring-boot:run

# 5. 启动撮合引擎（新终端）
cd cex-matching-engine
mvn spring-boot:run

# 6. 启动市场数据服务（新终端）
cd cex-market-data-service
mvn spring-boot:run

# 7. 启动WebSocket推送服务（新终端）
cd cex-websocket-push
mvn spring-boot:run
```

#### 3. 验证启动

```bash
# 检查服务是否注册到Nacos
curl http://127.0.0.1:8848/nacos/v1/ns/service/list?namespaceId=public

# 检查Gateway健康状态
curl http://127.0.0.1:8080/actuator/health

# 查看各服务日志
tail -f cex-gateway/logs/*.log
```

## API文档

### 网关路由配置

| 服务 | 路由前缀 | 服务端口 |
|------|--------|--------|
| User Service | /user-service | 9001 |
| Wallet Service | /wallet-service | 9002 |
| Order Service | /order-service | 9003 |
| Market Data Service | /market-service | 9005 |

### 访问Swagger UI

启动各个微服务后，可以通过以下地址访问Swagger文档：

- **网关API文档**: http://127.0.0.1:8080/swagger-ui.html
- **用户服务API**: http://127.0.0.1:9001/swagger-ui.html
- **钱包服务API**: http://127.0.0.1:9002/swagger-ui.html
- **订单服务API**: http://127.0.0.1:9003/swagger-ui.html
- **市场数据服务API**: http://127.0.0.1:9005/swagger-ui.html

### 核心API端点

#### 用户服务

```bash
# 用户注册
POST http://127.0.0.1:8080/user-service/api/v1/user/register
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}

# 用户登录
POST http://127.0.0.1:8080/user-service/api/v1/user/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}

# 获取用户信息
GET http://127.0.0.1:8080/user-service/api/v1/user/info/123456
Authorization: Bearer <token>
```

#### 钱包服务

```bash
# 获取用户钱包列表
GET http://127.0.0.1:8080/wallet-service/api/v1/wallet/list/123456
Authorization: Bearer <token>

# 获取特定币种钱包
GET http://127.0.0.1:8080/wallet-service/api/v1/wallet/123456/BTC
Authorization: Bearer <token>

# 充值
POST http://127.0.0.1:8080/wallet-service/api/v1/wallet/deposit?userId=123456&currency=BTC&amount=10
Authorization: Bearer <token>

# 提现
POST http://127.0.0.1:8080/wallet-service/api/v1/wallet/withdraw?userId=123456&currency=BTC&amount=5
Authorization: Bearer <token>
```

#### 订单服务

```bash
# 创建订单
POST http://127.0.0.1:8080/order-service/api/v1/order/create?userId=123456
Content-Type: application/json
Authorization: Bearer <token>

{
  "symbol": "BTC/USDT",
  "orderType": 1,
  "price": 50000,
  "quantity": 1
}

# 获取订单列表
GET http://127.0.0.1:8080/order-service/api/v1/order/list/123456
Authorization: Bearer <token>

# 撤销订单
POST http://127.0.0.1:8080/order-service/api/v1/order/cancel/987654
Authorization: Bearer <token>
```

## 代码规范

### 目录结构规范

```
src/main/java/com/cexpay/[modulename]/
├── [ModuleName]Application.java          # 启动类
├── config/                               # 配置类
│   └── *Config.java
├── controller/                           # 控制器层
│   └── *Controller.java
├── service/                              # 业务逻辑层
│   ├── I*Service.java                   # 接口
│   └── impl/
│       └── *ServiceImpl.java             # 实现
├── domain/                               # 实体类
│   └── *.java
├── mapper/                               # 数据访问层
│   └── *Mapper.java
├── dto/                                  # 数据传输对象
│   └── *Request.java / *Response.java
├── exception/                            # 异常类
│   └── *Exception.java
├── util/                                 # 工具类
│   └── *Util.java
└── listener/                             # 事件监听器
    └── *Listener.java
```

### 命名规范

| 类型 | 命名约定 | 示例 |
|------|--------|------|
| 类 | UpperCamelCase | `UserController`, `UserService` |
| 方法 | lowerCamelCase | `getUserInfo()`, `createOrder()` |
| 常量 | UPPER_SNAKE_CASE | `MAX_RETRY_TIMES`, `DEFAULT_TIMEOUT` |
| 包名 | lowercase | `com.cexpay.user.service` |
| 数据库表 | t_+ snake_case | `t_user`, `t_order` |
| 字段 | snake_case | `user_id`, `create_time` |

### 编码规范

#### 1. 注释规范

```java
/**
 * 用户服务实现类
 * 
 * 提供用户注册、登录、信息查询等功能
 */
public class UserServiceImpl implements IUserService {
    
    /**
     * 用户注册
     * 
     * @param request 注册请求
     * @return 创建的用户对象
     * @throws BusinessException 如果用户名已存在
     */
    @Override
    public User register(RegisterRequest request) {
        // 参数验证
        // ...
    }
}
```

#### 2. 日志规范

```java
@Slf4j
public class UserServiceImpl {
    
    public User register(RegisterRequest request) {
        try {
            // 业务逻辑
            log.info("用户注册成功: {}", request.getUsername());
            return user;
        } catch (Exception e) {
            log.error("用户注册失败: {}", request.getUsername(), e);
            throw new BusinessException("注册失败");
        }
    }
}
```

#### 3. 异常处理规范

```java
// 使用自定义异常
if (user == null) {
    throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
}

// 参数验证
if (StringUtils.isBlank(username)) {
    throw new BusinessException(ErrorCode.PARAM_ERROR, "用户名不能为空");
}
```

#### 4. 数据库操作规范

```java
// 使用MyBatis-Plus的QueryWrapper
QueryWrapper<User> queryWrapper = new QueryWrapper<>();
queryWrapper.eq("username", username);
User user = userMapper.selectOne(queryWrapper);

// 使用@Transactional处理事务
@Transactional
public void transferMoney(Long fromUserId, Long toUserId, BigDecimal amount) {
    // 多个数据库操作
}
```

## 配置管理

### Nacos配置管理

1. **访问Nacos控制台**
   ```
   http://127.0.0.1:8848/nacos
   ```

2. **添加配置**
   - Data ID: `cex-user-service`
   - Group: `DEFAULT_GROUP`
   - 内容: YAML格式的配置

3. **配置优先级**
   - application.yaml（应用默认配置）
   - Nacos远程配置（优先级高，会覆盖本地配置）

### 应用配置文件

每个服务都有对应的 `application.yaml` 配置文件：

```yaml
spring:
  application:
    name: cex-user-service
  datasource:
    url: jdbc:postgresql://43.138.23.51:5432/cex_platform
    username: root
    password: root
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
      
server:
  port: 9001
```

## 常见问题

### 1. 连接数据库失败

**错误信息**: `Could not connect to database`

**解决方案**:
- 检查PostgreSQL服务是否运行
- 验证数据库连接信息是否正确
- 确保防火墙允许数据库连接

```bash
# 测试PostgreSQL连接
psql -h 43.138.23.51 -U root -c "SELECT 1"
```

### 2. Nacos服务发现失败

**错误信息**: `Request from client failed`

**解决方案**:
- 检查Nacos服务是否运行
- 验证Nacos服务地址和端口
- 检查网络连接

```bash
# 测试Nacos连接
curl http://127.0.0.1:8848/nacos
```

### 3. 无法获取JWT token

**错误信息**: `401 Unauthorized`

**解决方案**:
- 登录获取token
- 在请求头中添加 `Authorization: Bearer <token>`
- 检查token是否过期

```bash
# 获取token
curl -X POST http://127.0.0.1:8080/user-service/api/v1/user/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'
```

### 4. 端口被占用

**错误信息**: `Port 8080 already in use`

**解决方案**:
- 修改配置文件中的端口号
- 或杀死占用该端口的进程

```bash
# 查找占用端口8080的进程
lsof -i :8080

# 杀死进程
kill -9 <PID>
```

## 部署指南

### Docker部署

#### 1. 构建Docker镜像

```dockerfile
# Dockerfile
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# 构建镜像
docker build -t cex-user-service:1.0.0 .

# 运行容器
docker run -d \
  --name cex-user-service \
  -p 9001:9001 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/cex_platform \
  cex-user-service:1.0.0
```

#### 2. Docker Compose

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
      POSTGRES_DB: cex_platform
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7
    ports:
      - "6379:6379"

  cex-gateway:
    image: cex-gateway:1.0.0
    ports:
      - "8080:8080"
    depends_on:
      - nacos

  cex-user-service:
    image: cex-user-service:1.0.0
    ports:
      - "9001:9001"
    depends_on:
      - postgres
      - nacos

volumes:
  postgres_data:
```

### Kubernetes部署

```yaml
# cex-user-service-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: cex-user-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: cex-user-service
  template:
    metadata:
      labels:
        app: cex-user-service
    spec:
      containers:
      - name: cex-user-service
        image: cex-user-service:1.0.0
        ports:
        - containerPort: 9001
        env:
        - name: SPRING_DATASOURCE_URL
          value: jdbc:postgresql://postgres:5432/cex_platform
        - name: SPRING_CLOUD_NACOS_SERVER_ADDR
          value: nacos:8848
```

## 性能优化

### 1. 数据库优化

- 为常查询字段建立索引
- 使用分页查询避免大量数据加载
- 使用MyBatis-Plus的批量操作接口

```java
// 批量插入
List<User> users = new ArrayList<>();
userMapper.insertBatch(users);

// 批量更新
userMapper.updateBatch(users);
```

### 2. 缓存策略

- 使用Redis缓存热点数据
- 设置合理的缓存过期时间
- 实现缓存预热机制

```java
// 使用@Cacheable注解
@Cacheable(value = "user", key = "#id")
public User getUserById(Long id) {
    return userMapper.selectById(id);
}
```

### 3. 异步处理

- 使用Kafka处理异步消息
- 使用@Async注解实现异步方法

```java
@Async
public void sendNotification(String message) {
    // 异步发送通知
}
```

## 监控和日志

### 1. 应用监控

- 使用Spring Boot Actuator暴露指标
- 集成Prometheus和Grafana进行可视化

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
```

### 2. 日志管理

- 使用SLF4J + Logback
- 配置日志级别和输出格式

```yaml
logging:
  level:
    root: INFO
    com.cexpay: DEBUG
  file:
    name: logs/application.log
```

## 贡献指南

1. Fork本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启Pull Request

## 许可证

本项目采用MIT许可证，详见LICENSE文件。

## 联系方式

- 项目主页: https://github.com/yourusername/cex-platform
- 问题反馈: https://github.com/yourusername/cex-platform/issues
- 邮箱: support@cexpay.com

## 更新日志

### v1.0.0 (2024-11-11)

- ✅ 完成微服务架构搭建
- ✅ 实现用户认证和授权
- ✅ 实现钱包和订单模块
- ✅ 集成Nacos配置中心
- ✅ 编写完整API文档

## 致谢

感谢所有贡献者和使用者的支持！
