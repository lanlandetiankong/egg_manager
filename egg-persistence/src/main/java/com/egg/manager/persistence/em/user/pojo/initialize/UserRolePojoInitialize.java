package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserRoleEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class UserRolePojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineRoleId
     * @param loginUser     当前登录用户
     * @return
     */
    public static UserRoleEntity generateSimpleInsertEntity(String userAccountId, String defineRoleId, UserAccountEntity loginUser) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        Date now = new Date();
        userRoleEntity.setUserAccountId(userAccountId);
        userRoleEntity.setDefineRoleId(defineRoleId);
        userRoleEntity.setType(1);
        userRoleEntity.setState(BaseStateEnum.ENABLED.getValue());
        userRoleEntity.setCreateTime(now);
        userRoleEntity.setUpdateTime(now);
        if (loginUser != null) {
            userRoleEntity.setCreateUserId(loginUser.getFid());
            userRoleEntity.setLastModifyerId(loginUser.getFid());
        }
        return userRoleEntity;
    }
}
