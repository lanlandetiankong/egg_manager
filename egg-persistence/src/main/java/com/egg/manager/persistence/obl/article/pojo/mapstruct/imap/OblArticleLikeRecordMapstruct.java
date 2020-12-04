package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeRecordEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblArticleLikeRecordConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeRecordVo;
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
        uses = {OblArticleLikeRecordConversion.class}
)
public interface OblArticleLikeRecordMapstruct extends MyBaseMysqlMapstruct<OblArticleLikeRecordEntity, OblArticleLikeRecordVo, OblArticleLikeRecordDto> {
    OblArticleLikeRecordMapstruct INSTANCE = Mappers.getMapper(OblArticleLikeRecordMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblArticleLikeRecordEntity transferVoToEntity(OblArticleLikeRecordVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblArticleLikeRecordVo transferEntityToVo(OblArticleLikeRecordEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblArticleLikeRecordVo transferDtoToVo(OblArticleLikeRecordDto dto);


}