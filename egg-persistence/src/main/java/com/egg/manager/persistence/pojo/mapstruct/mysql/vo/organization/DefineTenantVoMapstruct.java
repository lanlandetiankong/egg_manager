package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.organization;


import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.dto.mysql.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DefineTenantVoMapstruct extends MyBaseMysqlVoMapstruct<DefineTenant, DefineTenantVo, DefineTenantDto> {

    DefineTenantVoMapstruct INSTANCE = Mappers.getMapper(DefineTenantVoMapstruct.class);


}
