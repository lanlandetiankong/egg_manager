package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmRoleMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class EmRoleMenuPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static EmRoleMenuEntity generateSimpleInsertEntity(String defineRoleId, String defineMenuId, EmUserAccountEntity loginUser) {
        return EmRoleMenuPojoInitialize.generateSimpleInsertEntity(defineRoleId, defineMenuId, BaseStateEnum.ENABLED.getValue(), loginUser);
    }

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static EmRoleMenuEntity generateSimpleInsertEntity(String defineRoleId, String defineMenuId, Short stateVal, EmUserAccountEntity loginUser) {
        EmRoleMenuEntity emRoleMenuEntity = new EmRoleMenuEntity();
        Date now = new Date();
        emRoleMenuEntity.setDefineRoleId(defineRoleId);
        emRoleMenuEntity.setDefineMenuId(defineMenuId);
        emRoleMenuEntity.setType(1);
        emRoleMenuEntity.setState(stateVal);
        emRoleMenuEntity.setCreateTime(now);
        emRoleMenuEntity.setUpdateTime(now);
        if (loginUser != null) {
            emRoleMenuEntity.setCreateUserId(loginUser.getFid());
            emRoleMenuEntity.setLastModifyerId(loginUser.getFid());
        }
        return emRoleMenuEntity;
    }
}
