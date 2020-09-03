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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * TODO
 */
@Mapper(componentModel = "spring",
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
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(entity.getContent(),\"\"))")
    })
    AnnouncementVo transferEntityToVo(Announcement entity,@Context Map<String, AnnouncementTag> announcementTagMap);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(dto.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(dto.getContent(),\"\"))")
    })
    AnnouncementVo transferDtoToVo(AnnouncementDto dto,@Context Map<String, AnnouncementTag> announcementTagMap);
}
