package com.asimple.service;

import com.asimple.entity.CataLog;

import java.util.List;

public interface ICataLogService {
    // 查询所有在使用的一级分类
    List<CataLog> listIsUse();
    // 添加一级分类并返回id
    String add(CataLog cataLog);
    // 根据id查询一级分类
    CataLog load(String cataLog_id);
}
