package com.egg.manager.persistence.pojo.mysql.transfer.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.announcement.AnnouncementTagMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementTagVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("AnnouncementTagTransfer")
public class AnnouncementTagTransfer extends MyBaseMysqlTransfer {

    static AnnouncementTagMapstruct announcementTagVoMapstruct = AnnouncementTagMapstruct.INSTANCE ;

    @Deprecated
    public static AnnouncementTag transferVoToEntity(AnnouncementTagVo vo) {
        if (vo == null) {
            return null;
        }
        AnnouncementTag entity = announcementTagVoMapstruct.transferVoToEntity(vo);
        return entity;
    }
    @Deprecated
    public static AnnouncementTagVo transferEntityToVo(AnnouncementTag entity) {
        if (entity == null) {
            return null;
        }
        AnnouncementTagVo vo = announcementTagVoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static AnnouncementTagVo transferDtoToVo(AnnouncementTagDto dto) {
        if (dto == null) {
            return null;
        }
        AnnouncementTagVo vo = announcementTagVoMapstruct.transferDtoToVo(dto);
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
