package com.egg.manager.persistence.pojo.transfer.mysql.define;


import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineRoleMysqlDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineRoleMysqlVo;

import java.util.ArrayList;
import java.util.List;

public class DefineRoleMysqlTransfer extends MyBaseMysqlTransfer {
    public static DefineRole transferVoToEntity(DefineRoleMysqlVo defineRoleVo) {
        if (defineRoleVo == null) {
            return null;
        }
        DefineRole defineRole = new DefineRole();
        defineRole.setFid(defineRoleVo.getFid());
        defineRole.setName(defineRoleVo.getName());
        defineRole.setCode(defineRoleVo.getCode());
        defineRole.setType(defineRoleVo.getType());
        defineRole.setRemark(defineRoleVo.getRemark());
        defineRole.setState(defineRoleVo.getState());
        defineRole.setCreateTime(defineRoleVo.getCreateTime());
        defineRole.setUpdateTime(defineRoleVo.getUpdateTime());
        defineRole.setCreateUserId(defineRoleVo.getCreateUserId());
        defineRole.setLastModifyerId(defineRoleVo.getLastModifyerId());

        return defineRole;
    }

    public static DefineRoleMysqlVo transferEntityToVo(DefineRole defineRole) {
        if (defineRole == null) {
            return null;
        }
        DefineRoleMysqlVo defineRoleVo = new DefineRoleMysqlVo();
        defineRoleVo.setFid(defineRole.getFid());
        defineRoleVo.setName(defineRole.getName());
        defineRoleVo.setCode(defineRole.getCode());
        defineRoleVo.setType(defineRole.getType());
        if (defineRole.getType() != null) {
            DefineRoleTypeEnum typeEnum = DefineRoleTypeEnum.doGetEnumByValue(defineRole.getType());
            if (typeEnum != null) {
                defineRoleVo.setTypeStr(typeEnum.getLabel());
            } else {
                defineRoleVo.setTypeStr("");
            }
        }
        defineRoleVo.setRemark(defineRole.getRemark());
        defineRoleVo.setState(defineRole.getState());
        defineRoleVo.setCreateTime(defineRole.getCreateTime());
        defineRoleVo.setUpdateTime(defineRole.getUpdateTime());
        defineRoleVo.setCreateUserId(defineRole.getCreateUserId());
        defineRoleVo.setLastModifyerId(defineRole.getLastModifyerId());

        return defineRoleVo;
    }

    public static DefineRoleMysqlVo transferDtoToVo(DefineRoleMysqlDto defineRoleDto) {
        if (defineRoleDto == null) {
            return null;
        }
        DefineRoleMysqlVo defineRoleVo = new DefineRoleMysqlVo();
        defineRoleVo.setFid(defineRoleDto.getFid());
        defineRoleVo.setName(defineRoleDto.getName());
        defineRoleVo.setCode(defineRoleDto.getCode());
        defineRoleVo.setType(defineRoleDto.getType());
        if (defineRoleDto.getType() != null) {
            DefineRoleTypeEnum typeEnum = DefineRoleTypeEnum.doGetEnumByValue(defineRoleDto.getType());
            if (typeEnum != null) {
                defineRoleVo.setTypeStr(typeEnum.getLabel());
            } else {
                defineRoleVo.setTypeStr("");
            }
        }
        defineRoleVo.setState(defineRoleDto.getState());
        defineRoleVo.setRemark(defineRoleDto.getRemark());
        defineRoleVo.setCreateTime(defineRoleDto.getCreateTime());
        defineRoleVo.setUpdateTime(defineRoleDto.getUpdateTime());
        defineRoleVo.setCreateUserId(defineRoleDto.getCreateUserId());
        defineRoleVo.setLastModifyerId(defineRoleDto.getLastModifyerId());
        defineRoleVo.setCreateUser(UserAccountMysqlTransfer.transferEntityToVo(defineRoleDto.getCreateUser()));
        defineRoleVo.setLastModifyer(UserAccountMysqlTransfer.transferEntityToVo(defineRoleDto.getLastModifyer()));
        return defineRoleVo;
    }

    public static List<DefineRoleMysqlVo> transferEntityToVoList(List<DefineRole> definePermissions) {
        if (definePermissions == null) {
            return null;
        } else {
            List<DefineRoleMysqlVo> list = new ArrayList<>();
            for (DefineRole definePermission : definePermissions) {
                list.add(transferEntityToVo(definePermission));
            }
            return list;
        }
    }

    public static List<DefineRoleMysqlVo> transferDtoToVoList(List<DefineRoleMysqlDto> defineRoleDtos) {
        if (defineRoleDtos == null) {
            return null;
        } else {
            List<DefineRoleMysqlVo> list = new ArrayList<>();
            for (DefineRoleMysqlDto defineRoleDto : defineRoleDtos) {
                list.add(transferDtoToVo(defineRoleDto));
            }
            return list;
        }
    }
}
