package com.asimple.service;

import com.asimple.entity.Type;

import java.util.List;

public interface ITypeService {
    // 通过二级目录id查找所有可用的电影类型
    List<Type> listIsUseBySubClass_id(String subClass_id);

}
