package com.dang.blog.pojo;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 16:51
 */
@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
