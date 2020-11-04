package com.egg.manager.persistence.em.user.pojo.mapstruct.imap.session;

import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring")
public interface UserAccountTokenMapstruct {
    UserAccountTokenMapstruct INSTANCE = Mappers.getMapper(UserAccountTokenMapstruct.class);

    /**
     * 复制-UserAccount->UserAccountToken
     * @param userAccount
     * @return
     */
    @Mapping(source = "fid", target = "userAccountId")
    UserAccountToken userAccount_CopyTo_UserAccountToken(UserAccount userAccount);


}
