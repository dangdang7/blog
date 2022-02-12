package com.dang.blog.controller;

import com.dang.blog.common.Result;
import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: srx
 * @date: 2022/2/9 12:59
 */
@RestController
@RequestMapping("users")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取相应token的用户信息
     * @param token
     * @return
     */
    @GetMapping("currentUser")
    @LogAnnotation(module = "博客首页",operator = "获取登录用户信息（token）")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }

}
