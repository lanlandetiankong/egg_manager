package com.egg.manager.persistence.em.user.pojo.transfer;


import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAppRelatedEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAppRelatedDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.EmUserAppRelatedMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserAppRelatedVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description app用户关联表-Transfer
 * @date 2020-12-07
 */
@Component
@Named("emUserAppRelatedTransfer")
public class EmUserAppRelatedTransfer extends BaseMysqlTransfer {
    static EmUserAppRelatedMapstruct emUserAppRelatedMapstruct = EmUserAppRelatedMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmUserAppRelatedEntity transferVoToEntity(EmUserAppRelatedVo vo) {
        if (vo == null) {
            return null;
        }
        EmUserAppRelatedEntity entity = emUserAppRelatedMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmUserAppRelatedVo transferEntityToVo(EmUserAppRelatedEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserAppRelatedVo vo = emUserAppRelatedMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmUserAppRelatedVo transferDtoToVo(EmUserAppRelatedDto dto) {
        if (dto == null) {
            return null;
        }
        EmUserAppRelatedVo vo = emUserAppRelatedMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmUserAppRelatedVo> transferEntityToVoList(List<EmUserAppRelatedEntity> emUserAppRelatedEntities) {
        if (emUserAppRelatedEntities == null) {
            return null;
        } else {
            List<EmUserAppRelatedVo> list = new ArrayList<>();
            for (EmUserAppRelatedEntity emUserAppRelatedEntity : emUserAppRelatedEntities) {
                list.add(transferEntityToVo(emUserAppRelatedEntity));
            }
            return list;
        }
    }

    public static List<EmUserAppRelatedVo> transferDtoToVoList(List<EmUserAppRelatedDto> emUserAppRelatedDtos) {
        if (emUserAppRelatedDtos == null) {
            return null;
        } else {
            List<EmUserAppRelatedVo> list = new ArrayList<>();
            for (EmUserAppRelatedDto emUserAppRelatedDto : emUserAppRelatedDtos) {
                list.add(transferDtoToVo(emUserAppRelatedDto));
            }
            return list;
        }
    }

}