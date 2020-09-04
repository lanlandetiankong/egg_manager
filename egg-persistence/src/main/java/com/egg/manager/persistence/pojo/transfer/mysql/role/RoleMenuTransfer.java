package com.egg.manager.persistence.pojo.transfer.mysql.role;

import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.dto.mysql.role.RoleMenuDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.role.RoleMenuMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.role.RoleMenuVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("RoleMenuTransfer")
public class RoleMenuTransfer extends MyBaseMysqlTransfer {

    static RoleMenuMapstruct roleMenuVoMapstruct = RoleMenuMapstruct.INSTANCE ;

    public static RoleMenu transferVoToEntity(RoleMenuVo vo) {
        if (vo == null) {
            return null;
        }
        RoleMenu entity = roleMenuVoMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static RoleMenuVo transferEntityToVo(RoleMenu entity) {
        if (entity == null) {
            return null;
        }
        RoleMenuVo vo = roleMenuVoMapstruct.transferEntityToVo(entity);
        return vo;
    }


    public static RoleMenuVo transferDtoToVo(RoleMenuDto dto) {
        if (dto == null) {
            return null;
        }
        RoleMenuVo vo = roleMenuVoMapstruct.transferDtoToVo(dto);
        return vo;
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
