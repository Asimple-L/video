package com.asimple.controller;

import com.asimple.entity.User;
import com.asimple.service.IUserService;
import com.asimple.util.MD5Auth;
import com.asimple.util.Tools;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 用户登录注册以及等级验证
 * @author: Asimple
 */

@Controller
public class Authentication {
    private final static String key = "a1s2i3m4p5l6e7";
    public final static String USER_KEY = "u_skl";
    @Resource( name="userService")
    private IUserService userService;
    /**
     * @Author Asimple
     * @Description 进入注册页面
     **/
    @RequestMapping(value = "/registerInput.html")
    public String registerInput() {
        return "authentication/register_input";
    }

    /**
     * @Author Asimple
     * @Description 用户注册
     **/
    @RequestMapping(value = "/register.html")
    @ResponseBody
    public String register(User user, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        User userCondition = new User();
        userCondition.setUserEmail(user.getUserEmail());
        List<User> users = userService.findByCondition(userCondition);
        if( users == null || users.isEmpty() ) {
            userCondition = new User();
            userCondition.setUserName(user.getUserName());
            users = userService.findByCondition(user);
            if( users == null || users.isEmpty() ) {
                user.setCreateDate(new Date());
                user.setExpireDate(new Date());
                user.setUserPasswd(MD5Auth.MD5Encode(user.getUserPasswd()+key, "UTF-8"));
                User u = userService.add(user);
                if( u != null ) {
                    jsonObject.put("code","1");
                    jsonObject.put("data",u);
                    session.setAttribute(USER_KEY,u);
                } else {
                    jsonObject.put("code","0");
                    jsonObject.put("error","注册失败！请稍后重试");
                }
            } else {
                jsonObject.put("code","0");
                jsonObject.put("error","该用户名已存在！");
            }
        } else {
            jsonObject.put("code", "0");
            jsonObject.put("error", "该邮箱已经被注册！");
        }
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 用户登录
     **/
    @RequestMapping(value = "/login.html")
    @ResponseBody
    public String login(String account_l, String password_l, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        List<User> users = null;
        // 用户登录可以是邮箱或者用户名，需要进行两次匹配
        if ( Tools.notEmpty(account_l) ) {
            user.setUserName(account_l);
            users = userService.findByCondition(user);
        }
        if ( users!=null && users.size()>0 ) {
            checkAccount(password_l, session, jsonObject, users);
        } else {
            user.setUserEmail(account_l);
            users = userService.findByCondition(user);
            if( users!=null && users.size()>0 ) {
                checkAccount(password_l, session, jsonObject, users);
            } else {
                jsonObject.put("code", "0");
                jsonObject.put("error","登录失败，用户不存在或密码错误！");
            }
        }
        return jsonObject.toString();
    }
    /**
     * @Author Asimple
     * @Description 登出
     **/
    @RequestMapping( value = "/logout.html")
    @ResponseBody
    public String logout(HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        session.removeAttribute(USER_KEY);
        jsonObject.put("code","1");
        return jsonObject.toString();
    }

    // 检查用户登录信息是否正确
    private void checkAccount(String password, HttpSession session, JSONObject jsonObject, List<User> users) {
        User userDb = users.get(0);
        if( MD5Auth.validatePassword(userDb.getUserPasswd(), password+key, "UTF-8")) {
            jsonObject.put("code", "1");
            /*进行VIP身份过期校验*/
            if(userDb.getExpireDate().getTime()<=new Date().getTime()){
                /*当前过期时间与当前的时间小，则表示已经过期*/
                userDb.setIsVip(0);
                userService.update(userDb);
            }
            session.setAttribute(USER_KEY,userDb);
        } else {
            jsonObject.put("code", "0");
            jsonObject.put("error","用户或密码错误！");
        }
    }

}
