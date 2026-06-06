# Service 服务层编写规范

## 1. 类定义

- 使用具体类（**无接口层**），加 `@Service` 注解
- 类名：`XxxService`，PascalCase

```java
@Service
public class FilmService {
}
```

## 2. 依赖注入

- 使用 `@Resource`（javax.annotation），**不**使用 `@Autowired`

```java
@Resource
private FilmMapper filmMapper;

@Resource
private CommonService commonService;
```

## 3. 数据库操作

### 3.1 CRUD 委托给 Mapper

Service 层调用 Mapper 接口方法（继承 `IBaseDao<T>`）：

```java
public boolean addFilm(Film film) {
    return filmMapper.add(film);
}

public boolean updateFilm(Film film) {
    return filmMapper.edit(film);
}

public Film getFilmById(String id) {
    return filmMapper.getById(id);
}
```

对外方法返回 `boolean` 或实体对象。

### 3.2 分页查询

手动计算偏移量，使用 `Map<String, Object>` 传递参数：

```java
public PageBean<Film> getFilmList(int pc, int ps) {
    PageBean<Film> pageBean = new PageBean<>();
    pageBean.setPc(pc);
    pageBean.setPs(ps);

    Map<String, Object> param = new HashMap<>(4);
    param.put("start", (pc - 1) * ps);
    param.put("count", ps);

    List<Film> filmList = filmMapper.getFilmList(param);
    int totalRecord = filmMapper.getCount(param);

    pageBean.setBeanList(filmList);
    pageBean.setTr(totalRecord);
    return pageBean;
}
```

常用 `Map` key 约定：
- `start` — 偏移量（`(pc - 1) * ps`）
- `count` — 每页大小
- `key` — 搜索关键字
- `typeId` / `cataLogId` — 分类筛选

## 4. 缓存

### 4.1 读操作

使用 `@Cacheable`：

```java
@Cacheable(value = "film", key = "'film_' + #pc + '_' + #ps")
public PageBean<Film> getFilmList(int pc, int ps) {
    // ...
}
```

### 4.2 写操作

使用 `@CacheEvict(allEntries = true)` 清空整个缓存分区：

```java
@CacheEvict(value = "film", allEntries = true)
public boolean addFilm(Film film) {
    return filmMapper.add(film);
}
```

## 5. 日志

使用 `LogUtil` 记录日志：

```java
LogUtil.info(FilmService.class, "--> 新增影片：" + film.getName());
LogUtil.error(FilmService.class, "更新失败，id=" + id);
```

## 6. 事务

对于需要事务保证的操作，在 Service 方法上加 `@Transactional`：

```java
@Transactional
public boolean deleteFilmWithRelation(String filmId) {
    // 删除影片评论
    commentService.deleteByFilmId(filmId);
    // 删除影片
    return filmMapper.delete(filmId);
}
```

## 7. 完整示例

```java
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User login(String username, String password) {
        Map<String, Object> param = new HashMap<>(4);
        param.put("username", username);
        param.put("password", password);
        return userMapper.login(param);
    }

    @Cacheable(value = "user", key = "'user_' + #userId")
    public User getUserById(String userId) {
        return userMapper.getById(userId);
    }

    @CacheEvict(value = "user", allEntries = true)
    public boolean updateUser(User user) {
        return userMapper.edit(user);
    }
}
```

## 8. 禁止事项

- 不要创建 Service 接口层，项目约定服务为具体类
- 不要使用 `@Autowired`
- 不要在 Service 层处理 HTTP 请求对象（HttpServletRequest/Response）
- 避免在 Service 中直接返回 JSONObject，返回实体或 boolean
