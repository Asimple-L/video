# AGENTS.md - 项目指南

## 重要：编码前必须读取规范文档

在新增或修改代码之前，**必须先读取 `spec/` 目录下对应的规范文档**，严格按照项目约定风格编码：

| 要编写的内容 | 必读文档 |
|-------------|---------|
| Controller 接口 | `spec/如何编写Controller接口.md` |
| Service 服务 | `spec/如何编写Service服务.md` |
| 实体类 | `spec/如何编写实体类.md` |
| Mapper 接口 / XML 映射 | `spec/如何编写Mapper接口与XML映射.md` |
| 单元测试 | `spec/如何编写单元测试.md` |
| 配置类 | `spec/代码整体架构与分层规范.md` |
| 数据库相关 | `spec/数据库规范.md` |
| 响应返回 | `spec/统一响应格式规范.md` |
| 认证鉴权 | `spec/认证与拦截器规范.md` |
| 日志/工具类 | `spec/日志与工具类使用规范.md` |
| 缓存/定时任务 | `spec/缓存与定时任务规范.md` |

> 原则：当要编写的代码类型对应上述某一文档时，必须先读取该文档再编码，确保风格一致。

## 项目概述
Spring Boot 1.5.10 个人视频分享网站后端，Java 8，MyBatis ORM，Maven 构建。
基础包名：`com.asimple`，上下文路径：`/video`。

## 构建与运行

```bash
# 编译打包
mvn clean package

# 跳过测试打包
mvn clean package -DskipTests

# 本地运行（dev profile）
mvn spring-boot:run

# 生产环境打包
mvn clean package -P prod

# 指定 profile 运行
mvn spring-boot:run -Dspring.profiles.active=prod
```

## 测试

```bash
# 运行全部测试
mvn test

# 运行单个测试类
mvn test -Dtest=ManagerControllerTest

# 运行单个测试方法
mvn test -Dtest=ManagerControllerTest#testMethod

# 指定 profile 测试
mvn test -Dspring.profiles.active=dev
```

测试框架：JUnit 4 + SpringBootTest，使用 `SpringJUnit4ClassRunner`。
测试类样板：
```java
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxxTest {
    @Resource
    private XxxController controller;

    @Test
    public void testMethod() {
        // ...
    }
}
```

## 项目结构

```
src/main/java/com/asimple/
├── App.java                    # 启动类，@MapperScan 配置
├── controller/                 # REST 控制器，继承 CommonController
├── entity/                     # 实体类，@Data + Serializable
├── service/                    # 业务层，具体类（无接口）
├── mapper/                     # MyBatis Mapper 接口，继承 IBaseDao<T>
├── config/                     # Spring 配置类
├── interceptor/                # HTTP 拦截器
├── task/                       # 定时任务
└── util/                       # 工具类
src/main/resources/
├── mapper/                     # MyBatis XML 映射文件（*Mapper.xml）
├── application.yml             # 主配置，激活 dev profile
├── application-dev.yml         # 开发环境配置
├── application-prod.yml        # 生产环境配置
└── user.properties             # 自定义业务属性
src/test/java/com/asimple/
├── controller/                 # 控制器测试
└── dto/                        # DTO/实体测试
```

## 代码风格

### 命名规范
- **控制器**：`XxxController`，方法名 camelCase 动词（如 `addFilm()`、`getFilmList()`）
- **服务**：`XxxService`，具体类加 `@Service`，无接口层
- **Mapper**：`XxxMapper` 接口继承 `IBaseDao<T>`，加 `@Repository`
- **实体**：PascalCase 单数名词（如 `Film`、`User`），字段 camelCase
- **工具类**：`XxxUtil` 或 `XxxHelper`
- **常量**：`UPPER_SNAKE_CASE`，定义在 `ResponseReturnUtil`、`VideoKeyNameUtil` 等

### 注解使用
- 依赖注入：优先使用 `@Resource`（javax），不使用 `@Autowired`
- 控制器：`@RestController` + `@RequestMapping`（不用 `@GetMapping`/`@PostMapping`）
- 实体：`@Data`（Lombok），实现 `Serializable`。注意：实体可以同时有 `@Data` 和手动构造器
- 缓存：`@Cacheable` 读，`@CacheEvict` 写（Redis）
- 定时任务：`@Scheduled(cron = "...")`，类加 `@Component` + `@Configuration` + `@EnableScheduling`

### 导入风格
- 项目内部包和 `java.util.*` 使用通配符导入：`import com.asimple.entity.*;`
- 第三方框架类使用具体导入：`import org.springframework.web.bind.annotation.RequestMapping;`
- 无严格排序要求

### 格式规范
- 缩进：4 空格
- 花括号：K&R 风格（左花括号同行）
- 行宽：无强制限制，建议不超过 120 字符
- 无 `.editorconfig` 或代码格式化工具
- 集合初始化指定预期容量：`new HashMap<>(4)`、`new ArrayList<>()`

### 注释语言
- Javadoc 和行内注释均使用**中文**
- 类级 Javadoc 格式：`@author Asimple`、`@ProjectName video`、`@description 描述`
- 公共方法应有 Javadoc 注释（`@description`、`@param`、`@return`）

## 统一响应格式

控制器统一返回 `JSONObject`（Fastjson），结构：
```json
{ "code": "000000", "message": "...", "data": {...} }
```

通过 `ResponseReturnUtil` 工具类构建：
- `returnSuccessWithData(data)` — code=000000 + data
- `returnSuccessWithMsg(msg)` — code=000000 + message
- `returnErrorWithMsg(msg)` — code=111111 + message
- `returnErrorWithData(data)` — code=111111 + data
- `returnSuccessWithoutMsgAndData()` — code=000000

## 分页模式

使用自定义 `PageBean<T>`（手动 getter/setter，非 @Data），字段名：
- `pc` — 当前页码（Page Code）
- `ps` — 每页大小（Page Size）
- `tr` — 总记录数（Total Records）
- `tp` — 总页数（Total Pages，计算得出：`tr % ps == 0 ? tp : tp + 1`）
- `beanList` — 当前页数据
- `url` — 保存条件 URL

Service 层手动计算偏移量：`offset = (pc - 1) * ps`

## 认证与授权

- 基于 Session，无 Spring Security
- `RequestUtil` 工具类检查登录状态：`isLogin()`、`isNotAdminLogin()`、`isNotSelfLogin()`
- 拦截器：`ProfileInterceptor`（用户鉴权）、`AdminUserInterceptor`（管理鉴权）
- 控制器方法开头手动校验（匿名方法跳过）：
  ```java
  if (!RequestUtil.isLogin(request)) {
      return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.LOGIN_FIRST);
  }
  ```

## 数据库约定

- 表名前缀：`t_`（如 `t_film`、`t_user`、`t_view_history`、`t_bullet`）
- 主键：32 位大写 UUID 字符串（非自增），通过 `Tools.UUID()` 生成
- 编码：UTF-8，引擎：InnoDB
- 字段命名：蛇形 snake_case（数据库列名如 `type_id`、`view_number`），实体中对应 camelCase
- `isUse` 字段表示逻辑删除/启用状态（值为 0 或 1）

## 日志

- 使用自定义 `LogUtil` 封装 SLF4J
- 调用方式：`LogUtil.info(FilmService.class, "消息")`、`LogUtil.error("消息")`
- 日志消息前缀：`--> `

## Controller 模式

- 继承 `CommonController`（提供 `getUserInfo()`、`getAdminUserInfo()` 方法）
- 方法参数直接绑定请求参数（`String username, String password`）或实体对象，无需 `@RequestParam`（除非需要默认值）
- `@RequestMapping` 使用完整注解（`value`、`method`、`produces`），不拆分
- 返回类型：`Object`（实际返回 `JSONObject`）

## Service 层模式

- 无接口层，直接使用具体类加 `@Service`
- 注入 Mapper 使用 `@Resource`
- 分页参数常用 `Map<String, Object>` 传递，手动 key 存取
- 读方法加 `@Cacheable`，写方法（增/删/改）加 `@CacheEvict(allEntries = true)`
- 数据库 CRUD 委托给 Mapper 接口（继承 `IBaseDao<T>`），对外返回 `boolean` 或实体

## MyBatis XML 映射

- 位于 `src/main/resources/mapper/`，命名 `XxxMapper.xml`
- namespace 对应 Mapper 接口全限定名
- SQL 片段使用 `<sql>` + `<include>` 复用
- 参数引用：`#{obj.field}`（分页用 `#{start},#{count}`）
- 多参数方法使用 `param1`/`param2` 或 `@Param` 注解
- `resultType` 使用全限定类名
- 表名和字段名使用反引号包裹：`` `t_film` ``

## 常见反模式（避免）

1. 不要在控制器中使用 `System.out.println()`，使用 `LogUtil`
2. 不要使用 `e.printStackTrace()`，使用 `LogUtil.error()` + 异常对象（现有 `DateUtil` 和 `Tools.str2Date` 有遗留，新代码禁止使用）
3. 不要混用多个 JSON 库，统一使用 Fastjson（`com.alibaba.fastjson`）
4. 不要添加服务接口层，项目约定服务为具体类
5. 不要使用 `@Autowired`，统一使用 `@Resource`
6. 不要在实体中添加业务逻辑，保持实体纯净
7. 不要硬编码 CORS 配置，使用 `MyWebAppConfigurer` 统一管理
8. 不要使用 `StringBuffer`，使用 `StringBuilder`（非线程安全场景）
9. 不要在测试中使用 `System.out.println` 断言结果，使用 JUnit `Assert`

## 依赖库

- **JSON**：Alibaba Fastjson（主用）、`net.sf.json-lib`（少量）、`org.json`（少量）
- **数据库**：MySQL + Druid 连接池
- **缓存**：Redis（`spring-boot-starter-redis`），通过 `@Cacheable`/`@CacheEvict` 使用
- **搜索**：Apache Solr（`spring-boot-starter-data-solr`），使用 `SolrTemplate`
- **邮件**：Spring Boot Starter Mail
- **工具**：Lombok 1.16.10、Apache Commons（BeanUtils、Lang3、FileUpload）
- **验证**：Geetest 极验（`GeetestLib`）
- **日志**：Log4j2（已排除默认 Logging）
- **热部署**：spring-boot-devtools

## Maven 注意

- POM 中部分依赖使用版本范围（如 `[1.2.31,)`），注意可能引入不兼容版本
- 无专门的 lint/checkstyle 插件，代码质量靠人工 review
- 无 `maven-surefire-plugin` 显式配置，使用 Spring Boot parent 默认
