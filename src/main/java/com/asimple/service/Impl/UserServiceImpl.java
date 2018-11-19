package com.asimple.service.Impl;

import com.asimple.dao.user.IUserDao;
import com.asimple.entity.User;
import com.asimple.service.IUserService;
import com.asimple.util.PageBean;
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

    /**
     * @Author Asimple
     * @Description 带分页查询所有用户
     **/
    @Override
    public PageBean<User> getPage(User user, int pc, int pageSize) {
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setPc(pc);
        pageBean.setPs(pageSize);
        // 设置总数
        pageBean.setTr(userDao.getTotalCount(user));
        // 最开始的条数
        pageBean.setBeanList(userDao.getPage(user, (pc-1)*pageSize, pageSize));
        return pageBean;
    }
}
