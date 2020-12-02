package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeLogEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblContentLikeLogConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020-12-02
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {OblContentLikeLogConversion.class}
)
public interface OblContentLikeLogMapstruct extends MyBaseMysqlMapstruct<OblContentLikeLogEntity, OblContentLikeLogVo, OblContentLikeLogDto> {
    OblContentLikeLogMapstruct INSTANCE = Mappers.getMapper(OblContentLikeLogMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblContentLikeLogEntity transferVoToEntity(OblContentLikeLogVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblContentLikeLogVo transferEntityToVo(OblContentLikeLogEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblContentLikeLogVo transferDtoToVo(OblContentLikeLogDto dto);


}