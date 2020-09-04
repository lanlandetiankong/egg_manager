package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineDepartmentTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineDepartmentTransfer.class}
)
public interface DefineDepartmentVoMapstruct extends MyBaseMysqlVoMapstruct<DefineDepartment, DefineDepartmentVo, DefineDepartmentDto> {
    DefineDepartmentVoMapstruct INSTANCE = Mappers.getMapper(DefineDepartmentVoMapstruct.class);


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
