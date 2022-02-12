package com.dang.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 17:08
 */
@Data
public class CommentVo  {

    // 防止前端精度损失，把id转成String
    // @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private SysUserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private SysUserVo toUser;
}
