package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserJobEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserJobDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.EmUserJobMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserJobVo;
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
@Named("userJobTransfer")
public class EmUserJobTransfer extends BaseMysqlTransfer {

    static EmUserJobMapstruct emUserJobMapstruct = EmUserJobMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmUserJobEntity transferVoToEntity(EmUserJobVo vo) {
        if (vo == null) {
            return null;
        }
        EmUserJobEntity entity = emUserJobMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmUserJobVo transferEntityToVo(EmUserJobEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserJobVo vo = emUserJobMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmUserJobVo transferDtoToVo(EmUserJobDto dto) {
        if (dto == null) {
            return null;
        }
        EmUserJobVo entity = emUserJobMapstruct.transferDtoToVo(dto);
        return entity;
    }

    public static List<EmUserJobVo> transferEntityToVoList(List<EmUserJobEntity> userJobEntities) {
        if (userJobEntities == null) {
            return null;
        } else {
            List<EmUserJobVo> list = new ArrayList<>();
            for (EmUserJobEntity job : userJobEntities) {
                list.add(transferEntityToVo(job));
            }
            return list;
        }
    }

    public static List<EmUserJobVo> transferDtoToVoList(List<EmUserJobDto> emUserJobDtos) {
        if (emUserJobDtos == null) {
            return null;
        } else {
            List<EmUserJobVo> list = new ArrayList<>();
            for (EmUserJobDto emUserJobDto : emUserJobDtos) {
                list.add(transferDtoToVo(emUserJobDto));
            }
            return list;
        }
    }

}
