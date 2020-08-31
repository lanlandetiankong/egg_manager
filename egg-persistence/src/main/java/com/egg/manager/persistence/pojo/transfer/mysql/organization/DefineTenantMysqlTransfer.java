package com.egg.manager.persistence.pojo.transfer.mysql.organization;

import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.dto.mysql.organization.DefineTenantMysqlDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantMysqlVo;

import java.util.ArrayList;
import java.util.List;


public class DefineTenantMysqlTransfer extends MyBaseMysqlTransfer {

    public static DefineTenant transferVoToEntity(DefineTenantMysqlVo defineTenantVo) {
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

    public static DefineTenantMysqlVo transferEntityToVo(DefineTenant defineTenant) {
        if (defineTenant == null) {
            return null;
        }
        DefineTenantMysqlVo defineTenantVo = new DefineTenantMysqlVo();
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

    public static DefineTenantMysqlVo transferDtoToVo(DefineTenantMysqlDto defineTenantDto) {
        if (defineTenantDto == null) {
            return null;
        }
        DefineTenantMysqlVo defineTenantVo = new DefineTenantMysqlVo();
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

    public static List<DefineTenantMysqlVo> transferEntityToVoList(List<DefineTenant> defineTenants) {
        if (defineTenants == null) {
            return null;
        } else {
            List<DefineTenantMysqlVo> list = new ArrayList<>();
            for (DefineTenant defineTenant : defineTenants) {
                list.add(transferEntityToVo(defineTenant));
            }
            return list;
        }
    }

    public static List<DefineTenantMysqlVo> transferDtoToVoList(List<DefineTenantMysqlDto> defineTenantDtoList) {
        if (defineTenantDtoList == null) {
            return null;
        } else {
            List<DefineTenantMysqlVo> list = new ArrayList<>();
            for (DefineTenantMysqlDto defineTenantDto : defineTenantDtoList) {
                list.add(transferDtoToVo(defineTenantDto));
            }
            return list;
        }
    }

}
