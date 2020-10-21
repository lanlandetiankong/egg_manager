package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.user.UserTenantConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserTenantVo;
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
        uses = {UserTenantConversion.class}
)
public interface UserTenantMapstruct extends MyBaseMysqlMapstruct<UserTenant, UserTenantVo, UserTenantDto> {
    UserTenantMapstruct INSTANCE = Mappers.getMapper(UserTenantMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    UserTenant transferVoToEntity(UserTenantVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserTenantVo transferEntityToVo(UserTenant entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserTenantVo transferDtoToVo(UserTenantDto dto);
}
