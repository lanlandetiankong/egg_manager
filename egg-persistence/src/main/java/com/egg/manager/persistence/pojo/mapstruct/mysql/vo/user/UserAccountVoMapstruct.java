package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserAccountVoMapstruct extends MyBaseMysqlVoMapstruct<UserAccount,UserAccountVo, UserAccountDto> {
    UserAccountVoMapstruct INSTANCE = Mappers.getMapper(UserAccountVoMapstruct.class);


}
