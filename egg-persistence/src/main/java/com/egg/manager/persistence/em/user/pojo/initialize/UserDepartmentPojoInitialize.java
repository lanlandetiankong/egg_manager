package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserDepartmentEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class UserDepartmentPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineDepartmentId
     * @param loginUser          当前登录用户
     * @return
     */
    public static UserDepartmentEntity generateSimpleInsertEntity(Long userAccountId, Long defineDepartmentId, UserAccountEntity loginUser) {
        UserDepartmentEntity userDepartmentEntity = new UserDepartmentEntity();
        Date now = new Date();
        userDepartmentEntity.setUserAccountId(userAccountId);
        userDepartmentEntity.setDefineDepartmentId(defineDepartmentId);
        userDepartmentEntity.setIsManager(SwitchStateEnum.Close.getValue());
        userDepartmentEntity.setType(1);
        userDepartmentEntity.setState(BaseStateEnum.ENABLED.getValue());
        userDepartmentEntity.setCreateTime(now);
        userDepartmentEntity.setUpdateTime(now);
        if (loginUser != null) {
            userDepartmentEntity.setCreateUserId(loginUser.getFid());
            userDepartmentEntity.setLastModifyerId(loginUser.getFid());
        }
        return userDepartmentEntity;
    }
}
