package com.egg.manager.facade.persistence.em.define.pojo.transfer;

import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineJobDto;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap.EmDefineJobMapstruct;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineJobVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
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
@Named("defineJobTransfer")
public class EmDefineJobTransfer extends BaseMysqlTransfer {
    static EmDefineJobMapstruct emDefineJobMapstruct = EmDefineJobMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmDefineJobEntity transferVoToEntity(EmDefineJobVo vo) {
        if (vo == null) {
            return null;
        }
        EmDefineJobEntity entity = emDefineJobMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmDefineJobVo transferEntityToVo(EmDefineJobEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefineJobVo vo = emDefineJobMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmDefineJobVo transferDtoToVo(EmDefineJobDto dto) {
        if (dto == null) {
            return null;
        }
        EmDefineJobVo vo = emDefineJobMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmDefineJobVo> transferEntityToVoList(List<EmDefineJobEntity> defineJobEntities) {
        if (defineJobEntities == null) {
            return null;
        } else {
            List<EmDefineJobVo> list = new ArrayList<>();
            for (EmDefineJobEntity emDefineJobEntity : defineJobEntities) {
                list.add(transferEntityToVo(emDefineJobEntity));
            }
            return list;
        }
    }

    public static List<EmDefineJobVo> transferDtoToVoList(List<EmDefineJobDto> emDefineJobDtos) {
        if (emDefineJobDtos == null) {
            return null;
        } else {
            List<EmDefineJobVo> list = new ArrayList<>();
            for (EmDefineJobDto emDefineJobDto : emDefineJobDtos) {
                list.add(transferDtoToVo(emDefineJobDto));
            }
            return list;
        }
    }

}
