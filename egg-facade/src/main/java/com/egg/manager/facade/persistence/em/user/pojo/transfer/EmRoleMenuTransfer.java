package com.egg.manager.facade.persistence.em.user.pojo.transfer;

import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmRoleMenuEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmRoleMenuDto;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap.EmRoleMenuMapstruct;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmRoleMenuVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
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
public class EmRoleMenuTransfer extends BaseMysqlTransfer {

    static EmRoleMenuMapstruct emRoleMenuMapstruct = EmRoleMenuMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmRoleMenuEntity transferVoToEntity(EmRoleMenuVo vo) {
        if (vo == null) {
            return null;
        }
        EmRoleMenuEntity entity = emRoleMenuMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmRoleMenuVo transferEntityToVo(EmRoleMenuEntity entity) {
        if (entity == null) {
            return null;
        }
        EmRoleMenuVo vo = emRoleMenuMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmRoleMenuVo transferDtoToVo(EmRoleMenuDto dto) {
        if (dto == null) {
            return null;
        }
        EmRoleMenuVo vo = emRoleMenuMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmRoleMenuVo> transferEntityToVoList(List<EmRoleMenuEntity> roleMenuEntities) {
        if (roleMenuEntities == null) {
            return null;
        } else {
            List<EmRoleMenuVo> list = new ArrayList<>();
            for (EmRoleMenuEntity role : roleMenuEntities) {
                list.add(transferEntityToVo(role));
            }
            return list;
        }
    }

    public static List<EmRoleMenuVo> transferDtoToVoList(List<EmRoleMenuDto> emRoleMenuDtos) {
        if (emRoleMenuDtos == null) {
            return null;
        } else {
            List<EmRoleMenuVo> list = new ArrayList<>();
            for (EmRoleMenuDto emRoleMenuDto : emRoleMenuDtos) {
                list.add(transferDtoToVo(emRoleMenuDto));
            }
            return list;
        }
    }
}
