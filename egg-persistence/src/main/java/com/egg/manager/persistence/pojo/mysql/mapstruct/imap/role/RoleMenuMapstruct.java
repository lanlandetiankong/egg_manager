package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.role;


import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.mysql.dto.role.RoleMenuDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.role.RoleMenuConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.role.RoleMenuVo;
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
        uses = {RoleMenuConversion.class}
)
public interface RoleMenuMapstruct extends MyBaseMysqlMapstruct<RoleMenu, RoleMenuVo, RoleMenuDto> {
    RoleMenuMapstruct INSTANCE = Mappers.getMapper(RoleMenuMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    RoleMenu transferVoToEntity(RoleMenuVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    RoleMenuVo transferEntityToVo(RoleMenu entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    RoleMenuVo transferDtoToVo(RoleMenuDto dto);
}
