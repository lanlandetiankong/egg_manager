package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserTenantEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserTenantDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.EmUserTenantMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserTenantVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
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
@Named("userTenantTransfer")
public class EmUserTenantTransfer extends BaseMysqlTransfer {

    static EmUserTenantMapstruct emUserTenantMapstruct = EmUserTenantMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmUserTenantEntity transferVoToEntity(EmUserTenantVo vo) {
        if (vo == null) {
            return null;
        }
        EmUserTenantEntity entity = emUserTenantMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmUserTenantVo transferEntityToVo(EmUserTenantEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserTenantVo vo = emUserTenantMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmUserTenantVo transferDtoToVo(EmUserTenantDto dto) {
        if (dto == null) {
            return null;
        }
        EmUserTenantVo vo = emUserTenantMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmUserTenantVo> transferEntityToVoList(List<EmUserTenantEntity> userTenantEntities) {
        if (userTenantEntities == null) {
            return null;
        } else {
            List<EmUserTenantVo> list = new ArrayList<>();
            for (EmUserTenantEntity emUserTenantEntity : userTenantEntities) {
                list.add(transferEntityToVo(emUserTenantEntity));
            }
            return list;
        }
    }

    public static List<EmUserTenantVo> transferDtoToVoList(List<EmUserTenantDto> emUserTenantDtos) {
        if (emUserTenantDtos == null) {
            return null;
        } else {
            List<EmUserTenantVo> list = new ArrayList<>();
            for (EmUserTenantDto emUserTenantDto : emUserTenantDtos) {
                list.add(transferDtoToVo(emUserTenantDto));
            }
            return list;
        }
    }


}
