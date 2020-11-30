package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleTagRelatedEntity;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblArticleTagRelatedConversion;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleTagRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleTagRelatedVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020-11-30
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {OblArticleTagRelatedConversion.class}
)
public interface OblArticleTagRelatedMapstruct extends MyBaseMysqlMapstruct<OblArticleTagRelatedEntity, OblArticleTagRelatedVo, OblArticleTagRelatedDto> {
    OblArticleTagRelatedMapstruct INSTANCE = Mappers.getMapper(OblArticleTagRelatedMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblArticleTagRelatedEntity transferVoToEntity(OblArticleTagRelatedVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblArticleTagRelatedVo transferEntityToVo(OblArticleTagRelatedEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblArticleTagRelatedVo transferDtoToVo(OblArticleTagRelatedDto dto);


}