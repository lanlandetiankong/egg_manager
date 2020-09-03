package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDto;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTagTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * TODO
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {AnnouncementTransfer.class}
)
public interface AnnouncementVoMapstruct extends MyBaseMysqlVoMapstruct<Announcement,AnnouncementVo, AnnouncementDto> {
    AnnouncementVoMapstruct INSTANCE = Mappers.getMapper(AnnouncementVoMapstruct.class);

    @Mappings({
            @Mapping(target = "tagIds",expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    Announcement transferVoToEntity(AnnouncementVo vo);

    @Mappings({
            @Mapping(target = "tagIds",expression = "java(handleTagIdJsonStringToList(entity.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(entity.getContent(),\"\"))"),
            @Mapping(target = "tagNames", ignore = true),
            @Mapping(target = "tagNameOfStr", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    AnnouncementVo transferEntityToVo(Announcement entity,@Context Map<String, AnnouncementTag> announcementTagMap);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(dto.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(dto.getContent(),\"\"))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    AnnouncementVo transferDtoToVo(AnnouncementDto dto,@Context Map<String, AnnouncementTag> announcementTagMap);
}
