package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefinePermissionTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefinePermissionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefinePermissionTransfer.class}
)
public interface DefinePermissionVoMapstruct extends MyBaseMysqlVoMapstruct<DefinePermission, DefinePermissionVo, DefinePermissionDto> {
    DefinePermissionVoMapstruct INSTANCE = Mappers.getMapper(DefinePermissionVoMapstruct.class);

    @Mappings({
            @Mapping(target = "ensure",expression = "java(handleSwitchStateGetShort(vo.getEnsure()))")
    })
    DefinePermission transferVoToEntity(DefinePermissionVo vo);
    @Mappings({
            @Mapping(target = "ensureStr",expression = "java(handleSwitchStateGetName(entity.getEnsure()))"),
            @Mapping(target = "typeStr",source = "type",qualifiedByName = "doGetLabelOfDefinePermissionTypeEnum"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefinePermissionVo transferEntityToVo(DefinePermission entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefinePermissionVo transferDtoToVo(DefinePermissionDto dto);
}
