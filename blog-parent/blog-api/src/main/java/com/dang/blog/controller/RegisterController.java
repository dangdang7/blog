package com.dang.blog.controller;

import com.dang.blog.common.Result;
import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.service.LoginService;
import com.dang.blog.service.RegisterService;
import com.dang.blog.vo.params.RegisterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: srx
 * @date: 2022/2/9 13:31
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 注册
     * @param registerParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "博客首页",operator = "注册功能")
    public Result register(@RequestBody RegisterParams registerParams){
        return registerService.register(registerParams);
    }

}
