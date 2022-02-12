package com.dang.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/10 11:09
 */
@Data
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String path;

    private String description;
}
