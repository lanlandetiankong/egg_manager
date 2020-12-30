package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserRoleEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class EmUserRolePojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineRoleId
     * @param loginUser     当前登录用户
     * @return
     */
    public static EmUserRoleEntity generateSimpleInsertEntity(String userAccountId, String defineRoleId, EmUserAccountEntity loginUser) {
        EmUserRoleEntity emUserRoleEntity = new EmUserRoleEntity();
        Date now = new Date();
        emUserRoleEntity.setUserAccountId(userAccountId);
        emUserRoleEntity.setDefineRoleId(defineRoleId);
        emUserRoleEntity.setType(1);
        emUserRoleEntity.setState(BaseStateEnum.ENABLED.getValue());
        emUserRoleEntity.setCreateTime(now);
        emUserRoleEntity.setUpdateTime(now);
        if (loginUser != null) {
            emUserRoleEntity.setCreateUserId(loginUser.getFid());
            emUserRoleEntity.setLastModifyerId(loginUser.getFid());
        }
        return emUserRoleEntity;
    }
}
