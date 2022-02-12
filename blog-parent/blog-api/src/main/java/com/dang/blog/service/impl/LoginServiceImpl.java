package com.dang.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.dang.blog.common.Constants;
import com.dang.blog.common.ErrorCode;
import com.dang.blog.common.Result;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.service.LoginService;
import com.dang.blog.service.SysUserService;
import com.dang.blog.util.JWTUtils;
import com.dang.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: srx
 * @date: 2022/2/9 12:08
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 登录
     * 1、检查参数是否合法
     * 2、根据用户名和密码去user表中查询是否存在
     * 3、如果不存在登陆失败
     * 4、如果存在，使用jwt生成token，返回前端
     * 5、把token放入redis当中，redis中存储 token：user对应关系，设置过期
     * 6、登录认证的时候，先认证token字符串是否合法，去redis认证是否存在
     * @param loginParams
     * @return
     */
    @Override
    public Result login(LoginParams loginParams) {
        // 检查参数合法性
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.failure(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }

        // 密码加盐
        password = DigestUtils.md5Hex(password+ Constants.SALT);
        System.out.println(password);

        // 验证账号密码
        SysUser user = sysUserService.findUser(account,password);
        if(user == null){
            return Result.failure(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        // 将用户信息以token作key存入redis
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /**
     * 检查token合法性（是否为空、解析是否成功、redis中是否存在）
     * @param token
     * @return
     */
    @Override
    public SysUser checkToken(String token) {
        // 检测是否为空
        if(StringUtils.isBlank(token)){
            return null;
        }

        // 检测解析是否成功
        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map == null){
            return null;
        }

        // 检测redis中是否存在
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;

    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }
}
