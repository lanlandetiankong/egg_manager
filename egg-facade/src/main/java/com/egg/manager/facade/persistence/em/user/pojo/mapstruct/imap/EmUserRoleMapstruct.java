package com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserRoleEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserRoleDto;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.conversion.EmUserRoleConversion;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserRoleVo;
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
        uses = {EmUserRoleConversion.class}
)
public interface EmUserRoleMapstruct extends MyBaseMysqlMapstruct<EmUserRoleEntity, EmUserRoleVo, EmUserRoleDto> {
    EmUserRoleMapstruct INSTANCE = Mappers.getMapper(EmUserRoleMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmUserRoleEntity transferVoToEntity(EmUserRoleVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmUserRoleVo transferEntityToVo(EmUserRoleEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmUserRoleVo transferDtoToVo(EmUserRoleDto dto);

}
