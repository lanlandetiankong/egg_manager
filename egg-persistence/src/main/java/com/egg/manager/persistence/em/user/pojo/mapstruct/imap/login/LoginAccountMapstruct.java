package com.egg.manager.persistence.em.user.pojo.mapstruct.imap.login;

import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
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
