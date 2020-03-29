package com.asimple.util;

import com.asimple.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description 入参处理util
 * @Author Asimple
 * @date 2020/3/8 16:38
 */
public class RequestUtil {

    /**
     * 获取当前登录人信息
     * @param request 请求
     * @return 当前登录人信息
     */
    public static User getUserInformation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
    }

    /**
     * 登出 清除登录信息
     * @param request 请求
     */
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(VideoKeyNameUtil.USER_KEY);
        session.removeAttribute(VideoKeyNameUtil.ADMIN_USER_KEY);
    }

    /**
     * 入参鉴权：是否登录
     * @param request 请求
     * @return 有登录人返回true
     */
    public static boolean isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        return user!=null;
    }

    /**
     * 不是本人登录
     * @param request 请求
     * @return 是本人登录返回false
     */
    public static boolean isNotSelfLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        String uid = request.getParameter("uid");
        return !(isLogin(request) && user.getId().equals(uid));
    }

    /**
     * 是否管理员登录
     * @param request 请求信息
     * @return 当前登录用户是管理返回true
     */
    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        return user!=null && user.getIsManager()==1;
    }

}
