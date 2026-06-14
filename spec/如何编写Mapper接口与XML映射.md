# Mapper 接口与 XML 映射文件编写规范

## 一、Mapper 接口

### 1. 接口定义

- 继承 `com.asimple.util.IBaseDao<T>` 泛型接口
- 加 `@Repository` 注解
- 命名：`XxxMapper`（如 `FilmMapper`、`UserMapper`）

```java
@Repository
public interface FilmMapper extends IBaseDao<Film> {
}
```

### 2. IBaseDao 提供的通用方法

```java
public interface IBaseDao<T> {
    int add(T t);                       // 新增
    int delete(T t);                    // 按实体删除
    int deleteById(String id);          // 按ID删除
    int update(T t);                    // 更新（注意方法名为 update 非 edit）
    T load(String id);                  // 按ID加载（注意方法名为 load 非 getById）
    List<T> getAll();                   // 查询全部
    List<T> findByCondition(T t);       // 按条件查询
    List<T> findByIsUse();              // 查询启用的记录（isUse=1）
    int getTotalCount(@Param("obj") T t);     // 查询总数
    List<T> getPage(@Param("obj") T t,
                    @Param("start") int start,
                    @Param("count") int count); // 分页查询
}
```

> 注意：实际 `IBaseDao` 接口方法与常见命名不同，编码时请直接查看该接口确认。

### 3. 自定义方法

直接在接口中定义，通过 XML 映射：

```java
List<Film> getFilmList(Map<String, Object> param);
int getCount(Map<String, Object> param);
List<Film> searchFilm(Map<String, Object> param);
```

### 4. 多参数方法

当方法有多个参数时，使用 `@Param` 注解或在 XML 中用 `param1`/`param2`：

```java
// 推荐：使用 @Param
List<Film> getFilmList(@Param("start") int start, @Param("count") int count);

// 或使用 Map 统一传参（项目常用方式）
List<Film> getFilmList(Map<String, Object> param);
```

XML 中引用 `param1`/`param2` 的示例：

```xml
<select id="listByCataLogId" resultType="com.asimple.entity.Film">
    SELECT * FROM `t_film` WHERE `cataLog_id` = #{param1}
    <if test="param2 != null">
        LIMIT 0, #{param2}
    </if>
</select>
```

## 二、XML 映射文件

### 1. 文件位置与命名

- 位于 `src/main/resources/mapper/`
- 命名：`XxxMapper.xml`（与接口同名）
- namespace 对应 Mapper 接口全限定名

```xml
<mapper namespace="com.asimple.mapper.FilmMapper">
```

### 2. SQL 片段复用

使用 `<sql>` + `<include>` 复用字段列表：

```xml
<sql id="filmColumn">
    `id`, `name`, `image`, `url`, `view_number`, `comment_number`,
    `is_use`, `add_time`, `type_id`, `cata_log_id`,
    `sub_class_id`, `decade_id`, `loc_id`, `level_id`, `raty_id`
</sql>
```

### 3. 表名与字段名

表名和字段名使用反引号 `包裹：

```sql
SELECT <include refid="filmColumn"/> FROM `t_film`
```

### 4. 参数引用

- 单对象参数：`#{fieldName}`
- 分页参数：`#{start}, #{count}`（基于 Map 的 key）

```xml
<select id="getFilmList" resultType="com.asimple.entity.Film">
    SELECT <include refid="filmColumn"/>
    FROM `t_film`
    WHERE `is_use` = 1
    ORDER BY `add_time` DESC
    LIMIT #{start}, #{count}
</select>
```

### 5. resultType

使用全限定类名：

```xml
<select id="getById" resultType="com.asimple.entity.Film">
    SELECT <include refid="filmColumn"/> FROM `t_film` WHERE `id` = #{id}
</select>
```

当返回无对应实体的统计数据时，可使用 `java.util.HashMap`：

```xml
<select id="getViewHistory" resultType="java.util.HashMap">
    SELECT * FROM `t_view_history` WHERE `film_id` = #{filmId}
</select>
```

### 6. resultMap 关联映射

#### 6.1 association（一对一关联）

当实体包含关联对象时，使用 `<association>` + `<resultMap>` 实现 JOIN 查询：

```xml
<resultMap id="filmMap" type="com.asimple.entity.Film">
    <id property="id" column="id"/>
    <result property="name" column="name"/>
    <association property="cataLog" javaType="com.asimple.entity.CataLog">
        <id property="id" column="cid"/>
        <result property="name" column="cname"/>
    </association>
    <association property="subClass" javaType="com.asimple.entity.SubClass">
        <id property="id" column="sid"/>
        <result property="name" column="sname"/>
    </association>
    <association property="type" javaType="com.asimple.entity.Type">
        <id property="id" column="tid"/>
        <result property="name" column="tname"/>
    </association>
</resultMap>
```

#### 6.2 collection（一对多关联 + 嵌套 select）

使用 `<collection>` + `select` 属性实现递归子查询：

```xml
<resultMap id="myCataLog" type="com.asimple.entity.CataLog">
    <id property="id" column="id"/>
    <result property="name" column="name"/>
    <collection property="subClassList" column="id" select="selectSubclass"/>
</resultMap>

<resultMap id="mySubClass" type="com.asimple.entity.SubClass">
    <id property="id" column="id"/>
    <result property="name" column="name"/>
    <collection property="types" column="id" select="selectType"/>
</resultMap>
```

支持多层级联（Type → SubClass → CataLog）。

### 7. 动态 SQL

使用 MyBatis 动态 SQL 标签：

```xml
<select id="searchFilm" resultType="com.asimple.entity.Film">
    SELECT <include refid="filmColumn"/> FROM `t_film`
    <where>
        <if test="key != null and key != ''">
            AND `name` LIKE CONCAT('%', #{key}, '%')
        </if>
        <if test="typeId != null and typeId != ''">
            AND `type_id` = #{typeId}
        </if>
        AND `is_use` = 1
    </where>
    ORDER BY `add_time` DESC
</select>
```

### 8. selectKey 使用 UUID 先于 Insert

部分 Mapper 使用 `<selectKey>` 在 insert 前生成 UUID，替代 Java 端的 `Tools.UUID()`：

```xml
<insert id="add" parameterType="com.asimple.entity.User">
    <selectKey keyProperty="id" resultType="String" order="BEFORE">
        SELECT replace(uuid(), '-', '')
    </selectKey>
    INSERT INTO `t_user` (`id`, `username`, `password`)
    VALUES (#{id}, #{username}, #{password})
</insert>
```

### 9. 多参数引用（param1/param2）

当接口方法不使用 `@Param` 且有多个参数时，XML 中使用 `#{param1}`、`#{param2}` 按位置引用：

```xml
<select id="listByCataLogId" resultType="com.asimple.entity.Film">
    SELECT * FROM `t_film` WHERE `cataLog_id` = #{param1}
    <if test="param2 != null">
        LIMIT 0, #{param2}
    </if>
</select>
```

### 10. HashMap 返回类型

对于非实体的键值对查询，使用 `java.util.HashMap` 作为 resultType：

```xml
<select id="getViewHistory" resultType="java.util.HashMap">
    SELECT * FROM `t_view_history` WHERE `film_id` = #{filmId}
</select>
```

### 11. 新增（无 selectKey）

```xml
<insert id="add" parameterType="com.asimple.entity.Film">
    INSERT INTO `t_film` (`id`, `name`, `image`, `url`, `type_id`)
    VALUES (#{id}, #{name}, #{image}, #{url}, #{typeId})
</insert>
```

### 12. 完整示例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.asimple.mapper.FilmMapper">

    <sql id="filmColumn">
        `id`, `name`, `image`, `url`, `view_number`,
        `comment_number`, `is_use`, `add_time`,
        `type_id`, `cata_log_id`, `sub_class_id`
    </sql>

    <select id="getById" resultType="com.asimple.entity.Film">
        SELECT <include refid="filmColumn"/> FROM `t_film` WHERE `id` = #{id}
    </select>

    <insert id="add" parameterType="com.asimple.entity.Film">
        INSERT INTO `t_film` (`id`, `name`, `image`, `url`, `type_id`, `cata_log_id`)
        VALUES (#{id}, #{name}, #{image}, #{url}, #{typeId}, #{cataLogId})
    </insert>

    <update id="edit" parameterType="com.asimple.entity.Film">
        UPDATE `t_film`
        <set>
            <if test="name != null">`name` = #{name},</if>
            <if test="image != null">`image` = #{image},</if>
            <if test="viewNumber != null">`view_number` = #{viewNumber},</if>
        </set>
        WHERE `id` = #{id}
    </update>

</mapper>
```
