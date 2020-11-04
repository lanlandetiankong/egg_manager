package com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.announcement.db.mysql.entity.Announcement;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraft;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTag;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.conversion.AnnouncementConversion;
import com.egg.manager.persistence.expand.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {AnnouncementConversion.class}
)
public interface AnnouncementMapstruct extends MyBaseMysqlMapstruct<Announcement, AnnouncementVo, AnnouncementDto> {
    AnnouncementMapstruct INSTANCE = Mappers.getMapper(AnnouncementMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    Announcement transferVoToEntity(AnnouncementVo vo);

    /**
     * entity转vo
     * @param entity             db实体类
     * @param announcementTagMap 公告标签Map
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
    AnnouncementVo transferEntityToVo(Announcement entity, @Context Map<Long, AnnouncementTag> announcementTagMap);

    /**
     * dto转vo
     * @param dto
     * @param announcementTagMap
     * @return
     */
    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(dto.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(dto.getContent(),\"\"))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    AnnouncementVo transferDtoToVo(AnnouncementDto dto, @Context Map<Long, AnnouncementTag> announcementTagMap);

    /**
     * 公告草稿转公告(entity->entity)
     * @param draft 公告草稿entity
     * @return
     */
    @Mappings({})
    Announcement transferFromDraftEntity(AnnouncementDraft draft);
}
