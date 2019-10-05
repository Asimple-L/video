package com.asimple.util;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao层基础接口
 * @author Asimple
 */

public interface IBaseDao<T> {
    /**
     * 统一方法： 新增
     * @param t 实体
     * @return 返回1为添加成功
     */
    int add(T t);

    /**
     * 统一方法： 删除
     * @param t 实体
     * @return 返回1为删除成功
     */
    int delete(T t);

    /**
     * 统一方法： 根据id删除实体
     * @param id 实体id
     * @return 返回1为添加成功
     */
    int deleteById(String id);

    /**
     * 统一方法： 更新
     * @param t 实体，必须要有id
     * @return 返回1为成功
     */
    int update(T t);

    /**
     * 统一方法： 根据id加载实体
     * @param id 实体id
     * @return 实体
     */
    T load(String id);

    /**
     * 统一方法： 查询所有信息
     * @return 实体列表
     */
    List<T> getAll();

    /**
     * 统一方法： 有条件查询实体
     * @param t 实体
     * @return 实体列表
     */
    List<T> findByCondition(T t);

    /**
     * 统一方法： 查询在使用的信息
     * @return 实体信息
     */
    List<T> findByIsUse();

    /**
     * 统一方法： 查询信息总数
     * @param t 实体
     * @return 信息总数
     */
    int getTotalCount(@Param("obj") T t);

    /**
     * 统一方法： 分页获取数据
     * @param t 实体
     * @param start 开始行
     * @param count 结束行
     * @return 分页列表
     */
    List<T> getPage(@Param("obj") T t,@Param("start") int start, @Param("count") int count);
}
