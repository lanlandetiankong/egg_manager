package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermission;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRole;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermission;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RolePermissionMapper;
import com.egg.manager.persistence.em.user.pojo.vo.RolePermissionVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface RolePermissionService extends IService<RolePermission>, MyBaseMysqlService<RolePermission, RolePermissionMapper, RolePermissionVo> {

    /**
     * 查询 角色列表 拥有的所有权限
     * @param defineRoles
     * @return
     */
    List<DefinePermission> dealQueryPageByEntitys(List<DefineRole> defineRoles);
}
