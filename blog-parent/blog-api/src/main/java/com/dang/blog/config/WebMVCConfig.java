package com.dang.blog.config;

import com.dang.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: srx
 * @date: 2022/2/8 23:47
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        // 本地测试 端口不一致 也算跨域
        // registry.addMapping("/**").allowedOrigins("https://47.104.72.216:8081");
        registry.addMapping("/**").allowedOrigins("http://localhost:8081");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 暂时拦截test，后续实际需要在配置为真正的拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");;
    }

}
