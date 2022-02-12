package com.dang.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/10 11:40
 */
@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}
