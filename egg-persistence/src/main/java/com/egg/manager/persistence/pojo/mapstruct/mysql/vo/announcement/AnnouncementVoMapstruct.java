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

/**
 * TODO
 */
@Mapper(componentModel = "spring",
        uses = {AnnouncementTransfer.class}
)
public interface AnnouncementVoMapstruct extends MyBaseMysqlVoMapstruct<Announcement,AnnouncementVo, AnnouncementDto> {
    AnnouncementVoMapstruct INSTANCE = Mappers.getMapper(AnnouncementVoMapstruct.class);



    Announcement transferVoToEntity(AnnouncementVo vo);

    AnnouncementVo transferEntityToVo(Announcement entity);

    AnnouncementVo transferDtoToVo(AnnouncementDto dto);
}
