package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserTenantDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserTenantMysqlVo;

import java.util.ArrayList;
import java.util.List;

public class UserTenantTransfer extends MyBaseMysqlTransfer {
    public static UserTenant transferVoToEntity(UserTenantMysqlVo userTenantVo) {
        if (userTenantVo == null) {
            return null;
        }
        UserTenant userTenant = new UserTenant();
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
        return userTenant;
    }


    public static UserTenantMysqlVo transferEntityToVo(UserTenant userTenant) {
        if (userTenant == null) {
            return null;
        }
        UserTenantMysqlVo userTenantVo = new UserTenantMysqlVo();
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
        return userTenantVo;
    }


    public static UserTenantMysqlVo transferDtoToVo(UserTenantDto userTenantDto) {
        if (userTenantDto == null) {
            return null;
        }
        UserTenantMysqlVo userTenantVo = new UserTenantMysqlVo();
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
        return userTenantVo;
    }

    public static List<UserTenantMysqlVo> transferEntityToVoList(List<UserTenant> userTenants) {
        if (userTenants == null) {
            return null;
        } else {
            List<UserTenantMysqlVo> list = new ArrayList<>();
            for (UserTenant userTenant : userTenants) {
                list.add(transferEntityToVo(userTenant));
            }
            return list;
        }
    }

    public static List<UserTenantMysqlVo> transferDtoToVoList(List<UserTenantDto> userTenantDtos) {
        if (userTenantDtos == null) {
            return null;
        } else {
            List<UserTenantMysqlVo> list = new ArrayList<>();
            for (UserTenantDto userTenantDto : userTenantDtos) {
                list.add(transferDtoToVo(userTenantDto));
            }
            return list;
        }
    }


}
