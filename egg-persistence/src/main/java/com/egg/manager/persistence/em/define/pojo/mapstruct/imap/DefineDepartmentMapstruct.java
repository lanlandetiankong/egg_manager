package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartment;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.DefineDepartmentConversion;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.DefineDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineDepartmentConversion.class}
)
public interface DefineDepartmentMapstruct extends MyBaseMysqlMapstruct<DefineDepartment, DefineDepartmentVo, DefineDepartmentDto> {
    DefineDepartmentMapstruct INSTANCE = Mappers.getMapper(DefineDepartmentMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    DefineDepartment transferVoToEntity(DefineDepartmentVo vo);

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
    DefineDepartmentVo transferEntityToVo(DefineDepartment entity);

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
    DefineDepartmentVo transferDtoToVo(DefineDepartmentDto dto);


}
