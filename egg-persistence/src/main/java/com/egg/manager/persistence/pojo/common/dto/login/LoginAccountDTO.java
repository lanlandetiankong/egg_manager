package com.egg.manager.persistence.pojo.common.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
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
