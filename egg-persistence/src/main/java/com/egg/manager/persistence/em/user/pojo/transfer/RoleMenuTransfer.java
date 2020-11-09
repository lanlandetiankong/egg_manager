package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.db.mysql.entity.RoleMenuEntity;
import com.egg.manager.persistence.em.user.pojo.dto.RoleMenuDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.RoleMenuMapstruct;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.RoleMenuVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("roleMenuTransfer")
public class RoleMenuTransfer extends BaseMysqlTransfer {

    static RoleMenuMapstruct roleMenuMapstruct = RoleMenuMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static RoleMenuEntity transferVoToEntity(RoleMenuVo vo) {
        if (vo == null) {
            return null;
        }
        RoleMenuEntity entity = roleMenuMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static RoleMenuVo transferEntityToVo(RoleMenuEntity entity) {
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

    public static List<RoleMenuVo> transferEntityToVoList(List<RoleMenuEntity> roleMenuEntities) {
        if (roleMenuEntities == null) {
            return null;
        } else {
            List<RoleMenuVo> list = new ArrayList<>();
            for (RoleMenuEntity role : roleMenuEntities) {
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
