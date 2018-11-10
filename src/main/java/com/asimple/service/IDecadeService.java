package com.asimple.service;

import com.asimple.entity.Decade;

import java.util.List;

public interface IDecadeService {
    // 查询所有再使用的年份
    List<Decade> listIsUse();
    // 添加新的年份信息，并返回添加的主键
    String add(Decade decade);
}
