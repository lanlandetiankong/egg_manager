package com.egg.manager.persistence.pojo.transfer.mysql.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement.AnnouncementVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Named("AnnouncementTransfer")
public class AnnouncementTransfer extends MyBaseMysqlTransfer {
    static AnnouncementVoMapstruct announcementVoMapstruct = AnnouncementVoMapstruct.INSTANCE;

    public static Announcement transferVoToEntity(AnnouncementVo vo) {
        if (vo == null) {
            return null;
        }
        Announcement entity = announcementVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * TODO
     * @param entity
     * @param announcementTagMap
     * @return
     */
    public static AnnouncementVo transferEntityToVo(Announcement entity, Map<String, AnnouncementTag> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        AnnouncementVo announcementVo = announcementVoMapstruct.transferEntityToVo(entity,announcementTagMap);
        return announcementVo;
    }

    public static List<AnnouncementVo> transferEntityToVoList(List<Announcement> entityList, Map<String, AnnouncementTag> announcementTagMap) {
        if (entityList == null) {
            return null;
        } else {
            List<AnnouncementVo> list = new ArrayList<>();
            for (Announcement entity : entityList) {
                list.add(transferEntityToVo(entity, announcementTagMap));
            }
            return list;
        }
    }


    public static AnnouncementVo transferDtoToVo(AnnouncementDto dto, Map<String, AnnouncementTag> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        //TODO
        AnnouncementVo vo = announcementVoMapstruct.transferDtoToVo(dto,announcementTagMap);
        vo.setCreateUser(UserAccountTransfer.transferEntityToVo(dto.getCreateUser()));
        vo.setLastModifyer(UserAccountTransfer.transferEntityToVo(dto.getLastModifyer()));
        return vo;
    }

    public static List<AnnouncementVo> transferDtoToVoList(List<AnnouncementDto> dtos, Map<String, AnnouncementTag> announcementTagMap) {
        if (dtos == null) {
            return null;
        } else {
            List<AnnouncementVo> list = new ArrayList<>();
            for (AnnouncementDto dto : dtos) {
                list.add(transferDtoToVo(dto, announcementTagMap));
            }
            return list;
        }
    }

}
