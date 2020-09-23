package com.egg.manager.api.services.basic.role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.mapper.role.RolePermissionMapper;
import com.egg.manager.persistence.pojo.mysql.vo.role.RolePermissionVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface RolePermissionService extends IService<RolePermission>,MyBaseMysqlService<RolePermissionMapper,RolePermission,RolePermissionVo> {

    /**
     * 查询 角色列表 拥有的所有权限
     * @param defineRoles
     * @return
     */
    List<DefinePermission> dealQueryPageByEntitys(List<DefineRole> defineRoles) ;
}
