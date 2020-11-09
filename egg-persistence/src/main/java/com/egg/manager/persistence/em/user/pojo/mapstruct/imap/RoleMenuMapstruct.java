package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.RoleMenuEntity;
import com.egg.manager.persistence.em.user.pojo.dto.RoleMenuDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.RoleMenuConversion;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.RoleMenuVo;
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
        uses = {RoleMenuConversion.class}
)
public interface RoleMenuMapstruct extends MyBaseMysqlMapstruct<RoleMenuEntity, RoleMenuVo, RoleMenuDto> {
    RoleMenuMapstruct INSTANCE = Mappers.getMapper(RoleMenuMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    RoleMenuEntity transferVoToEntity(RoleMenuVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    RoleMenuVo transferEntityToVo(RoleMenuEntity entity);

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
