package com.egg.manager.persistence.em.announcement.pojo.transfer;

import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.pojo.dto.EmAnnouncementTagDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap.EmAnnouncementTagMapstruct;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementTagVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("announcementTagTransfer")
public class EmAnnouncementTagTransfer extends BaseMysqlTransfer {

    static EmAnnouncementTagMapstruct emAnnouncementTagMapstruct = EmAnnouncementTagMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmAnnouncementTagEntity transferVoToEntity(EmAnnouncementTagVo vo) {
        if (vo == null) {
            return null;
        }
        EmAnnouncementTagEntity entity = emAnnouncementTagMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmAnnouncementTagVo transferEntityToVo(EmAnnouncementTagEntity entity) {
        if (entity == null) {
            return null;
        }
        EmAnnouncementTagVo vo = emAnnouncementTagMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmAnnouncementTagVo transferDtoToVo(EmAnnouncementTagDto dto) {
        if (dto == null) {
            return null;
        }
        EmAnnouncementTagVo vo = emAnnouncementTagMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmAnnouncementTagVo> transferEntityToVoList(List<EmAnnouncementTagEntity> entityList) {
        if (entityList == null) {
            return null;
        } else {
            List<EmAnnouncementTagVo> list = new ArrayList<>();
            for (EmAnnouncementTagEntity entity : entityList) {
                list.add(transferEntityToVo(entity));
            }
            return list;
        }
    }

    public static List<EmAnnouncementTagVo> transferDtoToVoList(List<EmAnnouncementTagDto> dtoList) {
        if (dtoList == null) {
            return null;
        } else {
            List<EmAnnouncementTagVo> list = new ArrayList<>();
            for (EmAnnouncementTagDto dto : dtoList) {
                list.add(transferDtoToVo(dto));
            }
            return list;
        }
    }

}
