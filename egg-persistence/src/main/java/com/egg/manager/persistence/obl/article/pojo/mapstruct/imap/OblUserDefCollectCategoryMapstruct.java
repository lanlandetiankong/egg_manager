package com.egg.manager.persistence.obl.article.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserDefCollectCategoryEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserDefCollectCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion.OblUserDefCollectCategoryConversion;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserDefCollectCategoryVo;
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
        uses = {OblUserDefCollectCategoryConversion.class}
)
public interface OblUserDefCollectCategoryMapstruct extends MyBaseMysqlMapstruct<OblUserDefCollectCategoryEntity, OblUserDefCollectCategoryVo, OblUserDefCollectCategoryDto> {
    OblUserDefCollectCategoryMapstruct INSTANCE = Mappers.getMapper(OblUserDefCollectCategoryMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblUserDefCollectCategoryEntity transferVoToEntity(OblUserDefCollectCategoryVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblUserDefCollectCategoryVo transferEntityToVo(OblUserDefCollectCategoryEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblUserDefCollectCategoryVo transferDtoToVo(OblUserDefCollectCategoryDto dto);


}