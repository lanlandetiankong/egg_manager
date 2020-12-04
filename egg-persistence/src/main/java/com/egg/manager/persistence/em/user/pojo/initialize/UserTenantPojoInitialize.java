package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenantEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class UserTenantPojoInitialize {


    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineTenantId
     * @param loginUser      当前登录用户
     * @return
     */
    public static UserTenantEntity generateSimpleInsertEntity(String userAccountId, String defineTenantId, UserAccountEntity loginUser) {
        UserTenantEntity userTenantEntity = new UserTenantEntity();
        Date now = new Date();
        userTenantEntity.setUserAccountId(userAccountId);
        userTenantEntity.setDefineTenantId(defineTenantId);
        userTenantEntity.setIsManager(SwitchStateEnum.Close.getValue());
        userTenantEntity.setType(1);
        userTenantEntity.setState(BaseStateEnum.ENABLED.getValue());
        userTenantEntity.setCreateTime(now);
        userTenantEntity.setUpdateTime(now);
        if (loginUser != null) {
            userTenantEntity.setCreateUserId(loginUser.getFid());
            userTenantEntity.setLastModifyerId(loginUser.getFid());
        }
        return userTenantEntity;
    }

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineTenantId
     * @param loginUser      当前登录用户
     * @return
     */
    public static UserTenantEntity generateInsertIsManagerEntity(String userAccountId, String defineTenantId, UserAccountEntity loginUser) {
        UserTenantEntity userTenantEntity = new UserTenantEntity();
        Date now = new Date();
        userTenantEntity.setUserAccountId(userAccountId);
        userTenantEntity.setDefineTenantId(defineTenantId);
        userTenantEntity.setIsManager(SwitchStateEnum.Open.getValue());
        userTenantEntity.setType(1);
        userTenantEntity.setState(BaseStateEnum.ENABLED.getValue());
        userTenantEntity.setCreateTime(now);
        userTenantEntity.setUpdateTime(now);
        if (loginUser != null) {
            userTenantEntity.setCreateUserId(loginUser.getFid());
            userTenantEntity.setLastModifyerId(loginUser.getFid());
        }
        return userTenantEntity;
    }
}
