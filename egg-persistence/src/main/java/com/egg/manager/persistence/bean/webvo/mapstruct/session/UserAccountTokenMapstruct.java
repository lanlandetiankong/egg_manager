package com.egg.manager.persistence.bean.webvo.mapstruct.session;

import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @Description:
 * @ClassName: UserAccountDtoMapstruct
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
