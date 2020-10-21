package com.egg.manager.persistence.pojo.mysql.initialize.role;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

import java.util.Date;

/**
 * @author zhoucj
 * @version V1.0
 * @description:
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
    public static RolePermission generateSimpleInsertEntity(String defineRoleId, String definePermissionId, UserAccount loginUser) {
        RolePermission rolePermission = new RolePermission();
        Date now = new Date();
        rolePermission.setFid(MyUUIDUtil.renderSimpleUuid());
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
