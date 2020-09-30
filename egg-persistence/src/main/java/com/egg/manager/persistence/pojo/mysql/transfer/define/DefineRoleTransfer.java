package com.egg.manager.persistence.pojo.mysql.transfer.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.define.DefineRoleMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineRoleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
