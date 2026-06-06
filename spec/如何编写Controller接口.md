# Controller 接口编写规范

## 1. 类定义

- 使用 `@RestController` + `@RequestMapping` 注解，**不**使用 `@Controller`
- 继承 `com.asimple.controller.CommonController`
- 类级 `@RequestMapping` 定义统一前缀路径

```java
@RestController
@RequestMapping("/manager")
public class ManagerController extends CommonController {
}
```

## 2. 方法定义

### 2.1 请求映射

- 使用 `@RequestMapping` 完整注解（`value`、`method`、`produces`），**不**拆分使用 `@GetMapping` / `@PostMapping`

```java
@RequestMapping(value = "/filmType", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
public Object filmType() {
}
```

### 2.2 方法签名

- 返回类型统一为 `Object`（实际返回 `JSONObject`）
- 参数直接绑定请求参数名，**不需要** `@RequestParam`（除非需要默认值）
- 支持直接绑定实体对象作为参数

```java
// 简单参数绑定
public Object login(String username, String password, HttpServletRequest request)

// 实体参数绑定
public Object addType(HttpServletRequest request, Type type)
```

### 2.3 方法命名

- 小驼峰命名，动词开头
- 常用命名模式：
  - `getXxx` / `listXxx` — 获取列表
  - `addXxx` — 新增
  - `updateXxx` / `editXxx` — 修改
  - `deleteXxx` / `delXxx` — 删除
  - `checkXxx` — 校验

## 3. 鉴权模式

在每个需要登录的方法开头手动校验，**不使用** Spring Security：

```java
// 校验普通用户登录
if (!RequestUtil.isLogin(request)) {
    return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.LOGIN_FIRST);
}

// 校验管理员登录
if (RequestUtil.isNotAdminLogin(request)) {
    return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.ADMIN_LOGIN_FIRST);
}

// 校验非本人操作
if (RequestUtil.isNotSelfLogin(request, userId)) {
    return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.ILLEGAL_VISIT);
}
```

匿名方法（如登录、注册、首页展示）**跳过**鉴权。

## 4. 统一响应

使用 `ResponseReturnUtil` 工具类构建返回：

```java
// 成功带数据
return ResponseReturnUtil.returnSuccessWithData(data);

// 成功带消息
return ResponseReturnUtil.returnSuccessWithMsg("操作成功");

// 成功无消息无数据
return ResponseReturnUtil.returnSuccessWithoutMsgAndData();

// 失败带消息
return ResponseReturnUtil.returnErrorWithMsg("错误信息");

// 失败带数据
return ResponseReturnUtil.returnErrorWithData(data);
```

## 5. 获取当前用户信息

通过继承 `CommonController` 获取：

```java
// 获取当前登录用户
User user = getUserInfo(request);

// 获取当前管理员用户
User admin = getAdminUserInfo(request);
```

## 6. 完整示例

```java
@RestController
@RequestMapping("/profile")
public class ProfileController extends CommonController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/userInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object userInfo(HttpServletRequest request) {
        if (!RequestUtil.isLogin(request)) {
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.LOGIN_FIRST);
        }
        User user = getUserInfo(request);
        return ResponseReturnUtil.returnSuccessWithData(user);
    }
}
```

## 7. 禁止事项

- 不要在 Controller 中使用 `System.out.println()`，使用 `LogUtil`
- 不要使用 `e.printStackTrace()`，使用 `LogUtil.error()`
- 不要混用 JSON 库，统一使用 Fastjson（`com.alibaba.fastjson.JSONObject`）
- 不要在 Controller 中写业务逻辑，委托给 Service 层
