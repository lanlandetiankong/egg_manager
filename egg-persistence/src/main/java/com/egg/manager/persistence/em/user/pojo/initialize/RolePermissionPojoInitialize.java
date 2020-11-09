package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class RolePermissionPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param definePermissionId
     * @param loginUser
     * @return
     */
    public static RolePermissionEntity generateSimpleInsertEntity(Long defineRoleId, Long definePermissionId, UserAccountEntity loginUser) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        Date now = new Date();
        //rolePermission.setFid(MyUUIDUtil.renderSimpleUuid());
        rolePermissionEntity.setDefineRoleId(defineRoleId);
        rolePermissionEntity.setDefinePermissionId(definePermissionId);
        rolePermissionEntity.setType(1);
        rolePermissionEntity.setState(BaseStateEnum.ENABLED.getValue());
        rolePermissionEntity.setCreateTime(now);
        rolePermissionEntity.setUpdateTime(now);
        if (loginUser != null) {
            rolePermissionEntity.setCreateUserId(loginUser.getFid());
            rolePermissionEntity.setLastModifyerId(loginUser.getFid());
        }
        rolePermissionEntity.setRemark(null);
        return rolePermissionEntity;
    }
}
