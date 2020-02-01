package com.egg.manager.vo.session;

import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.entity.user.UserAccount;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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



    public static UserAccountToken gainByUserAccount(UserAccount userAccount) throws InvocationTargetException, IllegalAccessException {
        UserAccountToken accountToken = new UserAccountToken() ;
        BeanUtils.copyProperties(accountToken,userAccount);

        accountToken.setUserAccountId(userAccount.getFid());
        String token = MyUUIDUtil.renderSimpleUUID() ;
        accountToken.setToken(token);
        return accountToken ;
    }
}
