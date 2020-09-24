package com.egg.manager.persistence.pojo.mysql.initialize.user;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;

import java.util.Date;

/**
 * @Description: [用户职务] pojo static生成
 * @ClassName: UserAccountPojoInitialize
 * @Author: zhoucj
 * @Date: 2020/9/22 11:57
 */
public class UserRolePojoInitialize {

    /**
     * 返回一个通用的 entity实例
     *
     * @param userAccountId
     * @param defineRoleId
     * @param loginUser     当前登录用户
     * @return
     */
    public static UserRole generateSimpleInsertEntity(String userAccountId, String defineRoleId, UserAccount loginUser) {
        UserRole userRole = new UserRole();
        Date now = new Date();
        userRole.setFid(MyUUIDUtil.renderSimpleUUID());
        userRole.setUserAccountId(userAccountId);
        userRole.setDefineRoleId(defineRoleId);
        userRole.setType(1);
        userRole.setState(BaseStateEnum.ENABLED.getValue());
        userRole.setCreateTime(now);
        userRole.setUpdateTime(now);
        if (loginUser != null) {
            userRole.setCreateUserId(loginUser.getFid());
            userRole.setLastModifyerId(loginUser.getFid());
        }
        return userRole;
    }
}