package com.dang.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 9:30
 */
@Data
public class ArticleVo {

    // @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;

    private String createDate;

    private SysUserVo author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}

