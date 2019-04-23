package com.asimple.controller;

import com.asimple.entity.User;
import com.asimple.entity.VipCode;
import com.asimple.service.*;
import com.asimple.util.*;
import net.sf.json.JSONObject;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName video
 * @Description: 用户登录注册以及等级验证、个人中心
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
     * @Description 修改密码
     **/
    @RequestMapping(value = "/updatePassword.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updatePassword(HttpSession session, String oldPwd, String newPwd) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) session.getAttribute(USER_KEY);
        if( MD5Auth.validatePassword(user.getUserPasswd(), oldPwd+key, "UTF-8") ) {
            user.setUserPasswd(MD5Auth.MD5Encode(newPwd+key, "UTF-8"));
            userService.update(user);
            jsonObject.put("code", 1);
            session.removeAttribute(USER_KEY);
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "旧密码输入错误!");
        }
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

    /**
     * @Author Asimple
     * @Description 初始化验证码
     **/
    @RequestMapping( value = "/initCaptcha.html")
    @ResponseBody
    public void StartCaptcha(HttpServletRequest request, HttpServletResponse response) {
       try {
           GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                   GeetestConfig.isnewfailback());
           String resStr = "{}";
           //自定义参数,可选择添加
           HashMap<String, String> param = new HashMap<String, String>();
           param.put("user_id", "Asimple");
           param.put("client_type", "web");
           param.put("ip_address", "127.0.0.1");

           //进行验证预处理
           int gtServerStatus = gtSdk.preProcess(param);
           //将服务器状态设置到session中
           request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
           //将userid设置到session中
           request.getSession().setAttribute("userid", "Asimple");
           resStr = gtSdk.getResponseStr();
           PrintWriter out = response.getWriter();
           out.println(resStr);
       } catch (Exception e) {
           LogUtil.error("验证码加载失败！");
       }
    }

    /**
     * @Author Asimple
     * @Description 二次验证校验
     **/
    @RequestMapping( value = "/verifyLogin.html")
    @ResponseBody
    public void VerifyLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        int gtResult = 0;
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }

        if (gtResult == 1) {
            // 验证成功
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            try {
                data.put("status", "success");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.println(data.toString());
        }
        else {
            // 验证失败
            JSONObject data = new JSONObject();
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrintWriter out = response.getWriter();
            out.println(data.toString());
        }
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
