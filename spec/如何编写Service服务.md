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
    return filmMapper.add(film) == 1;
}

public boolean updateFilm(Film film) {
    return filmMapper.update(film) == 1;
}

public Film loadFilmById(String id) {
    return filmMapper.load(id);
}
```

- IBaseDao 的方法名为 `update()`、`load()`（**不是** `edit()` / `getById()`）
- 对外方法返回 `boolean`（判断 `== 1`）或实体对象

### 3.2 add-or-update 模式

部分 Service（如 `CataLogService`、`DecadeService`、`LevelService`、`LocService`）的 `add()` 方法同时支持新增和更新：无 ID 时 insert，有 ID 时 update：

```java
public boolean add(CataLog cataLog) {
    if (Tools.isEmpty(cataLog.getId())) {
        cataLog.setId(Tools.UUID());
        cataLog.setIsUse(1);
        return cataLogMapper.add(cataLog) == 1;
    }
    return cataLogMapper.update(cataLog) == 1;
}
```

### 3.3 Service 保存并返回 ID

当需要返回生成的 ID 时，方法返回 `String` 而非 `boolean`：

```java
public String save(Film film) {
    if (filmMapper.add(film) == 1) {
        return film.getId();
    }
    return null;
}
```

### 3.4 业务判断重复性

先 `count` 再决定 insert 还是 update（如观影记录）：

```java
public void addViewHistory(String filmId, String userId) {
    Map<String, Object> param = new HashMap<>(4);
    param.put("filmId", filmId);
    param.put("userId", userId);
    if (filmMapper.countViewHistory(param) == 0) {
        filmMapper.addViewHistory(param);
    } else {
        filmMapper.updateViewHistory(param);
    }
}
```

### 3.5 分页查询

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
- `filmId` / `userId` — 关联查询
- `username` / `password` — 登录参数

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

### 4.3 多分区清理

可同时清空多个缓存分区：

```java
@CacheEvict(value = {"index_filmTuijian", "index_filmPaiHang"}, allEntries = true)
public boolean update(FilmUpdateInfo film) {
    return filmMapper.updateFilm(film) == 1;
}
```

### 4.4 常用缓存分区名称

项目中实际使用的缓存 value 名称（无统一常量，直接使用字符串字面量）：

| 缓存 value | 用途 |
|-----------|------|
| `"film"` | 影片分页列表 |
| `"user"` | 用户信息 |
| `"redis_cataLogList"` | 分类列表 |
| `"redis_levelList"` | 等级列表 |
| `"redis_decadeList"` | 年代列表 |
| `"redis_locList"` | 地区列表 |
| `"index_filmTuijian"` | 首页推荐影片 |
| `"index_filmPaiHang"` | 首页排行榜 |

### 4.5 只读列表缓存模式

对于不常变动的配置数据（分类、年代等），使用专用缓存名字+`listIsUse()` 方法：

```java
@Cacheable(value = "redis_cataLogList")
public List<CataLog> listIsUse() {
    return cataLogMapper.findByIsUse();
}

@CacheEvict(value = "redis_cataLogList", allEntries = true)
public boolean add(CataLog cataLog) {
    // ...
}
```

## 5. Solr 搜索

Service 层使用 `SolrTemplate` 进行全文搜索，配合高亮显示：

```java
@Service
public class FilmService {

    @Resource
    private SolrTemplate solrTemplate;

    public List<Film> getFilmOfSolr(String key) {
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        // 添加搜索条件
        Criteria criteria = new Criteria("video_film_name").is(key);
        query.addCriteria(criteria);
        // 高亮配置
        HighlightOptions options = new HighlightOptions();
        options.addField("video_film_name");
        options.setSimplePrefix("<em style='color:red'>");
        options.setSimplePostfix("</em>");
        query.setHighlightOptions(options);
        // 执行查询
        HighlightPage<Film> page = solrTemplate.queryForHighlightPage("film", query, Film.class);
        // 处理高亮结果
        for (HighlightEntry<Film> entry : page.getHighlighted()) {
            Film film = entry.getEntity();
            List<String> highlights = entry.getHighlights().get("video_film_name");
            if (highlights != null && !highlights.isEmpty()) {
                film.setName(highlights.get(0));
            }
        }
        return page.getContent();
    }
}
```

## 6. 日志

使用 `LogUtil` 记录日志：

```java
LogUtil.info(FilmService.class, "--> 新增影片：" + film.getName());
LogUtil.error(FilmService.class, "更新失败，id=" + id);
```

## 8. 完整示例

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

## 8. 完整示例

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
    public User loadUser(String userId) {
        return userMapper.load(userId);
    }

    @CacheEvict(value = "user", allEntries = true)
    public boolean updateUser(User user) {
        return userMapper.update(user) == 1;
    }
}
```

## 9. 禁止事项

- 不要创建 Service 接口层，项目约定服务为具体类
- 不要使用 `@Autowired`
- 不要在 Service 层处理 HTTP 请求对象（HttpServletRequest/Response）
- 避免在 Service 中直接返回 JSONObject，返回实体或 boolean
