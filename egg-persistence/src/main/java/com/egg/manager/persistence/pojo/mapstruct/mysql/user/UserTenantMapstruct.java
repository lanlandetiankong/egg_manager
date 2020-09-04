package com.egg.manager.persistence.pojo.mapstruct.mysql.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserTenantTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserTenantTransfer.class}
)
public interface UserTenantMapstruct extends MyBaseMysqlMapstruct<UserTenant, UserTenantVo, UserTenantDto> {
    UserTenantMapstruct INSTANCE = Mappers.getMapper(UserTenantMapstruct.class);

    @Mappings({})
    UserTenant transferVoToEntity(UserTenantVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserTenantVo transferEntityToVo(UserTenant entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserTenantVo transferDtoToVo(UserTenantDto dto);
}
