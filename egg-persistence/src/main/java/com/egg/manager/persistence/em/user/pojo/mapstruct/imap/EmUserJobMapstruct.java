package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserJobEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserJobDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.EmUserJobConversion;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserJobVo;
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
        uses = {EmUserJobConversion.class}
)
public interface EmUserJobMapstruct extends MyBaseMysqlMapstruct<EmUserJobEntity, EmUserJobVo, EmUserJobDto> {
    EmUserJobMapstruct INSTANCE = Mappers.getMapper(EmUserJobMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmUserJobEntity transferVoToEntity(EmUserJobVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmUserJobVo transferEntityToVo(EmUserJobEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmUserJobVo transferDtoToVo(EmUserJobDto dto);
}
