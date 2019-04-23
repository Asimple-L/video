package com.asimple.interceptor;

import com.asimple.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ProjectName video
 * @Description: 个人中心拦截器
 * @author: Asimple
 */
public class ProfileInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是获取类型则放行
        if (request.getRequestURL().indexOf("/getType")>0 || request.getRequestURL().indexOf("getSubClass")>0 ) {
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("u_skl");
        if( user!=null ) return true;
        response.sendRedirect("/video/index.html");
        return false;
    }
}
