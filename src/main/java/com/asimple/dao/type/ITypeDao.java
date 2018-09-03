package com.asimple.dao.type;

import com.asimple.entity.Type;
import com.asimple.util.IBaseDao;

import java.util.List;

/**
 * @ProjectName video
 * @Description: 类型Dao层
 * @author: Asimple
 */
public interface ITypeDao extends IBaseDao<Type> {
    List<Type> listIsUseBySubClass_id(String subClass_id);
}
