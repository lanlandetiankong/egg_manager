package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAppRelatedEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAppRelatedDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.EmUserAppRelatedConversion;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserAppRelatedVo;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020-12-07
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmUserAppRelatedConversion.class}
)
public interface EmUserAppRelatedMapstruct extends MyBaseMysqlMapstruct<EmUserAppRelatedEntity, EmUserAppRelatedVo, EmUserAppRelatedDto> {
    EmUserAppRelatedMapstruct INSTANCE = Mappers.getMapper(EmUserAppRelatedMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmUserAppRelatedEntity transferVoToEntity(EmUserAppRelatedVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmUserAppRelatedVo transferEntityToVo(EmUserAppRelatedEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmUserAppRelatedVo transferDtoToVo(EmUserAppRelatedDto dto);


}