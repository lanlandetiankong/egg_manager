package com.egg.manager.persistence.pojo.mysql.initialize.user;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;

import java.util.Date;

/**
 * @author zhoucj
 * @version V1.0
 * @description: [用户职务] pojo static生成
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
    public static UserJob generateSimpleInsertEntity(String userAccountId, String defineJobId, UserAccount loginUser) {
        UserJob userJob = new UserJob();
        Date now = new Date();
        userJob.setFid(MyUUIDUtil.renderSimpleUuid());
        userJob.setUserAccountId(userAccountId);
        userJob.setDefineJobId(defineJobId);
        userJob.setState(BaseStateEnum.ENABLED.getValue());
        userJob.setCreateTime(now);
        userJob.setUpdateTime(now);
        if (loginUser != null) {
            userJob.setCreateUserId(loginUser.getFid());
            userJob.setLastModifyerId(loginUser.getFid());
        }
        return userJob;
    }
}
