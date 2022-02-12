package com.dang.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dang.blog.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 9:03
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(@Param("articleId") Long articleId);

    /**
     * 查询指定limit数量的最热tag id集合
     * SELECT tag_id FROM `ms_article_tag` group by tag_id order by count(*) desc limit 2
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(@Param("limit") int limit);

    /**
     * 根据tagId集合查找tag对象
     * select * from `ms_tag` where id in (1,2,3,4)
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(@Param("tagIds") List<Long> tagIds);
}
