package com.asimple.service;

import com.asimple.entity.User;
import com.asimple.mapper.UserMapper;
import com.asimple.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 用户Service实现类
 * @author: Asimple
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * @Author Asimple
     * @Description 有条件查询用户
     **/
    public List<User> findByCondition(User user) {
        return userMapper.findByCondition(user);
    }

    /**
     * @Author Asimple
     * @Description 添加用户
     **/
    public User add(User user) {
        int f = userMapper.add(user);
        if( f == 1 ) return user;
        return null;
    }

    /**
     * @Author Asimple
     * @Description 更新用户信息
     **/
    public Boolean update(User userDb) {
        return userMapper.update(userDb)==1;
    }

    /**
     * @Author Asimple
     * @Description 加载用户
     **/
    public User load(String id) {
        return userMapper.load(id);
    }

    /**
     * @Author Asimple
     * @Description 带分页查询所有用户
     **/
    public PageBean<User> getPage(User user, int pc, int pageSize) {
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setPc(pc);
        pageBean.setPs(pageSize);
        // 设置总数
        pageBean.setTr(userMapper.getTotalCount(user));
        // 最开始的条数
        pageBean.setBeanList(userMapper.getPage(user, (pc-1)*pageSize, pageSize));
        return pageBean;
    }
}