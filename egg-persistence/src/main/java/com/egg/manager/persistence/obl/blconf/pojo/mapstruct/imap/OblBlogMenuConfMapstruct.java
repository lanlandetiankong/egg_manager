package com.egg.manager.persistence.obl.blconf.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogMenuConfEntity;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogMenuConfDto;
import com.egg.manager.persistence.obl.blconf.pojo.mapstruct.conversion.OblBlogMenuConfConversion;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogMenuConfVo;
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
        uses = {OblBlogMenuConfConversion.class}
)
public interface OblBlogMenuConfMapstruct extends MyBaseMysqlMapstruct<OblBlogMenuConfEntity, OblBlogMenuConfVo, OblBlogMenuConfDto> {
    OblBlogMenuConfMapstruct INSTANCE = Mappers.getMapper(OblBlogMenuConfMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblBlogMenuConfEntity transferVoToEntity(OblBlogMenuConfVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblBlogMenuConfVo transferEntityToVo(OblBlogMenuConfEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblBlogMenuConfVo transferDtoToVo(OblBlogMenuConfDto dto);


}