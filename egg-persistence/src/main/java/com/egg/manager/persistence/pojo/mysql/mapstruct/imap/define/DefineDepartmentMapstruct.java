package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.define.DefineDepartmentConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineDepartmentConversion.class}
)
public interface DefineDepartmentMapstruct extends MyBaseMysqlMapstruct<DefineDepartment, DefineDepartmentVo, DefineDepartmentDto> {
    DefineDepartmentMapstruct INSTANCE = Mappers.getMapper(DefineDepartmentMapstruct.class);


    @Mappings({})
    DefineDepartment transferVoToEntity(DefineDepartmentVo vo);

    @Mappings({
            @Mapping(target = "parentDepartment", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineDepartmentVo transferEntityToVo(DefineDepartment entity);

    @Mappings({
            @Mapping(target = "parentDepartment",expression = "java(transferDtoToVo(dto.getParentDepartment()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineDepartmentVo transferDtoToVo(DefineDepartmentDto dto);



}
