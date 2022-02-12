package com.dang.blog.service;

import com.dang.blog.common.Result;
import com.dang.blog.vo.TagVo;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 9:48
 */
public interface TagService {

    /**
     * 根据文章id查询文章所有标签
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签
     * @param limit 查询出来的标签数
     * @return
     */
    Result hots(int limit);

    /**
     * 查询文章所有标签
     * @return
     */
    Result findAll();

    /**
     * 顶部导航栏，查询所有文章标签
     * @return
     */
    Result findAllDetail();

    /**
     * 顶部导航栏，查询所有文章标签,进入点击
     * @param tagId
     * @return
     */
    Result findAllDetailById(Long tagId);
}
