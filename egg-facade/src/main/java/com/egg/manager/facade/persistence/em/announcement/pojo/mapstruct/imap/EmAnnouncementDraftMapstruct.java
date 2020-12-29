package com.egg.manager.facade.persistence.em.announcement.pojo.mapstruct.imap;

import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementDraftEntity;
import com.egg.manager.facade.persistence.em.announcement.pojo.dto.EmAnnouncementDraftDto;
import com.egg.manager.facade.persistence.em.announcement.pojo.mapstruct.conversion.EmAnnouncementDraftConversion;
import com.egg.manager.facade.persistence.em.announcement.pojo.vo.EmAnnouncementDraftVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmAnnouncementDraftConversion.class}
)
public interface EmAnnouncementDraftMapstruct extends MyBaseMysqlMapstruct<EmAnnouncementDraftEntity, EmAnnouncementDraftVo, EmAnnouncementDraftDto> {

    EmAnnouncementDraftMapstruct INSTANCE = Mappers.getMapper(EmAnnouncementDraftMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    EmAnnouncementDraftEntity transferVoToEntity(EmAnnouncementDraftVo vo);

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
    EmAnnouncementDraftVo transferEntityToVo(EmAnnouncementDraftEntity entity);

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
    EmAnnouncementDraftVo transferDtoToVo(EmAnnouncementDraftDto dto);

}
