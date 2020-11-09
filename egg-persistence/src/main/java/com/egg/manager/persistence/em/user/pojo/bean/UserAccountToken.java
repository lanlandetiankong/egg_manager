package com.egg.manager.persistence.em.user.pojo.bean;

import com.egg.manager.persistence.commons.util.str.MyUUIDUtil;
import com.egg.manager.persistence.exchange.bean.webvo.MyBaseWebVo;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.session.UserAccountTokenMapstruct;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
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
    private Long userAccountId;
    /**
     * 所属租户_id
     */
    private Long userBelongTenantId;
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


    public static UserAccountToken gainByUserAccount(UserAccountEntity userAccountEntity) throws InvocationTargetException, IllegalAccessException {
        UserAccountToken accountToken = UserAccountTokenMapstruct.INSTANCE.userAccount_CopyTo_UserAccountToken(userAccountEntity);
        String token = MyUUIDUtil.renderSimpleUuid();
        accountToken.setToken(token);
        return accountToken;
    }

}
