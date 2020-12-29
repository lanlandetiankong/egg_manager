package com.egg.manager.facade.persistence.em.define.pojo.transfer;

import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineDepartmentEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap.EmDefineDepartmentMapstruct;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineDepartmentVo;
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
@Named("defineDepartmentTransfer")
public class EmDefineDepartmentTransfer extends BaseMysqlTransfer {
    static EmDefineDepartmentMapstruct emDefineDepartmentMapstruct = EmDefineDepartmentMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmDefineDepartmentEntity transferVoToEntity(EmDefineDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        EmDefineDepartmentEntity entity = emDefineDepartmentMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmDefineDepartmentVo transferEntityToVo(EmDefineDepartmentEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefineDepartmentVo vo = emDefineDepartmentMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmDefineDepartmentVo transferDtoToVo(EmDefineDepartmentDto dto) {
        if (dto == null) {
            return null;
        }
        EmDefineDepartmentVo vo = emDefineDepartmentMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmDefineDepartmentVo> transferEntityToVoList(List<EmDefineDepartmentEntity> defineDepartmentEntities) {
        if (defineDepartmentEntities == null) {
            return null;
        } else {
            List<EmDefineDepartmentVo> list = new ArrayList<>();
            for (EmDefineDepartmentEntity emDefineDepartmentEntity : defineDepartmentEntities) {
                list.add(transferEntityToVo(emDefineDepartmentEntity));
            }
            return list;
        }
    }

    public static List<EmDefineDepartmentVo> transferDtoToVoList(List<EmDefineDepartmentDto> emDefineDepartmentDtos) {
        if (emDefineDepartmentDtos == null) {
            return null;
        } else {
            List<EmDefineDepartmentVo> list = new ArrayList<>();
            for (EmDefineDepartmentDto emDefineDepartmentDto : emDefineDepartmentDtos) {
                list.add(transferDtoToVo(emDefineDepartmentDto));
            }
            return list;
        }
    }

}
