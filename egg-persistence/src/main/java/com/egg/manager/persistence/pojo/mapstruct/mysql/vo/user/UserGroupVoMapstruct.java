package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserGroupDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserGroupTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserGroupTransfer.class}
)
public interface UserGroupVoMapstruct extends MyBaseMysqlVoMapstruct<UserGroup,UserGroupVo, UserGroupDto> {
    UserGroupVoMapstruct INSTANCE = Mappers.getMapper(UserGroupVoMapstruct.class);

    @Mappings({})
    UserGroup transferVoToEntity(UserGroupVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserGroupVo transferEntityToVo(UserGroup entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserGroupVo transferDtoToVo(UserGroupDto dto);
}
