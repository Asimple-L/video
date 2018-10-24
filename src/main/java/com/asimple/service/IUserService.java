package com.asimple.service;

import com.asimple.entity.User;

import java.util.List;

public interface IUserService {

    List<User> findByCondition(User user);

    User add(User user);

    Boolean update(User userDb);
}
