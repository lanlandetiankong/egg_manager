package com.egg.manager.persistence.pojo.mapstruct.mysql.organization;


import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.dto.mysql.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineTenantTransfer.class}
)
public interface DefineTenantMapstruct extends MyBaseMysqlMapstruct<DefineTenant, DefineTenantVo, DefineTenantDto> {

    DefineTenantMapstruct INSTANCE = Mappers.getMapper(DefineTenantMapstruct.class);

    @Mappings({})
    DefineTenant transferVoToEntity(DefineTenantVo vo);

    @Mappings({
            @Mapping(target = "typeStr", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineTenantVo transferEntityToVo(DefineTenant entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineTenantVo transferDtoToVo(DefineTenantDto dto);
}
