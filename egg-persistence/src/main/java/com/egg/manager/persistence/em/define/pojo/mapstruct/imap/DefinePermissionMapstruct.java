package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermission;
import com.egg.manager.persistence.em.define.pojo.dto.DefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.DefinePermissionConversion;
import com.egg.manager.persistence.expand.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.DefinePermissionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefinePermissionConversion.class}
)
public interface DefinePermissionMapstruct extends MyBaseMysqlMapstruct<DefinePermission, DefinePermissionVo, DefinePermissionDto> {
    DefinePermissionMapstruct INSTANCE = Mappers.getMapper(DefinePermissionMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(target = "ensure", expression = "java(handleSwitchStateGetShort(vo.getEnsure()))")
    })
    DefinePermission transferVoToEntity(DefinePermissionVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "ensureStr", expression = "java(handleSwitchStateGetName(entity.getEnsure()))"),
            @Mapping(target = "typeStr", expression = "java(handleDefinePermissionTypeGetLabel(entity.getType()))"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefinePermissionVo transferEntityToVo(DefinePermission entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefinePermissionVo transferDtoToVo(DefinePermissionDto dto);
}
