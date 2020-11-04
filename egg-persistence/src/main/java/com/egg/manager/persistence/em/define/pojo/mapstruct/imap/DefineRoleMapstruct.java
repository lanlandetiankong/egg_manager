package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRole;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.DefineRoleConversion;
import com.egg.manager.persistence.expand.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.DefineRoleVo;
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
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineRoleConversion.class}
)
public interface DefineRoleMapstruct extends MyBaseMysqlMapstruct<DefineRole, DefineRoleVo, DefineRoleDto> {
    DefineRoleMapstruct INSTANCE = Mappers.getMapper(DefineRoleMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    DefineRole transferVoToEntity(DefineRoleVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "typeStr", expression = "java(handleDefineRoleTypeGetLabel(entity.getType()))"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineRoleVo transferEntityToVo(DefineRole entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineRoleVo transferDtoToVo(DefineRoleDto dto);
}
