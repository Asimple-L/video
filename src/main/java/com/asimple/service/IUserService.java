package com.asimple.service;

import com.asimple.entity.User;
import com.asimple.util.PageBean;

import java.util.List;

public interface IUserService {
    // 有条件查询用户
    List<User> findByCondition(User user);
    // 添加用户
    User add(User user);
    // 更新用户信息
    Boolean update(User userDb);
    // 加载用户
    User load(String id);
    // 带分页查询
    PageBean<User> getPage(User user, int pc, int pageSize);
}
