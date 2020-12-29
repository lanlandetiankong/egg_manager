package com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineGroupDto;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.conversion.EmDefineGroupConversion;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineGroupVo;
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
        uses = {EmDefineGroupConversion.class}
)
public interface EmDefineGroupMapstruct extends MyBaseMysqlMapstruct<EmDefineGroupEntity, EmDefineGroupVo, EmDefineGroupDto> {
    EmDefineGroupMapstruct INSTANCE = Mappers.getMapper(EmDefineGroupMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmDefineGroupEntity transferVoToEntity(EmDefineGroupVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmDefineGroupVo transferEntityToVo(EmDefineGroupEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmDefineGroupVo transferDtoToVo(EmDefineGroupDto dto);
}
