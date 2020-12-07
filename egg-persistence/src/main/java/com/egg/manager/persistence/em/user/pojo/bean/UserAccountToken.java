package com.egg.manager.persistence.em.user.pojo.bean;

import com.egg.manager.persistence.commons.util.data.str.MyUUIDUtil;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.session.UserAccountTokenMapstruct;
import com.egg.manager.persistence.exchange.bean.webvo.MyBaseWebVo;
import lombok.*;

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
@EqualsAndHashCode(callSuper = true)
public class UserAccountToken extends MyBaseWebVo {
    private static final long serialVersionUID = 3474254841894818833L;
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


    public static UserAccountToken gainByUserAccount(EmUserAccountEntity emUserAccountEntity) throws InvocationTargetException, IllegalAccessException {
        UserAccountToken accountToken = UserAccountTokenMapstruct.INSTANCE.translateEntityToToken(emUserAccountEntity);
        String token = MyUUIDUtil.renderSimpleUuid();
        accountToken.setToken(token);
        return accountToken;
    }

}
