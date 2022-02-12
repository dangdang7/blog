package com.dang.blog.service;

import com.dang.blog.common.Result;
import com.dang.blog.vo.params.RegisterParams;

/**
 * @author: srx
 * @date: 2022/2/9 13:34
 */
public interface RegisterService {

    /**
     * 注册用户
     * @param registerParams
     * @return
     */
    Result register(RegisterParams registerParams);
}
