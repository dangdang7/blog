package com.dang.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 9:02
 */
@Data
public class SysUser {

    // @TableId(type = IdType.ASSIGN_ID) // 默认类型id
    // 以后用户多了以后，要进行分表操作，id就要用分布式id
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
