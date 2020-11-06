package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermission;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;

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
    public static RolePermission generateSimpleInsertEntity(Long defineRoleId, Long definePermissionId, UserAccount loginUser) {
        RolePermission rolePermission = new RolePermission();
        Date now = new Date();
        //rolePermission.setFid(MyUUIDUtil.renderSimpleUuid());
        rolePermission.setDefineRoleId(defineRoleId);
        rolePermission.setDefinePermissionId(definePermissionId);
        rolePermission.setType(1);
        rolePermission.setState(BaseStateEnum.ENABLED.getValue());
        rolePermission.setCreateTime(now);
        rolePermission.setUpdateTime(now);
        if (loginUser != null) {
            rolePermission.setCreateUserId(loginUser.getFid());
            rolePermission.setLastModifyerId(loginUser.getFid());
        }
        rolePermission.setRemark(null);
        return rolePermission;
    }
}
