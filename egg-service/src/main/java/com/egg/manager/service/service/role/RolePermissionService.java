package com.egg.manager.service.service.role;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.entity.define.DefineRole;
import com.egg.manager.persistence.entity.role.RolePermission;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface RolePermissionService extends IService<RolePermission> {


    List<DefinePermission> dealGetAllPermissionByRoles(List<DefineRole> defineRoles) ;
}
