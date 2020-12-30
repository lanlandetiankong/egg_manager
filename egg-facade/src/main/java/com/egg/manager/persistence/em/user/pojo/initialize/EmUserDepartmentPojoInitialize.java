package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserDepartmentEntity;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class EmUserDepartmentPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineDepartmentId
     * @param loginUser          当前登录用户
     * @return
     */
    public static EmUserDepartmentEntity generateSimpleInsertEntity(String userAccountId, String defineDepartmentId, EmUserAccountEntity loginUser) {
        EmUserDepartmentEntity emUserDepartmentEntity = new EmUserDepartmentEntity();
        Date now = new Date();
        emUserDepartmentEntity.setUserAccountId(userAccountId);
        emUserDepartmentEntity.setDefineDepartmentId(defineDepartmentId);
        emUserDepartmentEntity.setIsManager(SwitchStateEnum.Close.getValue());
        emUserDepartmentEntity.setType(1);
        emUserDepartmentEntity.setState(BaseStateEnum.ENABLED.getValue());
        emUserDepartmentEntity.setCreateTime(now);
        emUserDepartmentEntity.setUpdateTime(now);
        if (loginUser != null) {
            emUserDepartmentEntity.setCreateUserId(loginUser.getFid());
            emUserDepartmentEntity.setLastModifyerId(loginUser.getFid());
        }
        return emUserDepartmentEntity;
    }
}
