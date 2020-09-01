package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserTenantVoMapstruct extends MyBaseMysqlVoMapstruct<UserTenant, UserTenantVo, UserTenantDto> {
    UserTenantVoMapstruct INSTANCE = Mappers.getMapper(UserTenantVoMapstruct.class);


}
