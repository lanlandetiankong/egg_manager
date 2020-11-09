package com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.conversion.AnnouncementDraftConversion;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {AnnouncementDraftConversion.class}
)
public interface AnnouncementDraftMapstruct extends MyBaseMysqlMapstruct<AnnouncementDraftEntity, AnnouncementDraftVo, AnnouncementDraftDto> {

    AnnouncementDraftMapstruct INSTANCE = Mappers.getMapper(AnnouncementDraftMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    AnnouncementDraftEntity transferVoToEntity(AnnouncementDraftVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(entity.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(entity.getContent(),\"\"))"),
            @Mapping(target = "tagNames", ignore = true),
            @Mapping(target = "tagNameOfStr", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    @InheritConfiguration(name = "defaultConfigVoToDto")
    AnnouncementDraftVo transferEntityToVo(AnnouncementDraftEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(dto.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(dto.getContent(),\"\"))"),
            @Mapping(target = "tagNames", ignore = true),
            @Mapping(target = "tagNameOfStr", ignore = true),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    @InheritConfiguration(name = "defaultConfigVoToDto")
    AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto dto);

}
