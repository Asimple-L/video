package com.asimple.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.asimple.entity.User;
import com.asimple.util.LogUtil;
import com.asimple.util.ResponseReturnUtil;
import com.asimple.util.VideoKeyNameUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Asimple
 * @ProjectName video
 * @description 后台拦截器
 */
public class AdminUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是登录login则放行
        if (request.getRequestURL().indexOf("/login") > 0) {
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(VideoKeyNameUtil.ADMIN_USER_KEY);
        if (user != null && user.getIsManager() == 1) {
            return true;
        }
        JSONObject jsonObject = ResponseReturnUtil.returnErrorWithMsg("请登录管理员账号!");
        ResponseReturnUtil.returnJson(response, jsonObject.toJSONString());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
