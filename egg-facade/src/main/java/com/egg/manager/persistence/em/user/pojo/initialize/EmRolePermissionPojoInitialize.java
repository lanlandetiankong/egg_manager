package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmRolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class EmRolePermissionPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param definePermissionId
     * @param loginUser
     * @return
     */
    public static EmRolePermissionEntity generateSimpleInsertEntity(String defineRoleId, String definePermissionId, EmUserAccountEntity loginUser) {
        EmRolePermissionEntity emRolePermissionEntity = new EmRolePermissionEntity();
        Date now = new Date();
        emRolePermissionEntity.setDefineRoleId(defineRoleId);
        emRolePermissionEntity.setDefinePermissionId(definePermissionId);
        emRolePermissionEntity.setType(1);
        emRolePermissionEntity.setState(BaseStateEnum.ENABLED.getValue());
        emRolePermissionEntity.setCreateTime(now);
        emRolePermissionEntity.setUpdateTime(now);
        if (loginUser != null) {
            emRolePermissionEntity.setCreateUserId(loginUser.getFid());
            emRolePermissionEntity.setLastModifyerId(loginUser.getFid());
        }
        emRolePermissionEntity.setRemark(null);
        return emRolePermissionEntity;
    }
}
