package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.define.DefinePermissionConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefinePermissionConversion.class}
)
public interface DefinePermissionMapstruct extends MyBaseMysqlMapstruct<DefinePermission, DefinePermissionVo, DefinePermissionDto> {
    DefinePermissionMapstruct INSTANCE = Mappers.getMapper(DefinePermissionMapstruct.class);

    @Mappings({
            @Mapping(target = "ensure", expression = "java(handleSwitchStateGetShort(vo.getEnsure()))")
    })
    DefinePermission transferVoToEntity(DefinePermissionVo vo);

    @Mappings({
            @Mapping(target = "ensureStr", expression = "java(handleSwitchStateGetName(entity.getEnsure()))"),
            @Mapping(target = "typeStr", expression = "java(handleDefinePermissionTypeGetLabel(entity.getType()))"),
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
