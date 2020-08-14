package com.egg.manager.persistence.webvo.login;

import com.egg.manager.persistence.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.webvo.MyBaseWebVo;
import com.egg.manager.persistence.webvo.mapstruct.login.UserAccountMapstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 13:41
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAccountVo extends MyBaseWebVo {
    private String account;
    private String password;


    /**
     * 转化为dto
     *
     * @param loginAccountVo
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static LoginAccountDTO transferToLoginAccountDTO(LoginAccountVo loginAccountVo) throws InvocationTargetException, IllegalAccessException {
        LoginAccountDTO dto = UserAccountMapstruct.INSTANCE.loginAccountVo_CopyTo_LoginAccountDTO(loginAccountVo);
        return dto;
    }

}
