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
    public List<User> findByCondition(User user) {
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

        String key = null;
        if( null != param.get("key") ) {
            key = (String) param.get("key");
        }
        if( StringUtils.equalsIgnoreCase("manager", key) ) {
            int isManager = userDb.getIsManager();
            userDb.setIsManager(1-isManager);
        } else if( StringUtils.equalsIgnoreCase("vip", key)) {
            long isVip = userDb.getIsVip();
            userDb.setIsVip(1L-isVip);
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
     * 带分页查询所有用户
     * @param param 参数
     * @return 用户分页列表
     */
    public PageBean<User> getPage(Map<String, Object> param) {
        User user = null;
        if( null != param.get("user") ) {
            user = (User) param.get("user");
        }
        String page;
        if( null == param.get("page") || StringUtils.isEmpty((String) param.get("page")) ) {
            page = "1";
        } else {
            page = (String) param.get("page");
        }
        String pageSize;
        if( null == param.get("pageSize") || StringUtils.isEmpty((String) param.get("pageSize")) ) {
            pageSize = "10";
        } else {
            pageSize = (String) param.get("pageSize");
        }
        int pc = Integer.parseInt(page);
        // 设置每页的条数
        int ps = Integer.parseInt(pageSize);
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setPc(pc);
        pageBean.setPs(ps);
        // 设置总数
        pageBean.setTr(userMapper.getTotalCount(user));
        // 最开始的条数
        pageBean.setBeanList(userMapper.getPage(user, (pc-1)*ps, ps));
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
