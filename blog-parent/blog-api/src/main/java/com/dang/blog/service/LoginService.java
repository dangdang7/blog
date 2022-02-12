package com.dang.blog.service;

import com.dang.blog.common.Result;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.vo.params.LoginParams;

/**
 * @author: srx
 * @date: 2022/2/9 12:07
 */
public interface LoginService {

    /**
     * 登录
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    /**
     * 检查token的合法性
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
}
