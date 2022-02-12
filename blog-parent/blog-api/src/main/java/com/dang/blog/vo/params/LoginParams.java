package com.dang.blog.vo.params;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: srx
 * @date: 2022/2/9 12:09
 */
@Data
public class LoginParams {

    private String account;

    private String password;

}
