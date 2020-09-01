package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnnouncementTagVoMapstruct extends MyBaseMysqlVoMapstruct<AnnouncementTag, AnnouncementTagVo, AnnouncementTagDto> {
    AnnouncementTagVoMapstruct INSTANCE = Mappers.getMapper(AnnouncementTagVoMapstruct.class);

    AnnouncementTag transferVoToEntity(AnnouncementTagVo announcementTagVo);

    AnnouncementTagVo transferEntityToVo(AnnouncementTag announcementTag);

    AnnouncementTagVo transferDtoToVo(AnnouncementTagDto announcementTagDto);


    List<AnnouncementTagVo> batchTransferEntityToVo(List<AnnouncementTag> announcementTags);

    List<AnnouncementTagVo> batchTransferDtoToVoList(List<AnnouncementTagDto> announcementTagDtos);

}
