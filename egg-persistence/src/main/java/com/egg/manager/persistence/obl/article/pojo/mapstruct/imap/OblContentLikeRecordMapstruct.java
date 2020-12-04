package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeRecordEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblContentLikeRecordConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeRecordVo;
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
        uses = {OblContentLikeRecordConversion.class}
)
public interface OblContentLikeRecordMapstruct extends MyBaseMysqlMapstruct<OblContentLikeRecordEntity, OblContentLikeRecordVo, OblContentLikeRecordDto> {
    OblContentLikeRecordMapstruct INSTANCE = Mappers.getMapper(OblContentLikeRecordMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblContentLikeRecordEntity transferVoToEntity(OblContentLikeRecordVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblContentLikeRecordVo transferEntityToVo(OblContentLikeRecordEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblContentLikeRecordVo transferDtoToVo(OblContentLikeRecordDto dto);


}