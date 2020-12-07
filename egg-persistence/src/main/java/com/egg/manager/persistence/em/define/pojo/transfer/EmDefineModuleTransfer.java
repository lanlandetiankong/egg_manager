package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineModuleEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineModuleDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.EmDefineModuleMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineModuleVo;
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
@Named("defineModuleTransfer")
public class EmDefineModuleTransfer extends BaseMysqlTransfer {

    static EmDefineModuleMapstruct emDefineModuleMapstruct = EmDefineModuleMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmDefineModuleEntity transferVoToEntity(EmDefineModuleVo vo) {
        if (vo == null) {
            return null;
        }
        EmDefineModuleEntity entity = emDefineModuleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmDefineModuleVo transferEntityToVo(EmDefineModuleEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefineModuleVo vo = emDefineModuleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmDefineModuleVo transferDtoToVo(EmDefineModuleDto dto) {
        if (dto == null) {
            return null;
        }
        EmDefineModuleVo vo = emDefineModuleMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmDefineModuleVo> transferEntityToVoList(List<EmDefineModuleEntity> defineModuleEntities) {
        if (defineModuleEntities == null) {
            return null;
        } else {
            List<EmDefineModuleVo> list = new ArrayList<>();
            for (EmDefineModuleEntity emDefineModuleEntity : defineModuleEntities) {
                list.add(transferEntityToVo(emDefineModuleEntity));
            }
            return list;
        }
    }

    public static List<EmDefineModuleVo> transferDtoToVoList(List<EmDefineModuleDto> emDefineModuleDtos) {
        if (emDefineModuleDtos == null) {
            return null;
        } else {
            List<EmDefineModuleVo> list = new ArrayList<>();
            for (EmDefineModuleDto emDefineModuleDto : emDefineModuleDtos) {
                list.add(transferDtoToVo(emDefineModuleDto));
            }
            return list;
        }
    }

}
