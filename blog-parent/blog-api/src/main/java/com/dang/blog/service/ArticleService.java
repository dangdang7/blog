package com.dang.blog.service;

import com.dang.blog.common.Result;
import com.dang.blog.vo.params.ArticleParams;
import com.dang.blog.vo.params.PageParams;

/**
 * @author: srx
 * @date: 2022/2/9 9:16
 */
public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 获取最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 获取最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 获取文章详情信息
     * @param articleId
     * @return
     */
    Result findArticleBodyByArticleId(Long articleId);

    /**
     * 发布文章
     * @param articleParams
     * @return
     */
    Result publish(ArticleParams articleParams);

}
