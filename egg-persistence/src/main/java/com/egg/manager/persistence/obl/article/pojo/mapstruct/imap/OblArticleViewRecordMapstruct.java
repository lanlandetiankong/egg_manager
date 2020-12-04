package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleViewRecordEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleViewRecordDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblArticleViewRecordConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleViewRecordVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020-12-04
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {OblArticleViewRecordConversion.class}
)
public interface OblArticleViewRecordMapstruct extends MyBaseMysqlMapstruct<OblArticleViewRecordEntity, OblArticleViewRecordVo, OblArticleViewRecordDto> {
    OblArticleViewRecordMapstruct INSTANCE = Mappers.getMapper(OblArticleViewRecordMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblArticleViewRecordEntity transferVoToEntity(OblArticleViewRecordVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblArticleViewRecordVo transferEntityToVo(OblArticleViewRecordEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblArticleViewRecordVo transferDtoToVo(OblArticleViewRecordDto dto);


}