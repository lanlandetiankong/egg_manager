package com.egg.manager.api.services.basic.role;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;

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
