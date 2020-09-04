package com.egg.manager.persistence.pojo.common.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 14:21
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAccountDTO implements Serializable {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
}
