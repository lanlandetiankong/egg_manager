package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefinePermissionEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.EmDefinePermissionMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefinePermissionVo;
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
@Named("definePermissionTransfer")
public class EmDefinePermissionTransfer extends BaseMysqlTransfer {

    static EmDefinePermissionMapstruct emDefinePermissionMapstruct = EmDefinePermissionMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmDefinePermissionEntity transferVoToEntity(EmDefinePermissionVo vo) {
        if (vo == null) {
            return null;
        }
        EmDefinePermissionEntity entity = emDefinePermissionMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static EmDefinePermissionVo transferEntityToVo(EmDefinePermissionEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefinePermissionVo vo = emDefinePermissionMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static EmDefinePermissionVo transferEntityToVo(EmDefinePermissionDto dto) {
        if (dto == null) {
            return null;
        }
        EmDefinePermissionVo vo = emDefinePermissionMapstruct.transferDtoToVo(dto);
        return vo;
    }

    /**
     * 已启用entity 值回设
     * @param updateEntity
     * @param oldEntity
     */
    public static void handleSwitchOpenChangeFieldChange(EmDefinePermissionEntity updateEntity, EmDefinePermissionEntity oldEntity) {
        if (updateEntity != null && oldEntity != null) {
            //避免前端可能篡改了数据！
            updateEntity.setEnsure(SwitchStateEnum.Open.getValue());
            updateEntity.setCode(oldEntity.getCode());
        }
    }

    public static List<EmDefinePermissionVo> transferEntityToVoList(List<EmDefinePermissionEntity> definePermissionEntities) {
        if (definePermissionEntities == null) {
            return null;
        } else {
            List<EmDefinePermissionVo> list = new ArrayList<>();
            for (EmDefinePermissionEntity emDefinePermissionEntity : definePermissionEntities) {
                list.add(transferEntityToVo(emDefinePermissionEntity));
            }
            return list;
        }
    }

    public static List<EmDefinePermissionVo> transferDtoToVoList(List<EmDefinePermissionDto> emDefinePermissionDtos) {
        if (emDefinePermissionDtos == null) {
            return null;
        } else {
            List<EmDefinePermissionVo> list = new ArrayList<>();
            for (EmDefinePermissionDto emDefinePermissionDto : emDefinePermissionDtos) {
                list.add(transferEntityToVo(emDefinePermissionDto));
            }
            return list;
        }
    }


}
