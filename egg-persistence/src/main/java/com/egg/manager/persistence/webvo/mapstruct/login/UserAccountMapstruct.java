package com.egg.manager.persistence.webvo.mapstruct.login;

import com.egg.manager.persistence.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.webvo.login.LoginAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description:
 * @ClassName: UserAccountMapstruct
 * @Author: zhoucj
 * @Date: 2020/8/7 9:56
 */
@Mapper(componentModel="spring")
public interface UserAccountMapstruct {
    UserAccountMapstruct INSTANCE = Mappers.getMapper(UserAccountMapstruct.class);

    /**
     *
     * @param loginAccountVo
     * @return
     */
    LoginAccountDTO loginAccountVo_CopyTo_LoginAccountDTO(LoginAccountVo loginAccountVo);


}
