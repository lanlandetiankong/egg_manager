package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserCollectArticleEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserCollectArticleDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblUserCollectArticleConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserCollectArticleVo;
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
        uses = {OblUserCollectArticleConversion.class}
)
public interface OblUserCollectArticleMapstruct extends MyBaseMysqlMapstruct<OblUserCollectArticleEntity, OblUserCollectArticleVo, OblUserCollectArticleDto> {
    OblUserCollectArticleMapstruct INSTANCE = Mappers.getMapper(OblUserCollectArticleMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblUserCollectArticleEntity transferVoToEntity(OblUserCollectArticleVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblUserCollectArticleVo transferEntityToVo(OblUserCollectArticleEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblUserCollectArticleVo transferDtoToVo(OblUserCollectArticleDto dto);


}