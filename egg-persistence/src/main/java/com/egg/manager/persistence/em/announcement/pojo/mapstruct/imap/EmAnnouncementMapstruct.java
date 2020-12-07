package com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.pojo.dto.EmAnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.conversion.EmAnnouncementConversion;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementVo;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmAnnouncementConversion.class}
)
public interface EmAnnouncementMapstruct extends MyBaseMysqlMapstruct<EmAnnouncementEntity, EmAnnouncementVo, EmAnnouncementDto> {
    EmAnnouncementMapstruct INSTANCE = Mappers.getMapper(EmAnnouncementMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    EmAnnouncementEntity transferVoToEntity(EmAnnouncementVo vo);

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
    EmAnnouncementVo transferEntityToVo(EmAnnouncementEntity entity, @Context Map<String, EmAnnouncementTagEntity> announcementTagMap);

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
    EmAnnouncementVo transferDtoToVo(EmAnnouncementDto dto, @Context Map<String, EmAnnouncementTagEntity> announcementTagMap);

    /**
     * 公告草稿转公告(entity->entity)
     * @param draft 公告草稿entity
     * @return
     */
    @Mappings({})
    EmAnnouncementEntity transferFromDraftEntity(EmAnnouncementDraftEntity draft);
}
