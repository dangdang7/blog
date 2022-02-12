package com.dang.blog.controller;

import com.dang.blog.common.Result;
import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: srx
 * @date: 2022/2/9 13:25
 */
@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    /**
     * 退出登录
     * @param token
     * @return
     */
    @GetMapping
    @LogAnnotation(module = "博客首页",operator = "登出功能")
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }

}
