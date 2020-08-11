package com.egg.manager.persistence.webvo.session;

import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.webvo.mapstruct.session.UserAccountTokenMapstruct;
import lombok.*;

import java.lang.reflect.InvocationTargetException;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 15:45
 * \* Description:
 * \
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserAccountToken {
    private String account;
    private String userAccountId ;
    private String token ;
    private String userName ;
    private String nickName ;
    private String avatarUrl ;
    private String authorization ;



    public static UserAccountToken gainByUserAccount(UserAccount userAccount) throws InvocationTargetException, IllegalAccessException {
        UserAccountToken accountToken = UserAccountTokenMapstruct.INSTANCE.userAccount_CopyTo_UserAccountToken(userAccount);
        String token = MyUUIDUtil.renderSimpleUUID() ;
        accountToken.setToken(token);
        return accountToken ;
    }

}
