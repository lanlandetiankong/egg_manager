package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermission;
import com.egg.manager.persistence.em.user.pojo.dto.RolePermissionDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.RolePermissionConversion;
import com.egg.manager.persistence.expand.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.RolePermissionVo;
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
        uses = {RolePermissionConversion.class}
)
public interface RolePermissionMapstruct extends MyBaseMysqlMapstruct<RolePermission, RolePermissionVo, RolePermissionDto> {

    RolePermissionMapstruct INSTANCE = Mappers.getMapper(RolePermissionMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    RolePermission transferVoToEntity(RolePermissionVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    RolePermissionVo transferEntityToVo(RolePermission entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    RolePermissionVo transferDtoToVo(RolePermissionDto dto);
}
