package com.egg.manager.persistence.webvo.mapstruct.session;

import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.webvo.session.UserAccountToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @Description:
 * @ClassName: UserAccountMapstruct
 * @Author: zhoucj
 * @Date: 2020/8/7 9:56
 */
@Mapper(componentModel = "spring")
public interface UserAccountTokenMapstruct {
    UserAccountTokenMapstruct INSTANCE = Mappers.getMapper(UserAccountTokenMapstruct.class);

    /**
     * 复制MyEmailMsgO 到 SimpleMailMessage
     *
     * @param userAccount
     * @return
     */
    @Mapping(source = "fid", target = "userAccountId")
    UserAccountToken userAccount_CopyTo_UserAccountToken(UserAccount userAccount);


}
