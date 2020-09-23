package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.announcement.AnnouncementConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {AnnouncementConversion.class}
)
public interface AnnouncementMapstruct extends MyBaseMysqlMapstruct<Announcement, AnnouncementVo, AnnouncementDto> {
    AnnouncementMapstruct INSTANCE = Mappers.getMapper(AnnouncementMapstruct.class);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    Announcement transferVoToEntity(AnnouncementVo vo);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(entity.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(entity.getContent(),\"\"))"),
            @Mapping(target = "tagNames", ignore = true),
            @Mapping(target = "tagNameOfStr", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    AnnouncementVo transferEntityToVo(Announcement entity, @Context Map<String, AnnouncementTag> announcementTagMap);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(dto.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(dto.getContent(),\"\"))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    AnnouncementVo transferDtoToVo(AnnouncementDto dto, @Context Map<String, AnnouncementTag> announcementTagMap);


    @Mappings({

    })
    Announcement transferFromDraftEntity(AnnouncementDraft draft);
}
