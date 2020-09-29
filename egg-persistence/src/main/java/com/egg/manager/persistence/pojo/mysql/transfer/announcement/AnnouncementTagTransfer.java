package com.egg.manager.persistence.pojo.mysql.transfer.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.announcement.AnnouncementTagMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementTagVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("announcementTagTransfer")
public class AnnouncementTagTransfer extends BaseMysqlTransfer {

    static AnnouncementTagMapstruct announcementTagMapstruct = AnnouncementTagMapstruct.INSTANCE;

    public static AnnouncementTag transferVoToEntity(AnnouncementTagVo vo) {
        if (vo == null) {
            return null;
        }
        AnnouncementTag entity = announcementTagMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static AnnouncementTagVo transferEntityToVo(AnnouncementTag entity) {
        if (entity == null) {
            return null;
        }
        AnnouncementTagVo vo = announcementTagMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static AnnouncementTagVo transferDtoToVo(AnnouncementTagDto dto) {
        if (dto == null) {
            return null;
        }
        AnnouncementTagVo vo = announcementTagMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<AnnouncementTagVo> transferEntityToVoList(List<AnnouncementTag> entityList) {
        if (entityList == null) {
            return null;
        } else {
            List<AnnouncementTagVo> list = new ArrayList<>();
            for (AnnouncementTag entity : entityList) {
                list.add(transferEntityToVo(entity));
            }
            return list;
        }
    }

    public static List<AnnouncementTagVo> transferDtoToVoList(List<AnnouncementTagDto> dtoList) {
        if (dtoList == null) {
            return null;
        } else {
            List<AnnouncementTagVo> list = new ArrayList<>();
            for (AnnouncementTagDto dto : dtoList) {
                list.add(transferDtoToVo(dto));
            }
            return list;
        }
    }

}
