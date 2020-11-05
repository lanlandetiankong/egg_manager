package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;

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
