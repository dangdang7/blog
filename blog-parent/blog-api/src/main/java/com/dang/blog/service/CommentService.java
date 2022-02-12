package com.dang.blog.service;

import com.dang.blog.common.Result;
import com.dang.blog.vo.params.CommentParams;

/**
 * @author: srx
 * @date: 2022/2/9 16:57
 */
public interface CommentService {

    /**
     * 获取相应文章id的评论
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 评论功能接口
     * @param commentParams
     * @return
     */
    Result comment(CommentParams commentParams);
}
