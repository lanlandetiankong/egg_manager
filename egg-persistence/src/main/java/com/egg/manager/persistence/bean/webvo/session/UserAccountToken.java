package com.egg.manager.persistence.bean.webvo.session;

import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.webvo.MyBaseWebVo;
import com.egg.manager.persistence.bean.webvo.mapstruct.session.UserAccountTokenMapstruct;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 15:45
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountToken extends MyBaseWebVo {
    /**
     * 账号
     */
    private String account;
    /**
     * 账号_id
     */
    private String userAccountId;
    /**
     * 所属租户_id
     */
    private String userBelongTenantId;
    /**
     * token
     */
    private String token;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 认真信息
     */
    private String authorization;


    public static UserAccountToken gainByUserAccount(UserAccount userAccount) throws InvocationTargetException, IllegalAccessException {
        UserAccountToken accountToken = UserAccountTokenMapstruct.INSTANCE.userAccount_CopyTo_UserAccountToken(userAccount);
        String token = MyUUIDUtil.renderSimpleUuid();
        accountToken.setToken(token);
        return accountToken;
    }

}
