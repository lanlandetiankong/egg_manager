package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.UserJobEntity;
import com.egg.manager.persistence.em.user.pojo.dto.UserJobDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.UserJobConversion;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.UserJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserJobConversion.class}
)
public interface UserJobMapstruct extends MyBaseMysqlMapstruct<UserJobEntity, UserJobVo, UserJobDto> {
    UserJobMapstruct INSTANCE = Mappers.getMapper(UserJobMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    UserJobEntity transferVoToEntity(UserJobVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserJobVo transferEntityToVo(UserJobEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserJobVo transferDtoToVo(UserJobDto dto);
}
