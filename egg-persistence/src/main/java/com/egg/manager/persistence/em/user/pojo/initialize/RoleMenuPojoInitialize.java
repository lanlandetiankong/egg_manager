package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.RoleMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class RoleMenuPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static RoleMenuEntity generateSimpleInsertEntity(String defineRoleId, String defineMenuId, UserAccountEntity loginUser) {
        return RoleMenuPojoInitialize.generateSimpleInsertEntity(defineRoleId, defineMenuId, BaseStateEnum.ENABLED.getValue(), loginUser);
    }

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static RoleMenuEntity generateSimpleInsertEntity(String defineRoleId, String defineMenuId, Short stateVal, UserAccountEntity loginUser) {
        RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
        Date now = new Date();
        roleMenuEntity.setDefineRoleId(defineRoleId);
        roleMenuEntity.setDefineMenuId(defineMenuId);
        roleMenuEntity.setType(1);
        roleMenuEntity.setState(stateVal);
        roleMenuEntity.setCreateTime(now);
        roleMenuEntity.setUpdateTime(now);
        if (loginUser != null) {
            roleMenuEntity.setCreateUserId(loginUser.getFid());
            roleMenuEntity.setLastModifyerId(loginUser.getFid());
        }
        return roleMenuEntity;
    }
}
