package com.egg.manager.service.role;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.dto.login.LoginAccountDTO;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.role.RolePermission;
import com.egg.manager.entity.user.UserAccount;

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
