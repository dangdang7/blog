package com.dang.blog.vo.params;

import com.dang.blog.vo.CategoryVo;
import com.dang.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 18:34
 */
@Data
public class ArticleParams {

    private Long id;

    private ArticleBodyParams body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}
