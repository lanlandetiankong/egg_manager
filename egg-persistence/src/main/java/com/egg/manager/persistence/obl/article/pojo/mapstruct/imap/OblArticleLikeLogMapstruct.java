package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeLogEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblArticleLikeLogConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeLogVo;
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
        uses = {OblArticleLikeLogConversion.class}
)
public interface OblArticleLikeLogMapstruct extends MyBaseMysqlMapstruct<OblArticleLikeLogEntity, OblArticleLikeLogVo, OblArticleLikeLogDto> {
    OblArticleLikeLogMapstruct INSTANCE = Mappers.getMapper(OblArticleLikeLogMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblArticleLikeLogEntity transferVoToEntity(OblArticleLikeLogVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblArticleLikeLogVo transferEntityToVo(OblArticleLikeLogEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblArticleLikeLogVo transferDtoToVo(OblArticleLikeLogDto dto);


}