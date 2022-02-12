package com.dang.blog.pojo;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 9:00
 */
@Data
public class Article {

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;
    /**
     * 置顶
     */
    private Integer weight;
    /**
     * 创建时间
     */
    private Long createDate;
}
