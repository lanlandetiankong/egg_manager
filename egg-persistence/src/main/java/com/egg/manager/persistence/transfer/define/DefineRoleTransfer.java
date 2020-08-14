package com.egg.manager.persistence.transfer.define;


import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.persistence.dto.define.DefineRoleDto;
import com.egg.manager.persistence.entity.define.DefineRole;
import com.egg.manager.persistence.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.vo.define.DefineRoleVo;

import java.util.ArrayList;
import java.util.List;

public class DefineRoleTransfer {
    public static DefineRole transferVoToEntity(DefineRoleVo defineRoleVo) {
        if(defineRoleVo == null){
            return null ;
        }
        DefineRole defineRole = new DefineRole() ;
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

        return defineRole ;
    }

    public static DefineRoleVo transferEntityToVo(DefineRole defineRole) {
        if(defineRole == null){
            return null ;
        }
        DefineRoleVo defineRoleVo = new DefineRoleVo() ;
        defineRoleVo.setFid(defineRole.getFid());
        defineRoleVo.setName(defineRole.getName());
        defineRoleVo.setCode(defineRole.getCode());
        defineRoleVo.setType(defineRole.getType());
        if(defineRole.getType() != null){
            DefineRoleTypeEnum typeEnum = DefineRoleTypeEnum.doGetEnumByValue(defineRole.getType());
            if(typeEnum != null){
                defineRoleVo.setTypeStr(typeEnum.getLabel());
            }   else {
                defineRoleVo.setTypeStr("");
            }
        }
        defineRoleVo.setRemark(defineRole.getRemark());
        defineRoleVo.setState(defineRole.getState());
        defineRoleVo.setCreateTime(defineRole.getCreateTime());
        defineRoleVo.setUpdateTime(defineRole.getUpdateTime());
        defineRoleVo.setCreateUserId(defineRole.getCreateUserId());
        defineRoleVo.setLastModifyerId(defineRole.getLastModifyerId());

        return defineRoleVo ;
    }
    public static DefineRoleVo transferDtoToVo(DefineRoleDto defineRoleDto) {
        if(defineRoleDto == null){
            return null ;
        }
        DefineRoleVo defineRoleVo = new DefineRoleVo() ;
        defineRoleVo.setFid(defineRoleDto.getFid());
        defineRoleVo.setName(defineRoleDto.getName());
        defineRoleVo.setCode(defineRoleDto.getCode());
        defineRoleVo.setType(defineRoleDto.getType());
        if(defineRoleDto.getType() != null){
            DefineRoleTypeEnum typeEnum = DefineRoleTypeEnum.doGetEnumByValue(defineRoleDto.getType());
            if(typeEnum != null){
                defineRoleVo.setTypeStr(typeEnum.getLabel());
            }   else {
                defineRoleVo.setTypeStr("");
            }
        }
        defineRoleVo.setState(defineRoleDto.getState());
        defineRoleVo.setRemark(defineRoleDto.getRemark());
        defineRoleVo.setCreateTime(defineRoleDto.getCreateTime());
        defineRoleVo.setUpdateTime(defineRoleDto.getUpdateTime());
        defineRoleVo.setCreateUserId(defineRoleDto.getCreateUserId());
        defineRoleVo.setLastModifyerId(defineRoleDto.getLastModifyerId());
        defineRoleVo.setCreateUser(UserAccountTransfer.transferEntityToVo(defineRoleDto.getCreateUser()));
        defineRoleVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(defineRoleDto.getLastModifyer()));
        return defineRoleVo ;
    }

    public static List<DefineRoleVo> transferEntityToVoList(List<DefineRole> definePermissions){
        if(definePermissions == null){
            return null ;
        }   else {
            List<DefineRoleVo> list = new ArrayList<>() ;
            for (DefineRole definePermission : definePermissions){
                list.add(transferEntityToVo(definePermission));
            }
            return list ;
        }
    }

    public static List<DefineRoleVo> transferDtoToVoList(List<DefineRoleDto> defineRoleDtos){
        if(defineRoleDtos == null){
            return null ;
        }   else {
            List<DefineRoleVo> list = new ArrayList<>() ;
            for (DefineRoleDto defineRoleDto : defineRoleDtos){
                list.add(transferDtoToVo(defineRoleDto));
            }
            return list ;
        }
    }
}
