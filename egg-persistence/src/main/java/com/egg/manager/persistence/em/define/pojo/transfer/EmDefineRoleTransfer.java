package com.egg.manager.persistence.em.define.pojo.transfer;


import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineRoleEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineRoleDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.EmDefineRoleMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineRoleVo;
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
@Named("defineRoleTransfer")
public class EmDefineRoleTransfer extends BaseMysqlTransfer {
    static EmDefineRoleMapstruct emDefineRoleMapstruct = EmDefineRoleMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmDefineRoleEntity transferVoToEntity(EmDefineRoleVo vo) {
        if (vo == null) {
            return null;
        }
        EmDefineRoleEntity entity = emDefineRoleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmDefineRoleVo transferEntityToVo(EmDefineRoleEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefineRoleVo vo = emDefineRoleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmDefineRoleVo transferDtoToVo(EmDefineRoleDto dto) {
        if (dto == null) {
            return null;
        }
        EmDefineRoleVo vo = emDefineRoleMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmDefineRoleVo> transferEntityToVoList(List<EmDefineRoleEntity> definePermissions) {
        if (definePermissions == null) {
            return null;
        } else {
            List<EmDefineRoleVo> list = new ArrayList<>();
            for (EmDefineRoleEntity definePermission : definePermissions) {
                list.add(transferEntityToVo(definePermission));
            }
            return list;
        }
    }

    public static List<EmDefineRoleVo> transferDtoToVoList(List<EmDefineRoleDto> emDefineRoleDtos) {
        if (emDefineRoleDtos == null) {
            return null;
        } else {
            List<EmDefineRoleVo> list = new ArrayList<>();
            for (EmDefineRoleDto emDefineRoleDto : emDefineRoleDtos) {
                list.add(transferDtoToVo(emDefineRoleDto));
            }
            return list;
        }
    }

}
