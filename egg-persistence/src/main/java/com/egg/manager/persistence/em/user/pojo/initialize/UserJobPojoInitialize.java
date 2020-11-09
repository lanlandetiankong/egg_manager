package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserJobEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description [用户职务] pojo static生成
 * @date 2020/10/20
 */
public class UserJobPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineJobId
     * @param loginUser     当前登录用户
     * @return
     */
    public static UserJobEntity generateSimpleInsertEntity(Long userAccountId, Long defineJobId, UserAccountEntity loginUser) {
        UserJobEntity userJobEntity = new UserJobEntity();
        Date now = new Date();
        //userJob.setFid(MyUUIDUtil.renderSimpleUuid());
        userJobEntity.setUserAccountId(userAccountId);
        userJobEntity.setDefineJobId(defineJobId);
        userJobEntity.setState(BaseStateEnum.ENABLED.getValue());
        userJobEntity.setCreateTime(now);
        userJobEntity.setUpdateTime(now);
        if (loginUser != null) {
            userJobEntity.setCreateUserId(loginUser.getFid());
            userJobEntity.setLastModifyerId(loginUser.getFid());
        }
        return userJobEntity;
    }
}
