package com.asimple.mapper;

import com.asimple.entity.Type;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName video
 * @description 类型Dao层
 * @author Asimple
 */
@Repository
public interface TypeMapper extends IBaseDao<Type> {
    /**
     * 查询二级分类下所有可用的类型
     * @param subClassId 二级分类id
     * @return 类型列表
     */
    List<Type> listIsUseBySubClassId(String subClassId);
}
