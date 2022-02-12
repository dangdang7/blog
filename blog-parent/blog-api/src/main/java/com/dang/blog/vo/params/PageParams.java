package com.dang.blog.vo.params;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 9:09
 */
@Data
public class PageParams {
    /**
     * 当前页
     */
    private int page = 1;
    /**
     * 每页显示数量
     */
    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
