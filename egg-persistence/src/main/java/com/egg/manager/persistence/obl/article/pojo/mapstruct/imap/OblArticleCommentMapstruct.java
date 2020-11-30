package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCommentEntity;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblArticleCommentConversion;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCommentDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCommentVo;
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
        uses = {OblArticleCommentConversion.class}
)
public interface OblArticleCommentMapstruct extends MyBaseMysqlMapstruct<OblArticleCommentEntity, OblArticleCommentVo, OblArticleCommentDto> {
    OblArticleCommentMapstruct INSTANCE = Mappers.getMapper(OblArticleCommentMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblArticleCommentEntity transferVoToEntity(OblArticleCommentVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblArticleCommentVo transferEntityToVo(OblArticleCommentEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblArticleCommentVo transferDtoToVo(OblArticleCommentDto dto);


}