package com.egg.manager.persistence.pojo.mysql.transfer.organization;

import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.organization.DefineTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("DefineTenantTransfer")
public class DefineTenantTransfer extends MyBaseMysqlTransfer {
    static DefineTenantMapstruct defineTenantVoMapstruct = DefineTenantMapstruct.INSTANCE ;

    public static DefineTenant transferVoToEntity(DefineTenantVo vo) {
        if (vo == null) {
            return null;
        }
        DefineTenant entity = defineTenantVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineTenantVo transferEntityToVo(DefineTenant entity) {
        if (entity == null) {
            return null;
        }
        DefineTenantVo vo = defineTenantVoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static DefineTenantVo transferDtoToVo(DefineTenantDto dto) {
        if (dto == null) {
            return null;
        }
        DefineTenantVo vo = defineTenantVoMapstruct.transferDtoToVo(dto);
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
