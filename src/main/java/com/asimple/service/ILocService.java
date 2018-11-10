package com.asimple.service;

import com.asimple.entity.Loc;

import java.util.List;

public interface ILocService {
    // 查询所有在使用的地区信息
    List<Loc> listIsUse();
    // 添加地区并返回id
    String add(Loc loc);
}
