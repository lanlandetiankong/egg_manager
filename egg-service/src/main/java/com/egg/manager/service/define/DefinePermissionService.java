package com.egg.manager.service.define;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefinePermissionService extends IService<DefinePermission> {

    List<DefinePermission> dealGetAllPermissionByRoles(List<DefineRole> defineRoles) ;
}
