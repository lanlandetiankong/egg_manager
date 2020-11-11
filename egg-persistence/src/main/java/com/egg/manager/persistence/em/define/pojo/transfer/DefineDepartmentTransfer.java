package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartmentEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.DefineDepartmentMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.DefineDepartmentVo;
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
@Named("defineDepartmentTransfer")
public class DefineDepartmentTransfer extends BaseMysqlTransfer {
    static DefineDepartmentMapstruct defineDepartmentMapstruct = DefineDepartmentMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static DefineDepartmentEntity transferVoToEntity(DefineDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        DefineDepartmentEntity entity = defineDepartmentMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineDepartmentVo transferEntityToVo(DefineDepartmentEntity entity) {
        if (entity == null) {
            return null;
        }
        DefineDepartmentVo vo = defineDepartmentMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static DefineDepartmentVo transferDtoToVo(DefineDepartmentDto dto) {
        if (dto == null) {
            return null;
        }
        DefineDepartmentVo vo = defineDepartmentMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<DefineDepartmentVo> transferEntityToVoList(List<DefineDepartmentEntity> defineDepartmentEntities) {
        if (defineDepartmentEntities == null) {
            return null;
        } else {
            List<DefineDepartmentVo> list = new ArrayList<>();
            for (DefineDepartmentEntity defineDepartmentEntity : defineDepartmentEntities) {
                list.add(transferEntityToVo(defineDepartmentEntity));
            }
            return list;
        }
    }

    public static List<DefineDepartmentVo> transferDtoToVoList(List<DefineDepartmentDto> defineDepartmentDtos) {
        if (defineDepartmentDtos == null) {
            return null;
        } else {
            List<DefineDepartmentVo> list = new ArrayList<>();
            for (DefineDepartmentDto defineDepartmentDto : defineDepartmentDtos) {
                list.add(transferDtoToVo(defineDepartmentDto));
            }
            return list;
        }
    }

}
