package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.module;


import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.module.DefineModuleConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;
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
        uses = {DefineModuleConversion.class}
)
public interface DefineModuleMapstruct extends MyBaseMysqlMapstruct<DefineModule, DefineModuleVo, DefineModuleDto> {
    DefineModuleMapstruct INSTANCE = Mappers.getMapper(DefineModuleMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    DefineModule transferVoToEntity(DefineModuleVo vo);

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
    DefineModuleVo transferEntityToVo(DefineModule entity);

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
