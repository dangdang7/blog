package com.dang.blog.service;

import com.dang.blog.common.Result;
import com.dang.blog.vo.CategoryVo;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 15:51
 */
public interface CategoryService {

    /**
     * 根据id获得文章种类
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 查询文章所有分类
     * @return
     */
    Result findAll();

    /**
     * 顶部导航栏，查询所有文章分类
     * @return
     */
    Result findAllDetail();

    /**
     * 顶部导航栏，查询所有文章分类,进入点击
     * @return
     */
    Result findAllDetailById(Long categoryId);
}
