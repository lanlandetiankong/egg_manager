package com.egg.manager.persistence.pojo.transfer.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.dto.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.transfer.MyBaseTransfer;
import com.egg.manager.persistence.pojo.vo.user.UserDepartmentVo;

import java.util.ArrayList;
import java.util.List;

public class UserDepartmentTransfer extends MyBaseTransfer {
    public static UserDepartment transferVoToEntity(UserDepartmentVo userTenantVo) {
        if (userTenantVo == null) {
            return null;
        }
        UserDepartment UserDepartment = new UserDepartment();
        UserDepartment.setFid(userTenantVo.getFid());
        UserDepartment.setUserAccountId(userTenantVo.getUserAccountId());
        UserDepartment.setDefineDepartmentId(userTenantVo.getDefineDepartmentId());
        UserDepartment.setType(userTenantVo.getType());
        UserDepartment.setIsManager(userTenantVo.getIsManager());
        UserDepartment.setState(userTenantVo.getState());
        UserDepartment.setRemark(userTenantVo.getRemark());
        UserDepartment.setCreateTime(userTenantVo.getCreateTime());
        UserDepartment.setUpdateTime(userTenantVo.getUpdateTime());
        UserDepartment.setCreateUserId(userTenantVo.getCreateUserId());
        UserDepartment.setLastModifyerId(userTenantVo.getLastModifyerId());
        return UserDepartment;
    }


    public static UserDepartmentVo transferEntityToVo(UserDepartment UserDepartment) {
        if (UserDepartment == null) {
            return null;
        }
        UserDepartmentVo userTenantVo = new UserDepartmentVo();
        userTenantVo.setFid(UserDepartment.getFid());
        userTenantVo.setUserAccountId(UserDepartment.getUserAccountId());
        userTenantVo.setDefineDepartmentId(UserDepartment.getDefineDepartmentId());
        userTenantVo.setType(UserDepartment.getType());
        userTenantVo.setIsManager(UserDepartment.getIsManager());
        userTenantVo.setRemark(UserDepartment.getRemark());
        userTenantVo.setState(UserDepartment.getState());
        userTenantVo.setCreateTime(UserDepartment.getCreateTime());
        userTenantVo.setUpdateTime(UserDepartment.getUpdateTime());
        userTenantVo.setCreateUserId(UserDepartment.getCreateUserId());
        userTenantVo.setLastModifyerId(UserDepartment.getLastModifyerId());
        return userTenantVo;
    }


    public static UserDepartmentVo transferDtoToVo(UserDepartmentDto userTenantDto) {
        if (userTenantDto == null) {
            return null;
        }
        UserDepartmentVo userTenantVo = new UserDepartmentVo();
        userTenantVo.setFid(userTenantDto.getFid());
        userTenantVo.setUserAccountId(userTenantDto.getUserAccountId());
        userTenantVo.setDefineDepartmentId(userTenantDto.getDefineDepartmentId());
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

    public static List<UserDepartmentVo> transferEntityToVoList(List<UserDepartment> userTenants) {
        if (userTenants == null) {
            return null;
        } else {
            List<UserDepartmentVo> list = new ArrayList<>();
            for (UserDepartment UserDepartment : userTenants) {
                list.add(transferEntityToVo(UserDepartment));
            }
            return list;
        }
    }

    public static List<UserDepartmentVo> transferDtoToVoList(List<UserDepartmentDto> userTenantDtos) {
        if (userTenantDtos == null) {
            return null;
        } else {
            List<UserDepartmentVo> list = new ArrayList<>();
            for (UserDepartmentDto userTenantDto : userTenantDtos) {
                list.add(transferDtoToVo(userTenantDto));
            }
            return list;
        }
    }


}
