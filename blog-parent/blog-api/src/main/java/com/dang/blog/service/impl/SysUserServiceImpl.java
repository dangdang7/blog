package com.dang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dang.blog.common.ErrorCode;
import com.dang.blog.common.Result;
import com.dang.blog.mapper.SysUserMapper;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.service.LoginService;
import com.dang.blog.service.SysUserService;
import com.dang.blog.vo.LoginUserVo;
import com.dang.blog.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: srx
 * @date: 2022/2/9 10:13
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    /**
     * 根据id查询用户（作者）
     * @param userId
     * @return
     */
    @Override
    public SysUser findUserById(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if(sysUser == null){
            sysUser= new SysUser();
            sysUser.setNickname("默认用户");
        }
        return sysUser;
    }

    /**
     * 获取登录用户
     * @param account
     * @param password
     * @return
     */
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 通过token获取登录的用户信息
     * 1、检查token合法性（是否为空、解析是否成功、redis中是否存在）
     * 2、token不合法：返回错误信息
     * 3、token合法：返回正确结果 LoginUserVo
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        // 检查token合法性
        SysUser sysUser = loginService.checkToken(token);

        // token不合法
        if(sysUser == null){
            return Result.failure(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }

        // token合法
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setNickname(sysUser.getNickname());

        return Result.success(loginUserVo);
    }

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    /**
     * 保存注册的用户信息
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        // 这里的id 默认是使用雪花算法生成的分布式id
        sysUserMapper.insert(sysUser);
    }

}
