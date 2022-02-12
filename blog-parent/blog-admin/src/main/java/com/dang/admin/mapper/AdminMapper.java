package com.dang.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dang.admin.pojo.Admin;
import com.dang.admin.pojo.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/10 11:44
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据admin id查找相应admin 所有权限
     * SELECT * FROM `ms_permission` WHERE id in (SELECT permission_id FROM `ms_admin_permission` WHERE admin_id = 1)
     * @param adminId
     * @return
     */
    // @Select("SELECT * FROM `ms_permission` WHERE id in (SELECT permission_id FROM `ms_admin_permission` WHERE admin_id = #{adminId})")
    List<Permission> findPermissionById(@Param("adminId") Long adminId);

}
