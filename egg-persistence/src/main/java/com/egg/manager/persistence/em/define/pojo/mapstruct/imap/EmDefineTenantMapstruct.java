package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineTenantEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.EmDefineTenantConversion;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineTenantVo;
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
        uses = {EmDefineTenantConversion.class}
)
public interface EmDefineTenantMapstruct extends MyBaseMysqlMapstruct<EmDefineTenantEntity, EmDefineTenantVo, EmDefineTenantDto> {

    EmDefineTenantMapstruct INSTANCE = Mappers.getMapper(EmDefineTenantMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmDefineTenantEntity transferVoToEntity(EmDefineTenantVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "typeStr", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmDefineTenantVo transferEntityToVo(EmDefineTenantEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmDefineTenantVo transferDtoToVo(EmDefineTenantDto dto);
}
