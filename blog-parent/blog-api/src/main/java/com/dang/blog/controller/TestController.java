package com.dang.blog.controller;

import com.dang.blog.common.Result;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.util.UserThreadLocal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: srx
 * @date: 2022/2/9 14:32
 */
@RestController
@RequestMapping("test")
public class TestController {

    /**
     * 测试拦截器
     * @return
     */
    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(sysUser);
    }
}
