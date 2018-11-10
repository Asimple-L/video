package com.asimple.service;

import com.asimple.entity.SubClass;

import java.util.List;

public interface ISubClassService {
    // 查询所有可使用的二级分类
    List<SubClass> listIsUse(String id);
    // 添加二级分类
    String add(SubClass subClass);
    // 根据id加载二级分类
    SubClass load(String subClass_id);
}
