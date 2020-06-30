package com.asimple.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.asimple.entity.User;
import com.asimple.util.ResponseReturnUtil;
import com.asimple.util.VideoKeyNameUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ProjectName video
 * @description 个人中心拦截器
 * @author Asimple
 */
public class ProfileInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:7001");
        // 当请求的地址写在了上面，此项必须为true
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        //如果是获取类型则放行
        if (request.getRequestURL().indexOf("/getType")>0 || request.getRequestURL().indexOf("getSubClass")>0 ) {
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(VideoKeyNameUtil.USER_KEY);
        if( user!=null ) {
            return true;
        }
        JSONObject jsonObject = ResponseReturnUtil.returnErrorWithMsg("请先登录!");
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
