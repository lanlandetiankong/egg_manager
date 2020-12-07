package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineJobDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.EmDefineJobConversion;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineJobVo;
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
        uses = {EmDefineJobConversion.class}
)
public interface EmDefineJobMapstruct extends MyBaseMysqlMapstruct<EmDefineJobEntity, EmDefineJobVo, EmDefineJobDto> {
    EmDefineJobMapstruct INSTANCE = Mappers.getMapper(EmDefineJobMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmDefineJobEntity transferVoToEntity(EmDefineJobVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "typeStr", expression = "java(handleDefineJobTypeGetGetLabel(entity.getType()))"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmDefineJobVo transferEntityToVo(EmDefineJobEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmDefineJobVo transferDtoToVo(EmDefineJobDto dto);


}
