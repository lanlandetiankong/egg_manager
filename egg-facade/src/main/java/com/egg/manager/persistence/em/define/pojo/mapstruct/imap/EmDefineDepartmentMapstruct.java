package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineDepartmentEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.EmDefineDepartmentConversion;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineDepartmentVo;
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
        uses = {EmDefineDepartmentConversion.class}
)
public interface EmDefineDepartmentMapstruct extends MyBaseMysqlMapstruct<EmDefineDepartmentEntity, EmDefineDepartmentVo, EmDefineDepartmentDto> {
    EmDefineDepartmentMapstruct INSTANCE = Mappers.getMapper(EmDefineDepartmentMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmDefineDepartmentEntity transferVoToEntity(EmDefineDepartmentVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "parentDepartment", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmDefineDepartmentVo transferEntityToVo(EmDefineDepartmentEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "parentDepartment", expression = "java(transferDtoToVo(dto.getParentDepartment()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmDefineDepartmentVo transferDtoToVo(EmDefineDepartmentDto dto);


}
