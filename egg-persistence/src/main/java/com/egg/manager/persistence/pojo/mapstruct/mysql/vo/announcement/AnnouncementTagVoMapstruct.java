package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTagTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {AnnouncementTagTransfer.class}
)
public interface AnnouncementTagVoMapstruct extends MyBaseMysqlVoMapstruct<AnnouncementTag, AnnouncementTagVo, AnnouncementTagDto> {
    AnnouncementTagVoMapstruct INSTANCE = Mappers.getMapper(AnnouncementTagVoMapstruct.class);





    AnnouncementTag transferVoToEntity(AnnouncementTagVo vo);

    AnnouncementTagVo transferEntityToVo(AnnouncementTag entity);

    AnnouncementTagVo transferDtoToVo(AnnouncementTagDto dto);

}
