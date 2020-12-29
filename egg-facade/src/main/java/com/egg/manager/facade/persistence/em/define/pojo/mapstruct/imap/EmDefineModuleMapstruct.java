package com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineModuleEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineModuleDto;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.conversion.EmDefineModuleConversion;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineModuleVo;
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
        uses = {EmDefineModuleConversion.class}
)
public interface EmDefineModuleMapstruct extends MyBaseMysqlMapstruct<EmDefineModuleEntity, EmDefineModuleVo, EmDefineModuleDto> {
    EmDefineModuleMapstruct INSTANCE = Mappers.getMapper(EmDefineModuleMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmDefineModuleEntity transferVoToEntity(EmDefineModuleVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "typeStr", expression = "java(handleDefineModuleTypeGetLabel(entity.getType()))"),
            @Mapping(target = "iconVal", source = "icon"),
            @Mapping(target = "styleVal", source = "style"),
            @Mapping(target = "typeVal", source = "type"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmDefineModuleVo transferEntityToVo(EmDefineModuleEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "typeStr", expression = "java(handleDefineModuleTypeGetLabel(dto.getType()))"),
            @Mapping(target = "iconVal", source = "icon"),
            @Mapping(target = "styleVal", source = "style"),
            @Mapping(target = "typeVal", source = "type"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmDefineModuleVo transferDtoToVo(EmDefineModuleDto dto);
}
