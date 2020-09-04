package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
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
public interface UserRoleVoMapstruct extends MyBaseMysqlVoMapstruct<UserRole, UserRoleVo, UserRoleDto> {
    UserRoleVoMapstruct INSTANCE = Mappers.getMapper(UserRoleVoMapstruct.class);


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
