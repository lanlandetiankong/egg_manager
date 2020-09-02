package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * TODO
 */
@Mapper(componentModel = "spring",
        uses = {AnnouncementDraftTransfer.class}
)
public interface AnnouncementDraftVoMapstruct extends MyBaseMysqlVoMapstruct<AnnouncementDraft, AnnouncementDraftVo,AnnouncementDraftDto> {

    AnnouncementDraftVoMapstruct INSTANCE = Mappers.getMapper(AnnouncementDraftVoMapstruct.class);


    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer",ignore = true)
    })
    AnnouncementDraftVo defaultConfigEntityToVo(AnnouncementDraft entity);


    @Mappings({})
    AnnouncementDraftVo defaultConfigDtoToVo(AnnouncementDraftDto dto);




    @Mappings({
        @Mapping(target = "tagIds", expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    AnnouncementDraft transferVoToEntity(AnnouncementDraftVo vo);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(entity.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(entity.getContent(),\"\"))"),
    })
    @InheritConfiguration(name="defaultConfigEntityToVo")
    AnnouncementDraftVo transferEntityToVo(AnnouncementDraft entity);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(dto.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(dto.getContent(),\"\"))")
    })
    @InheritConfiguration(name="defaultConfigDtoToVo")
    AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto dto);

}
