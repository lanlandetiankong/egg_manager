package com.egg.manager.persistence.pojo.mapstruct.mysql.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserDepartmentTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserDepartmentTransfer.class}
)
public interface UserDepartmentMapstruct extends MyBaseMysqlMapstruct<UserDepartment, UserDepartmentVo, UserDepartmentDto> {
    UserDepartmentMapstruct INSTANCE = Mappers.getMapper(UserDepartmentMapstruct.class);

    @Mappings({})
    UserDepartment transferVoToEntity(UserDepartmentVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserDepartmentVo transferEntityToVo(UserDepartment entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserDepartmentVo transferDtoToVo(UserDepartmentDto dto);
}
