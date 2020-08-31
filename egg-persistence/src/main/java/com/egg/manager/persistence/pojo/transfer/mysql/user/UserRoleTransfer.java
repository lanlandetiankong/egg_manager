package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserRoleDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserRoleMysqlVo;

import java.util.ArrayList;
import java.util.List;

public class UserRoleTransfer extends MyBaseMysqlTransfer {


    public static UserRole transferVoToEntity(UserRoleMysqlVo userRoleVo) {
        if (userRoleVo == null) {
            return null;
        }
        UserRole userRole = new UserRole();
        userRole.setFid(userRoleVo.getFid());
        userRole.setUserAccountId(userRoleVo.getUserAccountId());
        userRole.setDefineRoleId(userRoleVo.getDefineRoleId());
        userRole.setType(userRoleVo.getType());
        userRole.setState(userRoleVo.getState());
        userRole.setRemark(userRoleVo.getRemark());
        userRole.setCreateTime(userRoleVo.getCreateTime());
        userRole.setUpdateTime(userRoleVo.getUpdateTime());
        userRole.setCreateUserId(userRoleVo.getCreateUserId());
        userRole.setLastModifyerId(userRoleVo.getLastModifyerId());
        return userRole;
    }


    public static UserRoleMysqlVo transferEntityToVo(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        UserRoleMysqlVo userRoleVo = new UserRoleMysqlVo();
        userRoleVo.setFid(userRole.getFid());
        userRoleVo.setUserAccountId(userRole.getUserAccountId());
        userRoleVo.setDefineRoleId(userRole.getDefineRoleId());
        userRoleVo.setType(userRole.getType());
        userRoleVo.setRemark(userRole.getRemark());
        userRoleVo.setState(userRole.getState());
        userRoleVo.setCreateTime(userRole.getCreateTime());
        userRoleVo.setUpdateTime(userRole.getUpdateTime());
        userRoleVo.setCreateUserId(userRole.getCreateUserId());
        userRoleVo.setLastModifyerId(userRole.getLastModifyerId());
        return userRoleVo;
    }


    public static UserRoleMysqlVo transferDtoToVo(UserRoleDto userRoleDto) {
        if (userRoleDto == null) {
            return null;
        }
        UserRoleMysqlVo userRoleVo = new UserRoleMysqlVo();
        userRoleVo.setFid(userRoleDto.getFid());
        userRoleVo.setUserAccountId(userRoleDto.getUserAccountId());
        userRoleVo.setDefineRoleId(userRoleDto.getDefineRoleId());
        userRoleVo.setType(userRoleDto.getType());
        userRoleVo.setRemark(userRoleDto.getRemark());
        userRoleVo.setState(userRoleDto.getState());
        userRoleVo.setCreateTime(userRoleDto.getCreateTime());
        userRoleVo.setUpdateTime(userRoleDto.getUpdateTime());
        userRoleVo.setCreateUserId(userRoleDto.getCreateUserId());
        userRoleVo.setLastModifyerId(userRoleDto.getLastModifyerId());
        return userRoleVo;
    }

    public static List<UserRoleMysqlVo> transferEntityToVoList(List<UserRole> userRoles) {
        if (userRoles == null) {
            return null;
        } else {
            List<UserRoleMysqlVo> list = new ArrayList<>();
            for (UserRole role : userRoles) {
                list.add(transferEntityToVo(role));
            }
            return list;
        }
    }

    public static List<UserRoleMysqlVo> transferDtoToVoList(List<UserRoleDto> userRoleDtos) {
        if (userRoleDtos == null) {
            return null;
        } else {
            List<UserRoleMysqlVo> list = new ArrayList<>();
            for (UserRoleDto userRoleDto : userRoleDtos) {
                list.add(transferDtoToVo(userRoleDto));
            }
            return list;
        }
    }


}
