package com.egg.manager.persistence.pojo.mysql.initialize.user;

import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public class UserAccountPojoInitialize {

    /**
     * 游客
     * @return
     */
    public static UserAccount dealGetVisitor() {
        return UserAccount.builder()
                //.fid("Visitor")
                .userType(UserAccountBaseTypeEnum.Visitor.getValue()).build();
    }
}
