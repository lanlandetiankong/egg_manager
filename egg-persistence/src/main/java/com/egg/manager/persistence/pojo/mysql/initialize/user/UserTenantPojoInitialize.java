package com.egg.manager.persistence.pojo.mysql.initialize.user;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;

import java.util.Date;

/**
 * @author zhoucj
 * @description:
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
    public static UserTenant generateSimpleInsertEntity(String userAccountId, String defineTenantId, UserAccount loginUser) {
        UserTenant userTenant = new UserTenant();
        Date now = new Date();
        userTenant.setFid(MyUUIDUtil.renderSimpleUuid());
        userTenant.setUserAccountId(userAccountId);
        userTenant.setDefineTenantId(defineTenantId);
        userTenant.setIsManager(SwitchStateEnum.Close.getValue());
        userTenant.setType(1);
        userTenant.setState(BaseStateEnum.ENABLED.getValue());
        userTenant.setCreateTime(now);
        userTenant.setUpdateTime(now);
        if (loginUser != null) {
            userTenant.setCreateUserId(loginUser.getFid());
            userTenant.setLastModifyerId(loginUser.getFid());
        }
        return userTenant;
    }

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineTenantId
     * @param loginUser      当前登录用户
     * @return
     */
    public static UserTenant generateInsertIsManagerEntity(String userAccountId, String defineTenantId, UserAccount loginUser) {
        UserTenant userTenant = new UserTenant();
        Date now = new Date();
        userTenant.setFid(MyUUIDUtil.renderSimpleUuid());
        userTenant.setUserAccountId(userAccountId);
        userTenant.setDefineTenantId(defineTenantId);
        userTenant.setIsManager(SwitchStateEnum.Open.getValue());
        userTenant.setType(1);
        userTenant.setState(BaseStateEnum.ENABLED.getValue());
        userTenant.setCreateTime(now);
        userTenant.setUpdateTime(now);
        if (loginUser != null) {
            userTenant.setCreateUserId(loginUser.getFid());
            userTenant.setLastModifyerId(loginUser.getFid());
        }
        return userTenant;
    }
}
