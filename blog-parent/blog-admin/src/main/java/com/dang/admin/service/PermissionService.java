package com.dang.admin.service;

import com.dang.admin.common.Result;
import com.dang.admin.model.params.PageParams;
import com.dang.admin.pojo.Permission;

/**
 * @author: srx
 * @date: 2022/2/10 11:05
 */
public interface PermissionService {

    /**
     * 获取权限列表
     * @param pageParams
     * @return
     */
    Result listPermission(PageParams pageParams);

    /**
     * 权限增加
     * @param permission
     * @return
     */
    Result add(Permission permission);

    /**
     * 权限更新
     * @param permission
     * @return
     */
    Result update(Permission permission);

    /**
     * 权限删除
     * @param id
     * @return
     */
    Result delete(Long id);

}
