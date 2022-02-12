package com.dang.blog.async;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.dang.blog.mapper.ArticleMapper;
import com.dang.blog.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: srx
 * @date: 2022/2/9 16:19
 */
@Service
public class ThreadService {

    /**
     * 异步执行文章阅读数更新
     * 在线程池中执行，不影响原主线程
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());

        // 设置一个 为了在多线程的环境下线程安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);

        // 更新 update article set view_count=100 where view_count=99 and id=?
        articleMapper.update(articleUpdate,updateWrapper);
    }

    /**
     * 异步执行评论后，评论数+1
     * @param articleMapper
     * @param articleId
     */
    @Async("taskExecutor")
    public void updateArticleCommentCount(ArticleMapper articleMapper, Long articleId) {
        Article article = articleMapper.selectById(articleId);
        Integer commentCounts = article.getCommentCounts();
        Article articleUpdate = new Article();
        articleUpdate.setCommentCounts(commentCounts + 1);

        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());

        // 设置一个 为了在多线程的环境下线程安全
        updateWrapper.eq(Article::getCommentCounts,commentCounts);

        // 更新
        articleMapper.update(articleUpdate,updateWrapper);
    }
}
