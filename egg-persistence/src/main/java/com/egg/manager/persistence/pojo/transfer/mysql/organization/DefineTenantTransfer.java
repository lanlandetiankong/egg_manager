package com.egg.manager.persistence.pojo.transfer.mysql.organization;

import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.dto.mysql.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("DefineTenantTransfer")
public class DefineTenantTransfer extends MyBaseMysqlTransfer {

    public static DefineTenant transferVoToEntity(DefineTenantVo defineTenantVo) {
        if (defineTenantVo == null) {
            return null;
        }
        DefineTenant defineTenant = new DefineTenant();
        defineTenant.setFid(defineTenantVo.getFid());
        defineTenant.setName(defineTenantVo.getName());
        defineTenant.setCode(defineTenantVo.getCode());
        defineTenant.setDbCode(defineTenantVo.getDbCode());
        defineTenant.setRemark(defineTenantVo.getRemark());
        defineTenant.setState(defineTenantVo.getState());
        defineTenant.setCreateTime(defineTenantVo.getCreateTime());
        defineTenant.setUpdateTime(defineTenantVo.getUpdateTime());
        defineTenant.setCreateUserId(defineTenantVo.getCreateUserId());
        defineTenant.setLastModifyerId(defineTenantVo.getLastModifyerId());
        return defineTenant;
    }

    public static DefineTenantVo transferEntityToVo(DefineTenant defineTenant) {
        if (defineTenant == null) {
            return null;
        }
        DefineTenantVo defineTenantVo = new DefineTenantVo();
        defineTenantVo.setFid(defineTenant.getFid());
        defineTenantVo.setName(defineTenant.getName());
        defineTenantVo.setCode(defineTenant.getCode());
        defineTenantVo.setDbCode(defineTenant.getDbCode());
        defineTenantVo.setRemark(defineTenant.getRemark());
        defineTenantVo.setState(defineTenant.getState());
        defineTenantVo.setCreateTime(defineTenant.getCreateTime());
        defineTenantVo.setUpdateTime(defineTenant.getUpdateTime());
        defineTenantVo.setCreateUserId(defineTenant.getCreateUserId());
        defineTenantVo.setLastModifyerId(defineTenant.getLastModifyerId());
        return defineTenantVo;
    }

    public static DefineTenantVo transferDtoToVo(DefineTenantDto defineTenantDto) {
        if (defineTenantDto == null) {
            return null;
        }
        DefineTenantVo defineTenantVo = new DefineTenantVo();
        defineTenantVo.setFid(defineTenantDto.getFid());
        defineTenantVo.setName(defineTenantDto.getName());
        defineTenantVo.setCode(defineTenantDto.getCode());
        defineTenantVo.setDbCode(defineTenantDto.getDbCode());
        defineTenantVo.setRemark(defineTenantDto.getRemark());
        defineTenantVo.setState(defineTenantDto.getState());
        defineTenantVo.setCreateTime(defineTenantDto.getCreateTime());
        defineTenantVo.setUpdateTime(defineTenantDto.getUpdateTime());
        defineTenantVo.setCreateUserId(defineTenantDto.getCreateUserId());
        defineTenantVo.setLastModifyerId(defineTenantDto.getLastModifyerId());
        return defineTenantVo;
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
