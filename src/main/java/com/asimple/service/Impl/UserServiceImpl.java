package com.asimple.service.Impl;

import com.asimple.dao.user.IUserDao;
import com.asimple.entity.User;
import com.asimple.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 用户Service实现类
 * @author: Asimple
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource(name="IUserDao")
    private IUserDao userDao;

    /**
     * @Author Asimple
     * @Description 有条件查询用户
     **/
    @Override
    public List<User> findByCondition(User user) {
        return userDao.findByCondition(user);
    }

    /**
     * @Author Asimple
     * @Description 添加用户
     **/
    @Override
    public User add(User user) {
        int f = userDao.add(user);
        if( f == 1 ) return user;
        return null;
    }

    /**
     * @Author Asimple
     * @Description 更新用户信息
     **/
    @Override
    public Boolean update(User userDb) {
        return userDao.update(userDb)==1;
    }

    /**
     * @Author Asimple
     * @Description 加载用户
     **/
    @Override
    public User load(String id) {
        return userDao.load(id);
    }
}
