package com.asimple.controller;

import com.asimple.entity.User;
import com.asimple.entity.VipCode;
import com.asimple.service.*;
import com.asimple.util.*;
import net.sf.json.JSONObject;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Asimple
 * @ProjectName video
 * @description 用户登录注册以及等级验证、个人中心
 */

@RestController
public class AuthenticationController extends CommonController {
    @Resource
    private UserService userService;
    @Resource
    private VipCodeService vipCodeService;
    @Resource
    private CommonService commonService;

    /**
     * @author Asimple
     * @description 用户注册
     **/
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(User user, HttpSession session) {
        User userDb = userService.register(user);
        if (null == userDb) {
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.USER_REGISTER_ERROR);
        }
        session.setAttribute(VideoKeyNameUtil.USER_KEY, userDb);
        return ResponseReturnUtil.returnSuccessMsgAndData(ResponseReturnUtil.USER_REGISTER_SUC, user);
    }

    /**
     * @author Asimple
     * @description 用户登录
     **/
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Object login(String account, String password, HttpSession session) {
        User user = commonService.checkUser(account, password);
        if (null != user) {
            session.setAttribute(VideoKeyNameUtil.USER_KEY, user);
            session.setAttribute(VideoKeyNameUtil.ADMIN_USER_KEY, user);
            Map<String, Object> result = new HashMap<>(1);
            result.put("user", user);
            return ResponseReturnUtil.returnSuccessWithData(result);
        }
        return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.USER_LOGIN_ERROR);
    }

    /**
     * @author Asimple
     * @description 登出
     **/
    @RequestMapping(value = "/logout")
    public Object logout(HttpServletRequest request) {
        RequestUtil.logout(request);
        return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
    }

    /**
     * @author Asimple
     * @description 修改密码
     **/
    @RequestMapping(value = "/updatePassword", produces = "application/json;charset=UTF-8")
    public Object updatePassword(HttpServletRequest request) {
        if (RequestUtil.isNotSelfLogin(request)) {
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.LOGIN_FIRST);
        }
        User user = getUserInfo(request);
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        if (userService.checkPassword(user.getUserPasswd(), oldPwd)) {
            Map<String, Object> param = new HashMap<>(1);
            param.put("user", user);
            param.put("newPwd", newPwd);
            userService.update(param);
            RequestUtil.logout(request);
            return ResponseReturnUtil.returnSuccessWithMsg(ResponseReturnUtil.UPDATE_SUC_NEED_LOGIN);
        }
        return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.PASSWORD_ERROR);
    }

    /**
     * @author Asimple
     * @description 使用VIP卡号
     **/
    @RequestMapping(value = "/vipCodeVerification")
    public Object vipCodeVerification(String vipCode, HttpServletRequest request) {
        User userTemp = getUserInfo(request);
        HttpSession session = request.getSession();
        VipCode code = vipCodeService.findByVipCode(vipCode);
        User user = userService.load(userTemp.getId());
        if (null != code && null != user) {
            Map<String, Object> param = new HashMap<>(1);
            param.put("user", user);
            param.put("vipCode", code);
            if (vipCodeService.useCode(param)) {
                session.setAttribute(VideoKeyNameUtil.USER_KEY, user);
                return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
            }
            return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.USE_VIP_CARD_FAIL);
        }
        return ResponseReturnUtil.returnErrorWithMsg(ResponseReturnUtil.VIP_CARD_NOT_FOUND);
    }

    /**
     * @author Asimple
     * @description 初始化验证码
     **/
    @RequestMapping(value = "/initCaptcha")
    public void startCaptcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                    GeetestConfig.isnewfailback());
            String resStr;
            //自定义参数,可选择添加
            HashMap<String, String> param = new HashMap<>(16);
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
     * @author Asimple
     * @description 二次验证校验
     **/
    @RequestMapping(value = "/verifyLogin")
    public void verifyLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gtServerStatusCode = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        //从session中获取userId
        String userId = (String) request.getSession().getAttribute("userid");
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<>(16);
        //网站用户id
        param.put("user_id", userId);
        //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("client_type", "web");
        //传输用户请求验证时所携带的IP
        param.put("ip_address", "127.0.0.1");

        int gtResult;
        if (gtServerStatusCode == 1) {
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
        } else {
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

}
