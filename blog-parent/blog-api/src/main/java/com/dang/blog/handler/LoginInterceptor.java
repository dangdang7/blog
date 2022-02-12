package com.dang.blog.handler;

import com.alibaba.fastjson.JSON;
import com.dang.blog.common.ErrorCode;
import com.dang.blog.common.Result;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.service.LoginService;
import com.dang.blog.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: srx
 * @date: 2022/2/9 14:08
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    /**
     * 在执行controller方法之前进行执行
     * 1、需要判断请求的接口路径 是否为 HandlerMethod
     * 2、判断 token 是否为空，如果为空，未登录
     * 3、如果 token 不为空，进行登录验证 loginService checkLogin
     * 4、如果 认证成功则放行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不是我们需要的登录拦截方法，直接放行
        // 可能是 RequestResourceHandler springboot程序 访问静态资源 默认去classpath下的static目录下
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        // 认证token，认证前打印个日志
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("==================request end============================");

        SysUser sysUser = loginService.checkToken(token);
        if(sysUser == null){
            Result result = Result.failure(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        // 登录验证成功，放行
        // 登陆成功放行后，我想在controller中获取我们的用户信息怎么获取呢？ ===> 使用TheadLocal
        UserThreadLocal.put(sysUser);
        return true;
    }

    /**
     * 在controller请求结束后做收尾工作的，可以看成finally
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 如果不删除 ThreadLocal 中的信息，会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
