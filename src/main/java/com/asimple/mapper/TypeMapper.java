package com.asimple.mapper;

import com.asimple.entity.Type;
import com.asimple.util.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName video
 * @Description: 类型Dao层
 * @author: Asimple
 */
@Repository
public interface TypeMapper extends IBaseDao<Type> {
    List<Type> listIsUseBySubClass_id(String subClass_id);
}
