package com.egg.manager.persistence.pojo.mysql.initialize.user;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;

import java.util.Date;

/**
 * @author zhoucj
 * @description:
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
    public static UserDepartment generateSimpleInsertEntity(Long userAccountId, Long defineDepartmentId, UserAccount loginUser) {
        UserDepartment userDepartment = new UserDepartment();
        Date now = new Date();
        //userDepartment.setFid(MyUUIDUtil.renderSimpleUuid());
        userDepartment.setUserAccountId(userAccountId);
        userDepartment.setDefineDepartmentId(defineDepartmentId);
        userDepartment.setIsManager(SwitchStateEnum.Close.getValue());
        userDepartment.setType(1);
        userDepartment.setState(BaseStateEnum.ENABLED.getValue());
        userDepartment.setCreateTime(now);
        userDepartment.setUpdateTime(now);
        if (loginUser != null) {
            userDepartment.setCreateUserId(loginUser.getFid());
            userDepartment.setLastModifyerId(loginUser.getFid());
        }
        return userDepartment;
    }
}
