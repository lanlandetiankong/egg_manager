package com.egg.manager.persistence.pojo.mapstruct.mysql.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineRoleTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineRoleTransfer.class}
)
public interface DefineRoleMapstruct extends MyBaseMysqlMapstruct<DefineRole, DefineRoleVo, DefineRoleDto> {
    DefineRoleMapstruct INSTANCE = Mappers.getMapper(DefineRoleMapstruct.class);


    @Mappings({})
    DefineRole transferVoToEntity(DefineRoleVo vo);

    @Mappings({
            @Mapping(target = "typeStr", source = "type",qualifiedByName = "handleDefineRoleTypeGetLabel"),
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
