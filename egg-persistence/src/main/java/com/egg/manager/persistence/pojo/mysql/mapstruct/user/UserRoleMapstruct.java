package com.egg.manager.persistence.pojo.mysql.mapstruct.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserRoleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserRoleVo;
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
