package com.egg.manager.persistence.em.user.pojo.dto.login;

import com.egg.manager.persistence.exchange.bean.webvo.MyBaseWebVo;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.login.LoginAccountMapstruct;
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
public class LoginAccountVo extends MyBaseWebVo {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;


    /**
     * 转化为dto
     * @param loginAccountVo
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static LoginAccountDTO transferToLoginAccountDTO(LoginAccountVo loginAccountVo) throws InvocationTargetException, IllegalAccessException {
        LoginAccountDTO dto = LoginAccountMapstruct.INSTANCE.translateLoginAccountVoToDto(loginAccountVo);
        return dto;
    }

}