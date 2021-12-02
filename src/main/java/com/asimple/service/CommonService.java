package com.asimple.service;

import com.asimple.entity.*;
import com.asimple.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Asimple
 * @ProjectName video
 * @description 公共方法包括Redis和Solr的相关操作实现
 */
@Service
public class CommonService {
    @Resource
    private CataLogService cataLogService;
    @Resource
    private DecadeService decadeService;
    @Resource
    private LocService locService;
    @Resource
    private LevelService levelService;
    @Resource
    private FilmService filmService;
    @Resource
    private TypeService typeService;
    @Resource
    private UserService userService;

    /**
     * 更新影片信息
     *
     * @param film 影视
     * @return 是否更新成功
     */
    public boolean updateFilmInfo(FilmUpdateInfo film) {
        LogUtil.info(CommonService.class, "film = " + film);
        return filmService.update(film);
    }

    /**
     * 检查用户登录
     *
     * @param account  账户名称
     * @param password 登录密码
     * @return 登录成功返回user对象 否则返回null
     */
    public User checkUser(String account, String password) {
        User user = new User();
        List<User> users = null;
        // 用户登录可以是邮箱或者用户名，需要进行两次匹配
        if (Tools.notEmpty(account)) {
            user.setUserName(account);
            users = userService.findByCondition(user);
        }
        if (users != null && users.size() > 0) {
            return checkAccount(password, users);
        }
        user.setUserEmail(account);
        users = userService.findByCondition(user);
        if (users != null && users.size() > 0) {
            return checkAccount(password, users);
        }
        return null;
    }

    /**
     * 获取所有分类信息
     *
     * @return 分类信息
     */
    public Map<String, Object> getCatalog() {
        Map<String, Object> result = new HashMap<>(4);
        List<Loc> locList = locService.listIsUse();
        List<Level> levelList = levelService.listIsUse();
        List<Decade> decadeList = decadeService.listIsUse();
        List<CataLog> cataLogList = cataLogService.listIsUse();

        //读取路径下的文件返回UTF-8类型json字符串
        result.put("locList", locList);
        result.put("levelList", levelList);
        result.put("decadeList", decadeList);
        result.put("cataLogList", cataLogList);
        return result;
    }

    /**
     * 检查用户登录信息是否正确
     *
     * @param password 密码
     * @param users    用户信息
     * @return 更新成功返回更新后的user对象 否则返回null
     */
    private User checkAccount(String password, List<User> users) {
        User userDb = users.get(0);
        if (MD5Auth.validatePassword(userDb.getUserPasswd(), password + VideoKeyNameUtil.PASSWORD_KEY, VideoKeyNameUtil.ENCODE)) {
            /*进行VIP身份过期校验*/
            if (userDb.getExpireDate().getTime() <= System.currentTimeMillis()) {
                /*当前过期时间与当前的时间小，则表示已经过期*/
                userDb.setIsVip(0);
                Map<String, Object> param = new HashMap<>(2);
                param.put("user", userDb);
                userService.update(param);
            }
            return userDb;
        }
        return null;
    }
}
