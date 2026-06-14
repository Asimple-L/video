# 视频分享网站优化设计方案

> 项目：video | 日期：2026-06-14 | 状态：待实现

## 概述

对个人视频分享网站进行四方面优化：邮件功能完善、推荐算法引入、后台用户 VIP 管理增强、接口防刷安全机制。

实现原则：**逐个实现，每个完成后再进入下一个**。

---

## 一、邮件功能完善

### 现状

- `SendMail.java` 硬编码了 163 邮箱账号密码，不安全不灵活
- `spring-boot-starter-mail` 已引入但未使用
- 后台管理缺少日志监控（本次暂不处理）
- **视频详情反馈页面缺少发邮件通知管理员的接口**（README 待办）

### 方案

#### 1.1 邮件配置移入 application.yml

```yaml
spring:
  mail:
    host: smtp.163.com
    port: 25
    username: xxx@163.com
    password: ${MAIL_PASSWORD}  # 环境变量或加密密文
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
```

密码加密方案：
- 使用对称加密（AES）对邮箱密码进行加密，密文存于配置文件
- 启动时通过 `MailPasswordDecryptUtil` 解密加载
- 加密密钥通过环境变量 `MAIL_SECRET_KEY` 传入

#### 1.2 新增 Spring Mail Service

创建 `MailService`（Spring Service）：
- 注入 `JavaMailSender`
- 提供 `sendSimpleMail(to, subject, content)` 和 `sendHtmlMail(to, subject, htmlContent)`
- 日志记录发送结果

#### 1.3 视频详情反馈接口

在 `Util` 控制器或 `StartController` 中新增：
- `POST /feedback` — 用户提交反馈内容
- 参数：`content`（反馈内容）、`contact`（联系方式，可选）
- 鉴权：需要登录
- 成功后调用 `MailService` 发送邮件通知管理员
- 管理员邮箱通过 `user.properties` 配置：`admin.email=xxx@qq.com`

#### 1.4 相关文件变更清单

| 文件 | 操作 |
|------|------|
| `application.yml` | 新增 `spring.mail.*` 配置 |
| `application-dev.yml` | 新增 dev 环境的邮件配置 |
| `application-prod.yml` | 新增 prod 环境的邮件配置 |
| `util/SendMail.java` | 标记为 `@Deprecated`，保留兼容 |
| `service/MailService.java` | **新建** — Spring Mail 封装 |
| `util/MailPasswordUtil.java` | **新建** — 密码加解密工具 |
| `controller/Util.java` | 新增反馈接口 `/feedback` |
| `user.properties` | 新增 `admin.email` 配置 |

### 1.5 测试要点

- 配置正确时能正常发送 HTML 邮件
- 密码加密后配置仍能正常发送
- 反馈接口未登录时返回权限不足
- 反馈接口参数为空时提示参数缺失

---

## 二、推荐算法（协同过滤 + 缓存）

### 现状

- `RankTask.commendRank()` 每天凌晨刷新，实际是按分类取前几条，无算法
- 浏览记录表 `t_view_history` 已存储用户观影数据
- 评分表 `t_raty` 有用户评分数据
- Redis 缓存已通过 `@Cacheable` 使用

### 方案

#### 2.1 协同过滤实现

采用**基于物品的协同过滤（Item-Based CF）**，原因：数据量较小、冷启动问题较轻、计算可离线。

**算法逻辑：**

1. **构建物品相似度矩阵**（离线计算，定时任务执行）：
   - 输入：所有用户的浏览/评分记录
   - 方法：对每对影片，计算同时被同一用户观看的次数作为相似度
   - 存储：影片相似度列表存入 Redis（`rec:sim:{filmId}` → 前 N 个相似影片 ID 列表）

2. **生成个性化推荐**（在线读取）：
   - 输入：当前用户最近观看过的 N 部影片
   - 方法：聚合这些影片的相似影片，去重、按相似度排序
   - 输出：Top-K 推荐列表

3. **冷启动处理**：
   - 新用户/新影片无数据时，回退到热门影片排行
   - 热门榜基于总浏览量 + 评分加权计算

#### 2.2 缓存策略

- 相似度矩阵每天凌晨计算一次，缓存结果
- 个性化推荐结果（按用户）缓存 1 小时
- 热门榜缓存 1 小时

#### 2.3 接口变更

- `GET /recommend` — 获取当前用户的个性化推荐（需登录）
  - 返回推荐影片列表
  - 未登录或无历史时返回热门影片

#### 2.4 相关文件变更清单

| 文件 | 操作 |
|------|------|
| `task/RankTask.java` | 改造 — 增加协同过滤计算 |
| `service/RecommendService.java` | **新建** — 推荐逻辑 |
| `service/FilmService.java` | 新增热门榜、相似影片查询 |
| `controller/StartController.java` | 新增推荐接口 |
| `mapper/FilmMapper.java` | 新增协同过滤 SQL 查询 |
| `mapper/*Mapper.xml` | 新增相关 SQL |
| `entity/Film.java` | 无需变更 |

### 2.5 测试要点

- 有浏览记录的用户能获取个性化推荐
- 无浏览记录的用户获取热门推荐（降级）
- 推荐结果不含已看过的影片（除冷启动外）
- 定时任务执行后 Redis 缓存正确更新

---

## 三、后台用户 VIP 设置增强

### 现状

- `ManagerController.updateUser(key="vip")` 已支持切换用户 VIP 状态
- 但只是 `isVip` 在 0/1 间翻转，**未更新 `expireDate`**
- 当前 VIP 卡兑换功能已有完整的过期时间管理
- 两者**不重复**：VIP 卡是用户自助，后台设置是管理员手动

### 方案

#### 3.1 增强后台设置 VIP 接口

改造 `ManagerController.updateUser`（或新增接口）：
- 参数：`uid`、`action`（`setVip` / `removeVip`）、`duration`（1/3/6/12 月）
- `setVip`：设置 `isVip=1`，`expireDate = now + duration`（若已有 VIP 则叠加时长）
- `removeVip`：设置 `isVip=0`，清空或保留已过期 `expireDate`
- 操作记录日志

#### 3.2 管理端 VIP 整合

当前 `ManagerController` 已有：
- `vipCode` — 查看 VIP 卡列表
- `updateUser(key="vip")` — 切换用户 VIP

统一为 VIP 管理模块：
- `POST /admin/vip/setUserVip` — 设置用户 VIP
- `GET /admin/vip/userList` — 查看 VIP 用户列表（新增）

#### 3.3 相关文件变更清单

| 文件 | 操作 |
|------|------|
| `controller/ManagerController.java` | 改造 VIP 管理接口 |
| `service/UserService.java` | 增强 `update()` 方法 |
| `service/VipCodeService.java` | 无需变更 |

### 3.4 测试要点

- 管理员设置某用户 VIP 1 个月，该用户 `expireDate` 正确增加 1 月
- 已有 VIP 的用户再设置，时长正确叠加
- 取消 VIP 后 `isVip` 变为 0
- 非管理员调用返回权限不足

---

## 四、接口防刷（Rate Limiting）

### 现状

- 所有接口无访问频率限制
- Session 鉴权已在拦截器中实现
- Redis 已可用（`spring-boot-starter-redis`）
- `MyWebAppConfigurer` 统一管理拦截器注册

### 方案

#### 4.1 滑动窗口限流器

基于 Redis 的滑动窗口算法：

```
Key:   rate_limit:{ip}:{method}:{path}
Value: 请求时间戳的 ZSet
逻辑:
  1. 移除窗口外的旧记录 (ZREMRANGEBYSCORE)
  2. 统计窗口内请求数 (ZCARD)
  3. 若超过阈值则拒绝
  4. 否则记录当前时间戳 (ZADD)
```

核心参数（通过 `user.properties` 配置）：
- `rate.limit.login.limit=5` — 登录接口每分钟上限
- `rate.limit.login.window=60` — 窗口大小（秒）
- `rate.limit.default.limit=30` — 其他接口每分钟上限
- `rate.limit.default.window=60`

#### 4.2 限流注解 + 拦截器

创建 `@RateLimit` 注解：
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    String key() default "";       // 限流 key
    int limit() default 30;        // 上限次数
    int window() default 60;       // 窗口大小（秒）
}
```

创建 `RateLimitInterceptor`：
- 实现 `HandlerInterceptor`
- 在 `preHandle` 中检查注解
- 通过 Redis 判断是否超限
- 超限时返回 429 状态码 + JSON 错误信息
- 白名单路径（如 `/admin/**`）不限制

#### 4.3 注入 XSS/CSRF 防护（基础版）

**XSS 防护**：
- 利用 Spring `@InitBinder` 或自定义 `HttpServletRequestWrapper`
- 对请求参数做 HTML 标签过滤（如 `<script>` 转义）
- 使用 Commons Lang3 的 `StringEscapeUtils.escapeHtml4()`

**CSRF 防护**：
- 后端生成 CSRF Token 存入 Session
- 前端在请求 Header 中携带 Token
- 拦截器在校验 Token（`POST/PUT/DELETE` 请求）

> ⚠️ CSRF 实现需要前端配合，本次先实现 XSS 过滤 + 接口防刷，CSRF 作为后续追加项。

#### 4.4 相关文件变更清单

| 文件 | 操作 |
|------|------|
| `annotation/RateLimit.java` | **新建** — 限流注解 |
| `interceptor/RateLimitInterceptor.java` | **新建** — 限流拦截器 |
| `interceptor/XssFilter.java` | **新建** — XSS 过滤器 |
| `config/MyWebAppConfigurer.java` | 注册新拦截器 |
| `util/ResponseReturnUtil.java` | 新增 429 错误信息 |
| `user.properties` | 新增限流配置项 |

### 4.5 测试要点

- 登录接口连续快速请求超过阈值后被拒绝
- 限流结束后恢复正常
- 白名单路径不受限流影响
- XSS 参数被正确转义
- 限流拦截器不影响正常请求

---

## 实现顺序

1. **邮件功能完善** — 最独立，且为后续功能提供通知能力
2. **后台用户 VIP 设置增强** — 改动量小，快速完成
3. **接口防刷 + XSS 过滤** — 安全加固，保护已有和新增接口
4. **推荐算法（协同过滤）** — 最复杂，放在最后稳步推进

每个步骤完成后，运行 `mvn test` 确保不破坏现有功能。
