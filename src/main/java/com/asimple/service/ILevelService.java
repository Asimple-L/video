package com.asimple.service;

import com.asimple.entity.Level;

import java.util.List;

public interface ILevelService {
    // 查询所有在使用的等级信息
    List<Level> listIsUse();
    // 添加等级信息并返回id
    String add(Level level);
}
