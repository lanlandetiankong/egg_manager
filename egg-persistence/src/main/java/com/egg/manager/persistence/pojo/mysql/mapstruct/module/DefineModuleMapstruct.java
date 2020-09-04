package com.egg.manager.persistence.pojo.mysql.mapstruct.module;


import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.module.DefineModuleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineModuleTransfer.class}
)
public interface DefineModuleMapstruct extends MyBaseMysqlMapstruct<DefineModule, DefineModuleVo, DefineModuleDto> {
    DefineModuleMapstruct INSTANCE = Mappers.getMapper(DefineModuleMapstruct.class);

    @Mappings({})
    DefineModule transferVoToEntity(DefineModuleVo vo);

    @Mappings({
            @Mapping(target = "typeStr",source = "type",qualifiedByName = "handleDefineModuleTypeGetLabel"),
            @Mapping(target = "iconVal",source = "icon"),
            @Mapping(target = "styleVal",source = "style"),
            @Mapping(target = "typeVal",source = "type"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineModuleVo transferEntityToVo(DefineModule entity);

    @Mappings({
            @Mapping(target = "typeStr",source = "type",qualifiedByName = "handleDefineModuleTypeGetLabel"),
            @Mapping(target = "iconVal",source = "icon"),
            @Mapping(target = "styleVal",source = "style"),
            @Mapping(target = "typeVal",source = "type"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineModuleVo transferDtoToVo(DefineModuleDto dto);
}
