package com.dang.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dang.blog.dos.Archives;
import com.dang.blog.pojo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 9:01
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 获取文章归档信息
     * select year(FROM_UNIXTIME(create_date/1000)) as year,month(FROM_UNIXTIME(create_date/1000)) as month,count(*) as count from `ms_article` group by year,month
     * @return
     */
    List<Archives> listArchives();

    /**
     * 分页查询文章列表
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticle(@Param("page") Page<Article> page,
                               @Param("categoryId") Long categoryId,
                               @Param("tagId") Long tagId,
                               @Param("year") String year,
                               @Param("month") String month);
}
