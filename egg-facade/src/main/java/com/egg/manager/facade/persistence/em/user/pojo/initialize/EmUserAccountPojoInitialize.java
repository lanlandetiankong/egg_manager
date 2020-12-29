package com.egg.manager.facade.persistence.em.user.pojo.initialize;

import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class EmUserAccountPojoInitialize {

    /**
     * 游客
     * @return
     */
    public static EmUserAccountEntity dealGetVisitor() {
        return EmUserAccountEntity.builder()
                //.fid("Visitor")
                .userType(UserAccountBaseTypeEnum.Visitor.getValue()).build();
    }
}
