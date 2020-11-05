package com.egg.manager.persistence.em.define.pojo.transfer;


import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRole;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.DefineRoleMapstruct;
import com.egg.manager.persistence.enhance.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineRoleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("defineRoleTransfer")
public class DefineRoleTransfer extends BaseMysqlTransfer {
    static DefineRoleMapstruct defineRoleMapstruct = DefineRoleMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static DefineRole transferVoToEntity(DefineRoleVo vo) {
        if (vo == null) {
            return null;
        }
        DefineRole entity = defineRoleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineRoleVo transferEntityToVo(DefineRole entity) {
        if (entity == null) {
            return null;
        }
        DefineRoleVo vo = defineRoleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static DefineRoleVo transferDtoToVo(DefineRoleDto dto) {
        if (dto == null) {
            return null;
        }
        DefineRoleVo vo = defineRoleMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<DefineRoleVo> transferEntityToVoList(List<DefineRole> definePermissions) {
        if (definePermissions == null) {
            return null;
        } else {
            List<DefineRoleVo> list = new ArrayList<>();
            for (DefineRole definePermission : definePermissions) {
                list.add(transferEntityToVo(definePermission));
            }
            return list;
        }
    }

    public static List<DefineRoleVo> transferDtoToVoList(List<DefineRoleDto> defineRoleDtos) {
        if (defineRoleDtos == null) {
            return null;
        } else {
            List<DefineRoleVo> list = new ArrayList<>();
            for (DefineRoleDto defineRoleDto : defineRoleDtos) {
                list.add(transferDtoToVo(defineRoleDto));
            }
            return list;
        }
    }

}
