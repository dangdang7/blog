package com.dang.blog.service;

import com.dang.blog.common.Result;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.vo.SysUserVo;

/**
 * @author: srx
 * @date: 2022/2/9 10:12
 */
public interface SysUserService {

    /**
     * 根据id查询用户（作者）
     * @param userId
     * @return
     */
    SysUser findUserById(Long userId);

    /**
     * 获取登录用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 通过token获取登录的用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存注册的用户信息
     * @param sysUser
     */
    void save(SysUser sysUser);
}
