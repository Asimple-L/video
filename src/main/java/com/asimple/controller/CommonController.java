package com.asimple.controller;

import com.asimple.entity.User;
import com.asimple.util.VideoKeyNameUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Asimple
 * @description controller公共类
 */
@Component
public class CommonController {

    User getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
    }

    User getAdminUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(VideoKeyNameUtil.ADMIN_USER_KEY);
    }

}
