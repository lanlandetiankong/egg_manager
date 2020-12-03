package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblArticleCategoryConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryVo;
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
        uses = {OblArticleCategoryConversion.class}
)
public interface OblArticleCategoryMapstruct extends MyBaseMysqlMapstruct<OblArticleCategoryEntity, OblArticleCategoryVo, OblArticleCategoryDto> {
    OblArticleCategoryMapstruct INSTANCE = Mappers.getMapper(OblArticleCategoryMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblArticleCategoryEntity transferVoToEntity(OblArticleCategoryVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblArticleCategoryVo transferEntityToVo(OblArticleCategoryEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblArticleCategoryVo transferDtoToVo(OblArticleCategoryDto dto);


}