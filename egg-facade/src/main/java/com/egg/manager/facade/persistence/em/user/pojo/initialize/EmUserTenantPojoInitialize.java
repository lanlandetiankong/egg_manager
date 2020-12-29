package com.egg.manager.facade.persistence.em.user.pojo.initialize;

import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserTenantEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class EmUserTenantPojoInitialize {


    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineTenantId
     * @param loginUser      当前登录用户
     * @return
     */
    public static EmUserTenantEntity generateSimpleInsertEntity(String userAccountId, String defineTenantId, EmUserAccountEntity loginUser) {
        EmUserTenantEntity emUserTenantEntity = new EmUserTenantEntity();
        Date now = new Date();
        emUserTenantEntity.setUserAccountId(userAccountId);
        emUserTenantEntity.setDefineTenantId(defineTenantId);
        emUserTenantEntity.setIsManager(SwitchStateEnum.Close.getValue());
        emUserTenantEntity.setType(1);
        emUserTenantEntity.setState(BaseStateEnum.ENABLED.getValue());
        emUserTenantEntity.setCreateTime(now);
        emUserTenantEntity.setUpdateTime(now);
        if (loginUser != null) {
            emUserTenantEntity.setCreateUserId(loginUser.getFid());
            emUserTenantEntity.setLastModifyerId(loginUser.getFid());
        }
        return emUserTenantEntity;
    }

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineTenantId
     * @param loginUser      当前登录用户
     * @return
     */
    public static EmUserTenantEntity generateInsertIsManagerEntity(String userAccountId, String defineTenantId, EmUserAccountEntity loginUser) {
        EmUserTenantEntity emUserTenantEntity = new EmUserTenantEntity();
        Date now = new Date();
        emUserTenantEntity.setUserAccountId(userAccountId);
        emUserTenantEntity.setDefineTenantId(defineTenantId);
        emUserTenantEntity.setIsManager(SwitchStateEnum.Open.getValue());
        emUserTenantEntity.setType(1);
        emUserTenantEntity.setState(BaseStateEnum.ENABLED.getValue());
        emUserTenantEntity.setCreateTime(now);
        emUserTenantEntity.setUpdateTime(now);
        if (loginUser != null) {
            emUserTenantEntity.setCreateUserId(loginUser.getFid());
            emUserTenantEntity.setLastModifyerId(loginUser.getFid());
        }
        return emUserTenantEntity;
    }
}
