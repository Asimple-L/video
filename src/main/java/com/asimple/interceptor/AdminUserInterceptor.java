package com.asimple.interceptor;

import com.asimple.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ProjectName video
 * @Description: 后台拦截器
 * @author: Asimple
 */
public class AdminUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是登录login则放行
        if (request.getRequestURL().indexOf("/login")>0) {
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if( user!=null && user.getIsManager()==1 ) return true;
        request.setAttribute("msg", "请登录正确的管理员账号");
        request.getRequestDispatcher("/WEB-INF/jsp/manager/login.jsp").forward(request, response);
        return false;
    }
}