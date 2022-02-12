package com.dang.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.dang.blog.common.Constants;
import com.dang.blog.common.ErrorCode;
import com.dang.blog.common.Result;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.service.RegisterService;
import com.dang.blog.service.SysUserService;
import com.dang.blog.util.JWTUtils;
import com.dang.blog.vo.params.RegisterParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author: srx
 * @date: 2022/2/9 13:34
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 注册
     * 1、判断参数是否合法
     * 2、判断账户是否存在，存在->已经被注册，不存在->进行注册
     * 3、生成token 存入redis
     * 4、注意：加上事务，一旦中间出现问题，注册的用户需要回滚
     * @param registerParams
     * @return
     */
    @Transactional
    @Override
    public Result register(RegisterParams registerParams) {
        // 检测合法性
        String account = registerParams.getAccount();
        String password = registerParams.getPassword();
        String nickname = registerParams.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)){
            return Result.failure(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }

        // 检测用户是否存在
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser != null){
            return Result.failure(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        // 不存在则注册新用户信息
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+ Constants.SALT));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("https://gulimall-dang.oss-cn-beijing.aliyuncs.com/EDG1.jpg");
        sysUser.setAdmin(1);   // 1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        // 存入redis
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

        return Result.success(token);
    }

}
