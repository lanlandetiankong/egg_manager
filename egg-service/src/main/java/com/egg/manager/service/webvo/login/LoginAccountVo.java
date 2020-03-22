package com.egg.manager.service.webvo.login;

import com.egg.manager.persistence.dto.login.LoginAccountDTO;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 13:41
 * \* Description:
 * \
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginAccountVo {
    private String account ;
    private String password ;


    /**
     * 转化为dto
     * @param loginAccountVo
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static LoginAccountDTO transferToLoginAccountDTO(LoginAccountVo loginAccountVo) throws InvocationTargetException, IllegalAccessException {
        LoginAccountDTO dto = new LoginAccountDTO() ;
        BeanUtils.copyProperties(dto,loginAccountVo) ;
        return dto ;
    }

}
