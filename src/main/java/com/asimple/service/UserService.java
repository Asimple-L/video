package com.asimple.service;

import com.asimple.entity.User;
import com.asimple.mapper.UserMapper;
import com.asimple.util.MD5Auth;
import com.asimple.util.PageBean;
import com.asimple.util.VideoKeyNameUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName video
 * @description 用户Service实现类
 * @author Asimple
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * @author Asimple
     * @description 有条件查询用户
     **/
    private List<User> findByCondition(User user) {
        return userMapper.findByCondition(user);
    }

    /**
     * @author Asimple
     * @description 添加用户
     **/
    public User add(User user) {
        int f = userMapper.add(user);
        if( f == 1 ) {
            return user;
        }
        return null;
    }

    /**
     * 更新用户信息
     * @param param 参数必须包含user
     * @return 是否修改成功 true 成功
     */
    public boolean update(Map<String, Object> param) {
        User userDb = (User) param.get("user");
        if( null == userDb || StringUtils.isEmpty(userDb.getId()) ) {
            return false;
        }
        String newPwd = (String) param.get("newPwd");
        if(StringUtils.isNotEmpty(newPwd) ) {
            userDb.setUserPasswd(MD5Auth.MD5Encode(newPwd+VideoKeyNameUtil.PASSWORD_KEY, VideoKeyNameUtil.ENCODE));
        }
        return userMapper.update(userDb)==1;
    }

    /**
     * @author Asimple
     * @description 加载用户
     **/
    public User load(String id) {
        return userMapper.load(id);
    }

    /**
     * @author Asimple
     * @description 带分页查询所有用户
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

    /**
     * 用户注册
     * @param user 注册的用户信息
     * @return 注册成功返回用户信息，失败返回null
     */
    public User register(User user) {
        User userCondition = new User();
        userCondition.setUserEmail(user.getUserEmail());
        List<User> users = findByCondition(userCondition);
        if( users == null || users.isEmpty() ) {
            userCondition = new User();
            userCondition.setUserName(user.getUserName());
            users = findByCondition(user);
            if( users == null || users.isEmpty() ) {
                user.setCreateDate(new Date());
                user.setExpireDate(new Date());
                user.setUserPasswd(MD5Auth.MD5Encode(user.getUserPasswd()+ VideoKeyNameUtil.PASSWORD_KEY, VideoKeyNameUtil.ENCODE));
                return add(user);
            }
        }
        return null;
    }

    /**
     * 检查两个密码是否相同
     * @param userPwd 用户密码
     * @param oldPwd 输入密码
     * @return 是否一致 true 一致
     */
    public boolean checkPassword(String userPwd, String oldPwd) {
        return MD5Auth.validatePassword(userPwd, oldPwd+VideoKeyNameUtil.PASSWORD_KEY, VideoKeyNameUtil.ENCODE);
    }
}
