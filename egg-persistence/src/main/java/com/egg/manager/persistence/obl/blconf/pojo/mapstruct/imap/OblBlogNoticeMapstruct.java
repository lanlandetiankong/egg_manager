package com.egg.manager.persistence.obl.blconf.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogNoticeEntity;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogNoticeDto;
import com.egg.manager.persistence.obl.blconf.pojo.mapstruct.conversion.OblBlogNoticeConversion;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogNoticeVo;
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
        uses = {OblBlogNoticeConversion.class}
)
public interface OblBlogNoticeMapstruct extends MyBaseMysqlMapstruct<OblBlogNoticeEntity, OblBlogNoticeVo, OblBlogNoticeDto> {
    OblBlogNoticeMapstruct INSTANCE = Mappers.getMapper(OblBlogNoticeMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblBlogNoticeEntity transferVoToEntity(OblBlogNoticeVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblBlogNoticeVo transferEntityToVo(OblBlogNoticeEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblBlogNoticeVo transferDtoToVo(OblBlogNoticeDto dto);


}