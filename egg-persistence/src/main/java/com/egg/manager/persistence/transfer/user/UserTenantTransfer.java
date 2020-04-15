package com.egg.manager.persistence.transfer.user;

import com.egg.manager.persistence.dto.user.UserTenantDto;
import com.egg.manager.persistence.entity.user.UserTenant;
import com.egg.manager.persistence.vo.user.UserTenantVo;

import java.util.ArrayList;
import java.util.List;

public class UserTenantTransfer {
    public static UserTenant transferVoToEntity(UserTenantVo userTenantVo) {
        if(userTenantVo == null){
            return null ;
        }
        UserTenant userTenant = new UserTenant() ;
        userTenant.setFid(userTenantVo.getFid());
        userTenant.setUserAccountId(userTenantVo.getUserAccountId());
        userTenant.setDefineTenantId(userTenantVo.getDefineTenantId());
        userTenant.setType(userTenantVo.getType());
        userTenant.setIsManager(userTenantVo.getIsManager());
        userTenant.setState(userTenantVo.getState());
        userTenant.setRemark(userTenantVo.getRemark());
        userTenant.setCreateTime(userTenantVo.getCreateTime());
        userTenant.setUpdateTime(userTenantVo.getUpdateTime());
        userTenant.setCreateUserId(userTenantVo.getCreateUserId());
        userTenant.setLastModifyerId(userTenantVo.getLastModifyerId());
        return userTenant ;
    }


    public static UserTenantVo transferEntityToVo(UserTenant userTenant) {
        if(userTenant == null){
            return null ;
        }
        UserTenantVo userTenantVo = new UserTenantVo() ;
        userTenantVo.setFid(userTenant.getFid());
        userTenantVo.setUserAccountId(userTenant.getUserAccountId());
        userTenantVo.setDefineTenantId(userTenant.getDefineTenantId());
        userTenantVo.setType(userTenant.getType());
        userTenantVo.setIsManager(userTenant.getIsManager());
        userTenantVo.setRemark(userTenant.getRemark());
        userTenantVo.setState(userTenant.getState());
        userTenantVo.setCreateTime(userTenant.getCreateTime());
        userTenantVo.setUpdateTime(userTenant.getUpdateTime());
        userTenantVo.setCreateUserId(userTenant.getCreateUserId());
        userTenantVo.setLastModifyerId(userTenant.getLastModifyerId());
        return userTenantVo ;
    }


    public static UserTenantVo transferDtoToVo(UserTenantDto userTenantDto) {
        if(userTenantDto == null){
            return null ;
        }
        UserTenantVo userTenantVo = new UserTenantVo() ;
        userTenantVo.setFid(userTenantDto.getFid());
        userTenantVo.setUserAccountId(userTenantDto.getUserAccountId());
        userTenantVo.setDefineTenantId(userTenantDto.getDefineTenantId());
        userTenantVo.setType(userTenantDto.getType());
        userTenantVo.setIsManager(userTenantDto.getIsManager());
        userTenantVo.setRemark(userTenantDto.getRemark());
        userTenantVo.setState(userTenantDto.getState());
        userTenantVo.setCreateTime(userTenantDto.getCreateTime());
        userTenantVo.setUpdateTime(userTenantDto.getUpdateTime());
        userTenantVo.setCreateUserId(userTenantDto.getCreateUserId());
        userTenantVo.setLastModifyerId(userTenantDto.getLastModifyerId());
        return userTenantVo ;
    }

    public static List<UserTenantVo> transferEntityToVoList(List<UserTenant> userTenants){
        if(userTenants == null){
            return null ;
        }   else {
            List<UserTenantVo> list = new ArrayList<>() ;
            for (UserTenant userTenant : userTenants){
                list.add(transferEntityToVo(userTenant));
            }
            return list ;
        }
    }

    public static List<UserTenantVo> transferDtoToVoList(List<UserTenantDto> userTenantDtos){
        if(userTenantDtos == null){
            return null ;
        }   else {
            List<UserTenantVo> list = new ArrayList<>() ;
            for (UserTenantDto userTenantDto : userTenantDtos){
                list.add(transferDtoToVo(userTenantDto));
            }
            return list ;
        }
    }


}
