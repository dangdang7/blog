package com.dang.blog.controller;

import com.dang.blog.common.Result;
import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.service.LoginService;
import com.dang.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: srx
 * @date: 2022/2/9 12:04
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param loginParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "博客首页",operator = "登录功能")
    public Result login(@RequestBody LoginParams loginParams){
        // 登录 验证用户 访问用户表
        return loginService.login(loginParams);
    }

}
