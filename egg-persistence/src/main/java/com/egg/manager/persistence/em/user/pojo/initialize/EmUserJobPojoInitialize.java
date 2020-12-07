package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserJobEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description [用户职务] pojo static生成
 * @date 2020/10/20
 */
public class EmUserJobPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineJobId
     * @param loginUser     当前登录用户
     * @return
     */
    public static EmUserJobEntity generateSimpleInsertEntity(String userAccountId, String defineJobId, EmUserAccountEntity loginUser) {
        EmUserJobEntity emUserJobEntity = new EmUserJobEntity();
        Date now = new Date();
        emUserJobEntity.setUserAccountId(userAccountId);
        emUserJobEntity.setDefineJobId(defineJobId);
        emUserJobEntity.setState(BaseStateEnum.ENABLED.getValue());
        emUserJobEntity.setCreateTime(now);
        emUserJobEntity.setUpdateTime(now);
        if (loginUser != null) {
            emUserJobEntity.setCreateUserId(loginUser.getFid());
            emUserJobEntity.setLastModifyerId(loginUser.getFid());
        }
        return emUserJobEntity;
    }
}
