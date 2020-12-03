package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserAttentionArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserAttentionArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblUserAttentionArticleCategoryConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserAttentionArticleCategoryVo;
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
        uses = {OblUserAttentionArticleCategoryConversion.class}
)
public interface OblUserAttentionArticleCategoryMapstruct extends MyBaseMysqlMapstruct<OblUserAttentionArticleCategoryEntity, OblUserAttentionArticleCategoryVo, OblUserAttentionArticleCategoryDto> {
    OblUserAttentionArticleCategoryMapstruct INSTANCE = Mappers.getMapper(OblUserAttentionArticleCategoryMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblUserAttentionArticleCategoryEntity transferVoToEntity(OblUserAttentionArticleCategoryVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblUserAttentionArticleCategoryVo transferEntityToVo(OblUserAttentionArticleCategoryEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblUserAttentionArticleCategoryVo transferDtoToVo(OblUserAttentionArticleCategoryDto dto);


}