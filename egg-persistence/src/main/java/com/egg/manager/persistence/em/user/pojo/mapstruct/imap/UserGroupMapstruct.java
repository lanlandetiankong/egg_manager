package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.UserGroup;
import com.egg.manager.persistence.em.user.pojo.dto.UserGroupDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.UserGroupConversion;
import com.egg.manager.persistence.expand.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.UserGroupVo;
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
        uses = {UserGroupConversion.class}
)
public interface UserGroupMapstruct extends MyBaseMysqlMapstruct<UserGroup, UserGroupVo, UserGroupDto> {
    UserGroupMapstruct INSTANCE = Mappers.getMapper(UserGroupMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    UserGroup transferVoToEntity(UserGroupVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserGroupVo transferEntityToVo(UserGroup entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserGroupVo transferDtoToVo(UserGroupDto dto);
}
