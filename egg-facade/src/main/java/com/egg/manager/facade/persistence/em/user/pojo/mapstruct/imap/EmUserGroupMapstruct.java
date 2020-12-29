package com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserGroupEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserGroupDto;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.conversion.EmUserGroupConversion;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserGroupVo;
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
        uses = {EmUserGroupConversion.class}
)
public interface EmUserGroupMapstruct extends MyBaseMysqlMapstruct<EmUserGroupEntity, EmUserGroupVo, EmUserGroupDto> {
    EmUserGroupMapstruct INSTANCE = Mappers.getMapper(EmUserGroupMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmUserGroupEntity transferVoToEntity(EmUserGroupVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmUserGroupVo transferEntityToVo(EmUserGroupEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmUserGroupVo transferDtoToVo(EmUserGroupDto dto);
}
