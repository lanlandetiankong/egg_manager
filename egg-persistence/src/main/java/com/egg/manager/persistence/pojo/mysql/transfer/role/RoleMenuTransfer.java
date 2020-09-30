package com.egg.manager.persistence.pojo.mysql.transfer.role;

import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.mysql.dto.role.RoleMenuDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.role.RoleMenuMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.role.RoleMenuVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("roleMenuTransfer")
public class RoleMenuTransfer extends BaseMysqlTransfer {

    static RoleMenuMapstruct roleMenuMapstruct = RoleMenuMapstruct.INSTANCE;
    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static RoleMenu transferVoToEntity(RoleMenuVo vo) {
        if (vo == null) {
            return null;
        }
        RoleMenu entity = roleMenuMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static RoleMenuVo transferEntityToVo(RoleMenu entity) {
        if (entity == null) {
            return null;
        }
        RoleMenuVo vo = roleMenuMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static RoleMenuVo transferDtoToVo(RoleMenuDto dto) {
        if (dto == null) {
            return null;
        }
        RoleMenuVo vo = roleMenuMapstruct.transferDtoToVo(dto);
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
