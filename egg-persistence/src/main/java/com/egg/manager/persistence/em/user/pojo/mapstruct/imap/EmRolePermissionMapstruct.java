package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.EmRolePermissionEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmRolePermissionDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.EmRolePermissionConversion;
import com.egg.manager.persistence.em.user.pojo.vo.EmRolePermissionVo;
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
        uses = {EmRolePermissionConversion.class}
)
public interface EmRolePermissionMapstruct extends MyBaseMysqlMapstruct<EmRolePermissionEntity, EmRolePermissionVo, EmRolePermissionDto> {

    EmRolePermissionMapstruct INSTANCE = Mappers.getMapper(EmRolePermissionMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmRolePermissionEntity transferVoToEntity(EmRolePermissionVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmRolePermissionVo transferEntityToVo(EmRolePermissionEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmRolePermissionVo transferDtoToVo(EmRolePermissionDto dto);
}
