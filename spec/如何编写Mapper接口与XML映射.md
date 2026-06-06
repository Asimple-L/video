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
    Integer add(T t);            // 新增
    Integer delete(Object id);   // 按ID删除
    Integer edit(T t);           // 编辑
    T getById(Object id);        // 按ID查询
}
```

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

### 6. resultMap 级联映射

当实体包含关联对象时，使用 `resultMap` 实现级联查询：

```xml
<resultMap id="filmMap" type="com.asimple.entity.Film">
    <id property="id" column="id"/>
    <result property="name" column="name"/>
    <!-- 关联分类 -->
    <association property="cataLog" javaType="com.asimple.entity.CataLog">
        <id property="id" column="cid"/>
        <result property="name" column="cname"/>
    </association>
    <!-- 关联子分类 -->
    <association property="subClass" javaType="com.asimple.entity.SubClass">
        <id property="id" column="sid"/>
        <result property="name" column="sname"/>
    </association>
    <!-- 关联类型 -->
    <association property="type" javaType="com.asimple.entity.Type">
        <id property="id" column="tid"/>
        <result property="name" column="tname"/>
    </association>
</resultMap>
```

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

### 8. 新增返回主键

```xml
<insert id="add" parameterType="com.asimple.entity.Film">
    INSERT INTO `t_film` (`id`, `name`, `image`, `url`, `type_id`)
    VALUES (#{id}, #{name}, #{image}, #{url}, #{typeId})
</insert>
```

### 9. 完整示例

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
