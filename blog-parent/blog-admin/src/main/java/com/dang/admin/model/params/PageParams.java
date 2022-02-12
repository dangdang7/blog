package com.dang.admin.model.params;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/10 11:04
 */
@Data
public class PageParams {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
