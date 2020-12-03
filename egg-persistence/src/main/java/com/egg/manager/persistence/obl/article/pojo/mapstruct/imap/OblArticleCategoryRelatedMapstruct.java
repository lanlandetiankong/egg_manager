package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryRelatedEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblArticleCategoryRelatedConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryRelatedVo;
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
        uses = {OblArticleCategoryRelatedConversion.class}
)
public interface OblArticleCategoryRelatedMapstruct extends MyBaseMysqlMapstruct<OblArticleCategoryRelatedEntity, OblArticleCategoryRelatedVo, OblArticleCategoryRelatedDto> {
    OblArticleCategoryRelatedMapstruct INSTANCE = Mappers.getMapper(OblArticleCategoryRelatedMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblArticleCategoryRelatedEntity transferVoToEntity(OblArticleCategoryRelatedVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblArticleCategoryRelatedVo transferEntityToVo(OblArticleCategoryRelatedEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblArticleCategoryRelatedVo transferDtoToVo(OblArticleCategoryRelatedDto dto);


}