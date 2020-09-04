package com.egg.manager.persistence.pojo.mapstruct.mysql.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * TODO
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {AnnouncementDraftTransfer.class}
)
public interface AnnouncementDraftMapstruct extends MyBaseMysqlMapstruct<AnnouncementDraft, AnnouncementDraftVo,AnnouncementDraftDto> {

    AnnouncementDraftMapstruct INSTANCE = Mappers.getMapper(AnnouncementDraftMapstruct.class);



    @Mappings({
        @Mapping(target = "tagIds", expression = "java(handleTagIdListToJsonString(vo.getTagIds()))")
    })
    AnnouncementDraft transferVoToEntity(AnnouncementDraftVo vo);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(entity.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(entity.getContent(),\"\"))"),
            @Mapping(target = "tagNames", ignore = true),
            @Mapping(target = "tagNameOfStr", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    @InheritConfiguration(name="defaultConfigVoToDto")
    AnnouncementDraftVo transferEntityToVo(AnnouncementDraft entity);

    @Mappings({
            @Mapping(target = "tagIds", expression = "java(handleTagIdJsonStringToList(dto.getTagIds()))"),
            @Mapping(target = "shortContent", expression = "java(handleHtmlDomToText(dto.getContent(),\"\"))"),
            @Mapping(target = "tagNames", ignore = true),
            @Mapping(target = "tagNameOfStr", ignore = true),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    @InheritConfiguration(name="defaultConfigVoToDto")
    AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto dto);

}
