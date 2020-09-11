package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.define.DefineRoleConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineRoleConversion.class}
)
public interface DefineRoleMapstruct extends MyBaseMysqlMapstruct<DefineRole, DefineRoleVo, DefineRoleDto> {
    DefineRoleMapstruct INSTANCE = Mappers.getMapper(DefineRoleMapstruct.class);


    @Mappings({})
    DefineRole transferVoToEntity(DefineRoleVo vo);

    @Mappings({
            @Mapping(target = "typeStr", expression = "java(handleDefineRoleTypeGetLabel(entity.getType()))"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineRoleVo transferEntityToVo(DefineRole entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineRoleVo transferDtoToVo(DefineRoleDto dto);
}
