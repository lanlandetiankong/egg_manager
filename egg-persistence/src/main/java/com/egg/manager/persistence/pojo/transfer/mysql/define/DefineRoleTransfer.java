package com.egg.manager.persistence.pojo.transfer.mysql.define;


import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define.DefineRoleVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineRoleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("DefineRoleTransfer")
public class DefineRoleTransfer extends MyBaseMysqlTransfer {
    static DefineRoleVoMapstruct defineRoleVoMapstruct = DefineRoleVoMapstruct.INSTANCE ;

    public static DefineRole transferVoToEntity(DefineRoleVo vo) {
        if (vo == null) {
            return null;
        }
        DefineRole entity = defineRoleVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineRoleVo transferEntityToVo(DefineRole entity) {
        if (entity == null) {
            return null;
        }
        DefineRoleVo vo = defineRoleVoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static DefineRoleVo transferDtoToVo(DefineRoleDto dto) {
        if (dto == null) {
            return null;
        }
        DefineRoleVo vo = defineRoleVoMapstruct.transferDtoToVo(dto);
        //TODO
        vo.setType(dto.getType());
        if (dto.getType() != null) {
            DefineRoleTypeEnum typeEnum = DefineRoleTypeEnum.doGetEnumByValue(dto.getType());
            if (typeEnum != null) {
                vo.setTypeStr(typeEnum.getLabel());
            } else {
                vo.setTypeStr("");
            }
        }
        vo.setCreateUser(UserAccountTransfer.transferEntityToVo(dto.getCreateUser()));
        vo.setLastModifyer(UserAccountTransfer.transferEntityToVo(dto.getLastModifyer()));
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
