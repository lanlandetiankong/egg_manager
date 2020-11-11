package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.define.db.mysql.entity.DefineModuleEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefineModuleDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.DefineModuleConversion;
import com.egg.manager.persistence.em.define.pojo.vo.DefineModuleVo;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
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
        uses = {DefineModuleConversion.class}
)
public interface DefineModuleMapstruct extends MyBaseMysqlMapstruct<DefineModuleEntity, DefineModuleVo, DefineModuleDto> {
    DefineModuleMapstruct INSTANCE = Mappers.getMapper(DefineModuleMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    DefineModuleEntity transferVoToEntity(DefineModuleVo vo);

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
    DefineModuleVo transferEntityToVo(DefineModuleEntity entity);

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
    DefineModuleVo transferDtoToVo(DefineModuleDto dto);
}
