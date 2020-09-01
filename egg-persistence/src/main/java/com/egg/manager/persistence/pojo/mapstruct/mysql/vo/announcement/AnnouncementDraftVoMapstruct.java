package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * TODO
 */
@Mapper(componentModel = "spring",uses = {AnnouncementDraftTransfer.class})
public interface AnnouncementDraftVoMapstruct extends MyBaseMysqlVoMapstruct<AnnouncementDraft, AnnouncementDraftVo,AnnouncementDraftDto> {

    AnnouncementDraftVoMapstruct INSTANCE = Mappers.getMapper(AnnouncementDraftVoMapstruct.class);


    @Mappings({
        @Mapping(target = "tagIds", expression = "java(tagIdListToJsonString(vo.getTagIds()))")
        //@Mapping(target = "id",expression = "java(getUUID())") 调用父接口的方法
    })
    AnnouncementDraft transferVoToEntity(AnnouncementDraftVo vo);

    @Mappings({
            @Mapping(source = "tagIds",target = "tagIds", qualifiedByName = "tagIdJsonStringToList")
    })
    AnnouncementDraftVo transferEntityToVo(AnnouncementDraft announcementDraft);


    //AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto announcementDraftDto);


    List<AnnouncementDraftVo> batchTransferEntityToVo(List<AnnouncementDraft> announcementDrafts);


    List<AnnouncementDraftVo> batchTransferDtoToVo(List<AnnouncementDraftDto> announcementDraftDtos);
}
