package com.egg.manager.persistence.pojo.transfer.role;

import com.egg.manager.persistence.pojo.dto.role.RoleMenuDto;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.transfer.MyBaseTransfer;
import com.egg.manager.persistence.pojo.vo.role.RoleMenuVo;

import java.util.ArrayList;
import java.util.List;


public class RoleMenuTransfer extends MyBaseTransfer {
    public static RoleMenu transferVoToEntity(RoleMenuVo roleMenuVo) {
        if (roleMenuVo == null) {
            return null;
        }
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setFid(roleMenuVo.getFid());
        roleMenu.setDefineRoleId(roleMenuVo.getDefineRoleId());
        roleMenu.setDefineMenuId(roleMenuVo.getDefineMenuId());
        roleMenu.setType(roleMenuVo.getType());
        roleMenu.setState(roleMenuVo.getState());
        roleMenu.setRemark(roleMenuVo.getRemark());
        roleMenu.setCreateTime(roleMenuVo.getCreateTime());
        roleMenu.setUpdateTime(roleMenuVo.getUpdateTime());
        roleMenu.setCreateUserId(roleMenuVo.getCreateUserId());
        roleMenu.setLastModifyerId(roleMenuVo.getLastModifyerId());
        return roleMenu;
    }


    public static RoleMenuVo transferEntityToVo(RoleMenu roleMenu) {
        if (roleMenu == null) {
            return null;
        }
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        roleMenuVo.setFid(roleMenu.getFid());
        roleMenuVo.setDefineRoleId(roleMenu.getDefineRoleId());
        roleMenuVo.setDefineMenuId(roleMenu.getDefineMenuId());
        roleMenuVo.setType(roleMenu.getType());
        roleMenuVo.setRemark(roleMenu.getRemark());
        roleMenuVo.setState(roleMenu.getState());
        roleMenuVo.setCreateTime(roleMenu.getCreateTime());
        roleMenuVo.setUpdateTime(roleMenu.getUpdateTime());
        roleMenuVo.setCreateUserId(roleMenu.getCreateUserId());
        roleMenuVo.setLastModifyerId(roleMenu.getLastModifyerId());
        return roleMenuVo;
    }


    public static RoleMenuVo transferDtoToVo(RoleMenuDto roleMenuDto) {
        if (roleMenuDto == null) {
            return null;
        }
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        roleMenuVo.setFid(roleMenuDto.getFid());
        roleMenuVo.setDefineRoleId(roleMenuDto.getDefineRoleId());
        roleMenuVo.setDefineMenuId(roleMenuDto.getDefineMenuId());
        roleMenuVo.setType(roleMenuDto.getType());
        roleMenuVo.setRemark(roleMenuDto.getRemark());
        roleMenuVo.setState(roleMenuDto.getState());
        roleMenuVo.setCreateTime(roleMenuDto.getCreateTime());
        roleMenuVo.setUpdateTime(roleMenuDto.getUpdateTime());
        roleMenuVo.setCreateUserId(roleMenuDto.getCreateUserId());
        roleMenuVo.setLastModifyerId(roleMenuDto.getLastModifyerId());
        return roleMenuVo;
    }

    public static List<RoleMenuVo> transferEntityToVoList(List<RoleMenu> roleMenus) {
        if (roleMenus == null) {
            return null;
        } else {
            List<RoleMenuVo> list = new ArrayList<>();
            for (RoleMenu role : roleMenus) {
                list.add(transferEntityToVo(role));
            }
            return list;
        }
    }

    public static List<RoleMenuVo> transferDtoToVoList(List<RoleMenuDto> roleMenuDtos) {
        if (roleMenuDtos == null) {
            return null;
        } else {
            List<RoleMenuVo> list = new ArrayList<>();
            for (RoleMenuDto roleMenuDto : roleMenuDtos) {
                list.add(transferDtoToVo(roleMenuDto));
            }
            return list;
        }
    }
}
