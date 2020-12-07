package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineTenantEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.EmDefineTenantMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineTenantVo;
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
@Named("defineTenantTransfer")
public class EmDefineTenantTransfer extends BaseMysqlTransfer {
    static EmDefineTenantMapstruct emDefineTenantMapstruct = EmDefineTenantMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmDefineTenantEntity transferVoToEntity(EmDefineTenantVo vo) {
        if (vo == null) {
            return null;
        }
        EmDefineTenantEntity entity = emDefineTenantMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmDefineTenantVo transferEntityToVo(EmDefineTenantEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefineTenantVo vo = emDefineTenantMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmDefineTenantVo transferDtoToVo(EmDefineTenantDto dto) {
        if (dto == null) {
            return null;
        }
        EmDefineTenantVo vo = emDefineTenantMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmDefineTenantVo> transferEntityToVoList(List<EmDefineTenantEntity> defineTenantEntities) {
        if (defineTenantEntities == null) {
            return null;
        } else {
            List<EmDefineTenantVo> list = new ArrayList<>();
            for (EmDefineTenantEntity emDefineTenantEntity : defineTenantEntities) {
                list.add(transferEntityToVo(emDefineTenantEntity));
            }
            return list;
        }
    }

    public static List<EmDefineTenantVo> transferDtoToVoList(List<EmDefineTenantDto> emDefineTenantDtoList) {
        if (emDefineTenantDtoList == null) {
            return null;
        } else {
            List<EmDefineTenantVo> list = new ArrayList<>();
            for (EmDefineTenantDto emDefineTenantDto : emDefineTenantDtoList) {
                list.add(transferDtoToVo(emDefineTenantDto));
            }
            return list;
        }
    }

}
