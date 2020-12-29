package com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserDepartmentEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserDepartmentDto;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.conversion.EmUserDepartmentConversion;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserDepartmentVo;
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
        uses = {EmUserDepartmentConversion.class}
)
public interface EmUserDepartmentMapstruct extends MyBaseMysqlMapstruct<EmUserDepartmentEntity, EmUserDepartmentVo, EmUserDepartmentDto> {
    EmUserDepartmentMapstruct INSTANCE = Mappers.getMapper(EmUserDepartmentMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmUserDepartmentEntity transferVoToEntity(EmUserDepartmentVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmUserDepartmentVo transferEntityToVo(EmUserDepartmentEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmUserDepartmentVo transferDtoToVo(EmUserDepartmentDto dto);
}
