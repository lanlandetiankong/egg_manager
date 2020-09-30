package com.egg.manager.persistence.pojo.mysql.transfer.organization;

import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.organization.DefineTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("defineTenantTransfer")
public class DefineTenantTransfer extends BaseMysqlTransfer {
    static DefineTenantMapstruct defineTenantMapstruct = DefineTenantMapstruct.INSTANCE;
    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static DefineTenant transferVoToEntity(DefineTenantVo vo) {
        if (vo == null) {
            return null;
        }
        DefineTenant entity = defineTenantMapstruct.transferVoToEntity(vo);
        return entity;
    }
    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineTenantVo transferEntityToVo(DefineTenant entity) {
        if (entity == null) {
            return null;
        }
        DefineTenantVo vo = defineTenantMapstruct.transferEntityToVo(entity);
        return vo;
    }
    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static DefineTenantVo transferDtoToVo(DefineTenantDto dto) {
        if (dto == null) {
            return null;
        }
        DefineTenantVo vo = defineTenantMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<DefineTenantVo> transferEntityToVoList(List<DefineTenant> defineTenants) {
        if (defineTenants == null) {
            return null;
        } else {
            List<DefineTenantVo> list = new ArrayList<>();
            for (DefineTenant defineTenant : defineTenants) {
                list.add(transferEntityToVo(defineTenant));
            }
            return list;
        }
    }

    public static List<DefineTenantVo> transferDtoToVoList(List<DefineTenantDto> defineTenantDtoList) {
        if (defineTenantDtoList == null) {
            return null;
        } else {
            List<DefineTenantVo> list = new ArrayList<>();
            for (DefineTenantDto defineTenantDto : defineTenantDtoList) {
                list.add(transferDtoToVo(defineTenantDto));
            }
            return list;
        }
    }

}
