package com.asimple.controller;

import com.asimple.entity.User;
import com.asimple.entity.VipCode;
import com.asimple.service.IUserService;
import com.asimple.service.IVipCodeService;
import com.asimple.util.MD5Auth;
import com.asimple.util.Tools;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
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
    private final static String USER_KEY = "u_skl";
    @Resource( name="userService")
    private IUserService userService;
    @Resource( name = "vipCodeService")
    private IVipCodeService vipCodeService;


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
        session.removeAttribute("adminUser");
        jsonObject.put("code","1");
        return jsonObject.toString();
    }

    /**
     * @Author Asimple
     * @Description 使用VIP卡号
     **/
    @RequestMapping( value = "/vipCodeVerification.html")
    @ResponseBody
    public String vipCodeVerification(String vip_code, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        VipCode vipCode = vipCodeService.findByVipCode(vip_code);
        if( vip_code != null ) {// 卡是不为空
            User user_temp = (User) session.getAttribute(USER_KEY);
            User user = userService.load(user_temp.getId());
            if( user != null ) {// 用户不为空
                // 判断当前改用户的到期时间是否比当前时间大
                Date expireTime = user.getExpireDate();
                Date expireTimeTemp = expireTime;
                long isVip = user.getIsVip();
                // 使用Calendar类操作时间会简洁很多
                Calendar rightNow = Calendar.getInstance();
                if( expireTime.getTime() > new Date().getTime() ) rightNow.setTime(expireTime);
                // 添加一个月的时间
                rightNow.add(Calendar.MONTH,1);
                expireTime = rightNow.getTime();
                // 重新设置VIP到期时间
                user.setExpireDate(expireTime);
                user.setIsVip(1);
                if( userService.update(user) ) { // 更新用户信息成功
                    // 设置VIP卡为不可用
                    vipCode.setExpire_time(new Date());
                    vipCode.setIsUse(0);
                    if( vipCodeService.update(vipCode) ) {
                        jsonObject.put("code", "1");
                    } else {
                        user.setExpireDate(expireTimeTemp);
                        user.setIsVip(isVip);
                        userService.update(user);
                        jsonObject.put("code", "0");
                        jsonObject.put("error", "系统繁忙，请稍后重试！");
                    }
                    session.setAttribute(USER_KEY, user);
                } else {
                    jsonObject.put("code", "0");
                    jsonObject.put("error", "加油失败，请稍后重试！");
                }
            } else {
                jsonObject.put("code","0");
                jsonObject.put("error","用户信息错误！");
            }
        } else {
            jsonObject.put("code", "0");
            jsonObject.put("error", "VIP加油卡号不存在");
        }

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
