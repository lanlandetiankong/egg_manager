package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserGroupDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserGroupVoMapstruct extends MyBaseMysqlVoMapstruct<UserGroup,UserGroupVo, UserGroupDto> {
    UserGroupVoMapstruct INSTANCE = Mappers.getMapper(UserGroupVoMapstruct.class);


}
