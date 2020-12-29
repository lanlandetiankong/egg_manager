package com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmRoleMenuEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmRoleMenuDto;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.conversion.EmRoleMenuConversion;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmRoleMenuVo;
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
        uses = {EmRoleMenuConversion.class}
)
public interface EmRoleMenuMapstruct extends MyBaseMysqlMapstruct<EmRoleMenuEntity, EmRoleMenuVo, EmRoleMenuDto> {
    EmRoleMenuMapstruct INSTANCE = Mappers.getMapper(EmRoleMenuMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmRoleMenuEntity transferVoToEntity(EmRoleMenuVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmRoleMenuVo transferEntityToVo(EmRoleMenuEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmRoleMenuVo transferDtoToVo(EmRoleMenuDto dto);
}
