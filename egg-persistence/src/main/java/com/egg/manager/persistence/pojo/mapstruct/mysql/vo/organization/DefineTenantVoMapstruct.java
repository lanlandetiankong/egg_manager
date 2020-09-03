package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.organization;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
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
public interface DefineTenantVoMapstruct extends MyBaseMysqlVoMapstruct<DefineTenant, DefineTenantVo, DefineTenantDto> {

    DefineTenantVoMapstruct INSTANCE = Mappers.getMapper(DefineTenantVoMapstruct.class);

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
