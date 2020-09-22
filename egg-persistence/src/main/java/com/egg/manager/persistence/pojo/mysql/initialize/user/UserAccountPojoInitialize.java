package com.egg.manager.persistence.pojo.mysql.initialize.user;

import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

/**
 * @Description:
 * @ClassName: UserAccountPojoInitialize
 * @Author: zhoucj
 * @Date: 2020/9/22 11:57
 */
public class UserAccountPojoInitialize {

    /**
     * 游客
     * @return
     */
    public static UserAccount dealGetVisitor() {
        return UserAccount.builder().fid("Visitor").userType(UserAccountBaseTypeEnum.Visitor.getValue()).build();
    }
}
