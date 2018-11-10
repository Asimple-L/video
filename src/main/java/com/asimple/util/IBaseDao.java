package com.asimple.util;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBaseDao<T> {
    // 增删改方法以及查询所有
    int add(T t);
    int delete(T t);
    int deleteById(String id);
    int update(T t);
    T load(String id);
    List<T> getAll();
    List<T> findByCondition(T t);
    List<T> findByIsUse();

    // 分页相关
    int getTotalCount(@Param("obj") T t);
    List<T> getPage(@Param("obj") T t,@Param("start") int start, @Param("count") int count);
}
