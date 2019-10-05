package com.asimple.mapper;

import com.asimple.entity.SubClass;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Asimple
 * @description 二级分类mapper类
 */

@Repository
public interface SubClassMapper extends IBaseDao<SubClass> {
    /**
     * 查询一级分类下所有可用的二级分类
     * @param id 一级分类id
     * @return 二级分类列表
     */
    List<SubClass> listIsUse(String id);
}
