package com.asimple.config;

import com.asimple.interceptor.AdminUserInterceptor;
import com.asimple.interceptor.ProfileInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ProjectName video
 * @description web配置
 * @author Asimple
 * @date 2019/8/31 14:39
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 添加拦截器，可以一次添加多个，下面的会覆盖上面的
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminUserInterceptor()).addPathPatterns("/admin/**");
        registry.addInterceptor(new ProfileInterceptor()).addPathPatterns("/profile/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .maxAge(3600);
    }
}
