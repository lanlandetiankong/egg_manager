package com.egg.manager.persistence.bean.webvo.mapstruct.login;

import com.egg.manager.persistence.pojo.common.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.bean.webvo.login.LoginAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description:
 * @ClassName: UserAccountDtoMapstruct
 * @Author: zhoucj
 * @Date: 2020/8/7 9:56
 */
@Mapper(componentModel = "spring")
public interface UserAccountMapstruct {
    UserAccountMapstruct INSTANCE = Mappers.getMapper(UserAccountMapstruct.class);

    /**
     * @param loginAccountVo
     * @return
     */
    LoginAccountDTO loginAccountVo_CopyTo_LoginAccountDTO(LoginAccountVo loginAccountVo);


}
