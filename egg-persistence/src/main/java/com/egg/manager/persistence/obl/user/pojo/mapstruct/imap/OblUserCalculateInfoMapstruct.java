package com.egg.manager.persistence.obl.user.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserCalculateInfoEntity;
import com.egg.manager.persistence.obl.user.pojo.mapstruct.conversion.OblUserCalculateInfoConversion;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserCalculateInfoDto;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserCalculateInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020-12-03
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {OblUserCalculateInfoConversion.class}
)
public interface OblUserCalculateInfoMapstruct extends MyBaseMysqlMapstruct<OblUserCalculateInfoEntity, OblUserCalculateInfoVo, OblUserCalculateInfoDto> {
    OblUserCalculateInfoMapstruct INSTANCE = Mappers.getMapper(OblUserCalculateInfoMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblUserCalculateInfoEntity transferVoToEntity(OblUserCalculateInfoVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblUserCalculateInfoVo transferEntityToVo(OblUserCalculateInfoEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblUserCalculateInfoVo transferDtoToVo(OblUserCalculateInfoDto dto);


}