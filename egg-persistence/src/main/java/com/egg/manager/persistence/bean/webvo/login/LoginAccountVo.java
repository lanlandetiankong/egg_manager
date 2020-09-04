package com.egg.manager.persistence.bean.webvo.login;

import com.egg.manager.persistence.pojo.common.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.bean.webvo.MyBaseWebVo;
import com.egg.manager.persistence.bean.webvo.mapstruct.login.UserAccountMapstruct;
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
