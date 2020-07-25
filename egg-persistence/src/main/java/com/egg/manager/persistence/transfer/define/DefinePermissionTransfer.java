package com.egg.manager.persistence.transfer.define;

import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.persistence.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.vo.define.DefinePermissionVo;
import com.egg.manager.persistence.vo.user.UserAccountVo;

import java.util.ArrayList;
import java.util.List;


public class DefinePermissionTransfer {
    public static DefinePermission transferVoToEntity(DefinePermissionVo definePermissionVo,DefinePermission definePermission) {
        if(definePermissionVo == null){
            return null ;
        }
        definePermission = definePermission != null ? definePermission : new DefinePermission() ;
        definePermission.setFid(definePermissionVo.getFid());
        definePermission.setName(definePermissionVo.getName());
        definePermission.setCode(definePermissionVo.getCode());
        definePermission.setEnsure(definePermissionVo.isEnsure() ? (short)1 : (short)0);
        definePermission.setType(definePermissionVo.getType());
        definePermission.setRemark(definePermissionVo.getRemark());
        definePermission.setState(definePermissionVo.getState());
        definePermission.setCreateTime(definePermissionVo.getCreateTime());
        definePermission.setUpdateTime(definePermissionVo.getUpdateTime());
        definePermission.setCreateUserId(definePermissionVo.getCreateUserId());
        definePermission.setLastModifyerId(definePermissionVo.getLastModifyerId());
        return definePermission ;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermission definePermission) {
        if(definePermission == null){
            return null ;
        }
        DefinePermissionVo definePermissionVo = new DefinePermissionVo() ;
        definePermissionVo.setFid(definePermission.getFid());
        definePermissionVo.setName(definePermission.getName());
        definePermissionVo.setCode(definePermission.getCode());
        definePermissionVo.setEnsure(SwitchStateEnum.Open.getValue().equals(definePermission.getEnsure()));
        definePermissionVo.setEnsureStr(SwitchStateEnum.dealGetNameByVal(definePermission.getEnsure()));
        definePermissionVo.setType(definePermission.getType());
        if(definePermission.getType() != null){
            DefinePermissionTypeEnum typeEnum = DefinePermissionTypeEnum.doGetEnumByValue(definePermission.getType());
            if(typeEnum != null){
                definePermissionVo.setTypeStr(typeEnum.getLabel());
            }   else {
                definePermissionVo.setTypeStr("");
            }
        }
        definePermissionVo.setRemark(definePermission.getRemark());
        definePermissionVo.setState(definePermission.getState());
        definePermissionVo.setCreateTime(definePermission.getCreateTime());
        definePermissionVo.setUpdateTime(definePermission.getUpdateTime());
        definePermissionVo.setCreateUserId(definePermission.getCreateUserId());
        definePermissionVo.setLastModifyerId(definePermission.getLastModifyerId());

        return definePermissionVo ;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermissionDto definePermissionDto) {
        if(definePermissionDto == null){
            return null ;
        }
        DefinePermissionVo definePermissionVo = new DefinePermissionVo() ;
        definePermissionVo.setFid(definePermissionDto.getFid());
        definePermissionVo.setName(definePermissionDto.getName());
        definePermissionVo.setCode(definePermissionDto.getCode());
        definePermissionVo.setEnsure(SwitchStateEnum.Open.getValue().equals(definePermissionDto.getEnsure()));
        definePermissionVo.setEnsureStr(SwitchStateEnum.dealGetNameByVal(definePermissionDto.getEnsure()));
        definePermissionVo.setType(definePermissionDto.getType());
        if(definePermissionDto.getType() != null){
            DefinePermissionTypeEnum typeEnum = DefinePermissionTypeEnum.doGetEnumByValue(definePermissionDto.getType());
            if(typeEnum != null){
                definePermissionVo.setTypeStr(typeEnum.getLabel());
            }   else {
                definePermissionVo.setTypeStr("");
            }
        }
        definePermissionVo.setRemark(definePermissionDto.getRemark());
        definePermissionVo.setState(definePermissionDto.getState());
        definePermissionVo.setCreateTime(definePermissionDto.getCreateTime());
        definePermissionVo.setUpdateTime(definePermissionDto.getUpdateTime());
        definePermissionVo.setCreateUserId(definePermissionDto.getCreateUserId());
        definePermissionVo.setLastModifyerId(definePermissionDto.getLastModifyerId());
        definePermissionVo.setCreateUser(UserAccountTransfer.transferEntityToVo(definePermissionDto.getCreateUser()));
        definePermissionVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(definePermissionDto.getLastModifyer()));
        return definePermissionVo ;
    }

    //已启用entity 值回设
    public static void handleSwitchOpenChangeFieldChange(DefinePermission updateEntity,DefinePermission oldEntity){
        if(updateEntity != null && oldEntity != null){
            //避免前端可能篡改了数据！
            updateEntity.setEnsure(SwitchStateEnum.Open.getValue());
            updateEntity.setCode(oldEntity.getCode());
        }
    }

    public static List<DefinePermissionVo> transferEntityToVoList(List<DefinePermission> definePermissions){
        if(definePermissions == null){
            return null ;
        }   else {
            List<DefinePermissionVo> list = new ArrayList<>() ;
            for (DefinePermission definePermission : definePermissions){
                list.add(transferEntityToVo(definePermission));
            }
            return list ;
        }
    }

    public static List<DefinePermissionVo> transferDtoToVoList(List<DefinePermissionDto> definePermissionDtos){
        if(definePermissionDtos == null){
            return null ;
        }   else {
            List<DefinePermissionVo> list = new ArrayList<>() ;
            for (DefinePermissionDto definePermissionDto : definePermissionDtos){
                list.add(transferEntityToVo(definePermissionDto));
            }
            return list ;
        }
    }
}
