package com.egg.manager.persistence.obl.user.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAttentionPersonEntity;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAttentionPersonDto;
import com.egg.manager.persistence.obl.user.pojo.mapstruct.conversion.OblUserAttentionPersonConversion;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAttentionPersonVo;
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
        uses = {OblUserAttentionPersonConversion.class}
)
public interface OblUserAttentionPersonMapstruct extends MyBaseMysqlMapstruct<OblUserAttentionPersonEntity, OblUserAttentionPersonVo, OblUserAttentionPersonDto> {
    OblUserAttentionPersonMapstruct INSTANCE = Mappers.getMapper(OblUserAttentionPersonMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblUserAttentionPersonEntity transferVoToEntity(OblUserAttentionPersonVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblUserAttentionPersonVo transferEntityToVo(OblUserAttentionPersonEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblUserAttentionPersonVo transferDtoToVo(OblUserAttentionPersonDto dto);


}