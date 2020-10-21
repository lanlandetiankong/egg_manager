package com.egg.manager.persistence.bean.webvo.mapstruct.login;

import com.egg.manager.persistence.bean.webvo.login.LoginAccountVo;
import com.egg.manager.persistence.pojo.common.dto.login.LoginAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring")
public interface LoginAccountMapstruct {
    LoginAccountMapstruct INSTANCE = Mappers.getMapper(LoginAccountMapstruct.class);

    /**
     * vo转dto
     * @param loginAccountVo
     * @return
     */
    LoginAccountDTO translateLoginAccountVoToDto(LoginAccountVo loginAccountVo);


}
