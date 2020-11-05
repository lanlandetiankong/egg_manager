package com.egg.manager.persistence.em.announcement.pojo.transfer;

import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTag;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementTagDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap.AnnouncementTagMapstruct;
import com.egg.manager.persistence.enhance.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementTagVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("announcementTagTransfer")
public class AnnouncementTagTransfer extends BaseMysqlTransfer {

    static AnnouncementTagMapstruct announcementTagMapstruct = AnnouncementTagMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static AnnouncementTag transferVoToEntity(AnnouncementTagVo vo) {
        if (vo == null) {
            return null;
        }
        AnnouncementTag entity = announcementTagMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static AnnouncementTagVo transferEntityToVo(AnnouncementTag entity) {
        if (entity == null) {
            return null;
        }
        AnnouncementTagVo vo = announcementTagMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
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
