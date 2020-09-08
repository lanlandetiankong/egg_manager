package com.egg.manager.persistence.bean.webvo.mapstruct.login;

import com.egg.manager.persistence.bean.webvo.login.LoginAccountVo;
import com.egg.manager.persistence.pojo.common.dto.login.LoginAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description:
 * @ClassName: UserAccountDtoMapstruct
 * @Author: zhoucj
 * @Date: 2020/8/7 9:56
 */
@Mapper(componentModel = "spring")
public interface LoginAccountMapstruct {
    LoginAccountMapstruct INSTANCE = Mappers.getMapper(LoginAccountMapstruct.class);

    /**
     * @param loginAccountVo
     * @return
     */
    LoginAccountDTO translateLoginAccountVoToDto(LoginAccountVo loginAccountVo);


}
