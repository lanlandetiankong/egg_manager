package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserTenantEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserTenantDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.EmUserTenantConversion;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserTenantVo;
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
        uses = {EmUserTenantConversion.class}
)
public interface EmUserTenantMapstruct extends MyBaseMysqlMapstruct<EmUserTenantEntity, EmUserTenantVo, EmUserTenantDto> {
    EmUserTenantMapstruct INSTANCE = Mappers.getMapper(EmUserTenantMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmUserTenantEntity transferVoToEntity(EmUserTenantVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmUserTenantVo transferEntityToVo(EmUserTenantEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmUserTenantVo transferDtoToVo(EmUserTenantDto dto);
}
