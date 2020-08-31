package com.egg.manager.persistence.pojo.transfer.mysql.role;

import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.dto.mysql.role.RoleMenuDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.role.RoleMenuMysqlVo;

import java.util.ArrayList;
import java.util.List;


public class RoleMenuTransfer extends MyBaseMysqlTransfer {
    public static RoleMenu transferVoToEntity(RoleMenuMysqlVo roleMenuVo) {
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


    public static RoleMenuMysqlVo transferEntityToVo(RoleMenu roleMenu) {
        if (roleMenu == null) {
            return null;
        }
        RoleMenuMysqlVo roleMenuVo = new RoleMenuMysqlVo();
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


    public static RoleMenuMysqlVo transferDtoToVo(RoleMenuDto roleMenuDto) {
        if (roleMenuDto == null) {
            return null;
        }
        RoleMenuMysqlVo roleMenuVo = new RoleMenuMysqlVo();
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

    public static List<RoleMenuMysqlVo> transferEntityToVoList(List<RoleMenu> roleMenus) {
        if (roleMenus == null) {
            return null;
        } else {
            List<RoleMenuMysqlVo> list = new ArrayList<>();
            for (RoleMenu role : roleMenus) {
                list.add(transferEntityToVo(role));
            }
            return list;
        }
    }

    public static List<RoleMenuMysqlVo> transferDtoToVoList(List<RoleMenuDto> roleMenuDtos) {
        if (roleMenuDtos == null) {
            return null;
        } else {
            List<RoleMenuMysqlVo> list = new ArrayList<>();
            for (RoleMenuDto roleMenuDto : roleMenuDtos) {
                list.add(transferDtoToVo(roleMenuDto));
            }
            return list;
        }
    }
}
