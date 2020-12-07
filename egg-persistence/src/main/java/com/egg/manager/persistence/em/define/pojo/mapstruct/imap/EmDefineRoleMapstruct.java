package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineRoleEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineRoleDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.EmDefineRoleConversion;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineRoleVo;
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
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmDefineRoleConversion.class}
)
public interface EmDefineRoleMapstruct extends MyBaseMysqlMapstruct<EmDefineRoleEntity, EmDefineRoleVo, EmDefineRoleDto> {
    EmDefineRoleMapstruct INSTANCE = Mappers.getMapper(EmDefineRoleMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmDefineRoleEntity transferVoToEntity(EmDefineRoleVo vo);

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
    EmDefineRoleVo transferEntityToVo(EmDefineRoleEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "typeStr", expression = "java(handleDefineRoleTypeGetLabel(dto.getType()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmDefineRoleVo transferDtoToVo(EmDefineRoleDto dto);
}
