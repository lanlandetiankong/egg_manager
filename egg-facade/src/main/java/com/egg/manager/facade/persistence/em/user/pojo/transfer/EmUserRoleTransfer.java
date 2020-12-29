package com.egg.manager.facade.persistence.em.user.pojo.transfer;

import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserRoleEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserRoleDto;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap.EmUserRoleMapstruct;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserRoleVo;
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
@Named("userRoleTransfer")
public class EmUserRoleTransfer extends BaseMysqlTransfer {

    static EmUserRoleMapstruct emUserRoleMapstruct = EmUserRoleMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmUserRoleEntity transferVoToEntity(EmUserRoleVo vo) {
        if (vo == null) {
            return null;
        }
        EmUserRoleEntity entity = emUserRoleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmUserRoleVo transferEntityToVo(EmUserRoleEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserRoleVo vo = emUserRoleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmUserRoleVo transferDtoToVo(EmUserRoleDto dto) {
        if (dto == null) {
            return null;
        }
        EmUserRoleVo vo = emUserRoleMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmUserRoleVo> transferEntityToVoList(List<EmUserRoleEntity> userRoleEntities) {
        if (userRoleEntities == null) {
            return null;
        } else {
            List<EmUserRoleVo> list = new ArrayList<>();
            for (EmUserRoleEntity role : userRoleEntities) {
                list.add(transferEntityToVo(role));
            }
            return list;
        }
    }

    public static List<EmUserRoleVo> transferDtoToVoList(List<EmUserRoleDto> emUserRoleDtos) {
        if (emUserRoleDtos == null) {
            return null;
        } else {
            List<EmUserRoleVo> list = new ArrayList<>();
            for (EmUserRoleDto emUserRoleDto : emUserRoleDtos) {
                list.add(transferDtoToVo(emUserRoleDto));
            }
            return list;
        }
    }


}
