package com.egg.manager.persistence.pojo.mapstruct.mysql.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserRoleTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserRoleTransfer.class}
)
public interface UserRoleMapstruct extends MyBaseMysqlMapstruct<UserRole, UserRoleVo, UserRoleDto> {
    UserRoleMapstruct INSTANCE = Mappers.getMapper(UserRoleMapstruct.class);


    @Mappings({})
    UserRole transferVoToEntity(UserRoleVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserRoleVo transferEntityToVo(UserRole entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserRoleVo transferDtoToVo(UserRoleDto dto);

}
