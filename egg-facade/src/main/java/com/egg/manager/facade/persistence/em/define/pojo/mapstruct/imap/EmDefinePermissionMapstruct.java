package com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefinePermissionEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefinePermissionDto;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.conversion.EmDefinePermissionConversion;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefinePermissionVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmDefinePermissionConversion.class}
)
public interface EmDefinePermissionMapstruct extends MyBaseMysqlMapstruct<EmDefinePermissionEntity, EmDefinePermissionVo, EmDefinePermissionDto> {
    EmDefinePermissionMapstruct INSTANCE = Mappers.getMapper(EmDefinePermissionMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(target = "ensure", expression = "java(handleSwitchStateGetShort(vo.getEnsure()))")
    })
    EmDefinePermissionEntity transferVoToEntity(EmDefinePermissionVo vo);

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
    EmDefinePermissionVo transferEntityToVo(EmDefinePermissionEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "ensureStr", expression = "java(handleSwitchStateGetName(dto.getEnsure()))"),
            @Mapping(target = "typeStr", expression = "java(handleDefinePermissionTypeGetLabel(dto.getType()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmDefinePermissionVo transferDtoToVo(EmDefinePermissionDto dto);
}
