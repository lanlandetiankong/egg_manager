package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserGroupDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.user.UserGroupConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserGroupConversion.class}
)
public interface UserGroupMapstruct extends MyBaseMysqlMapstruct<UserGroup, UserGroupVo, UserGroupDto> {
    UserGroupMapstruct INSTANCE = Mappers.getMapper(UserGroupMapstruct.class);

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
