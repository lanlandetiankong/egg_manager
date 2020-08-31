package com.egg.manager.persistence.pojo.transfer.mysql.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineDepartmentMysqlDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineDepartmentMysqlVo;

import java.util.ArrayList;
import java.util.List;

public class DefineDepartmentMysqlTransfer extends MyBaseMysqlTransfer {
    public static DefineDepartment transferVoToEntity(DefineDepartmentMysqlVo defineDepartmentVo) {
        if (defineDepartmentVo == null) {
            return null;
        }
        DefineDepartment defineDepartment = new DefineDepartment();
        defineDepartment.setFid(defineDepartmentVo.getFid());
        defineDepartment.setName(defineDepartmentVo.getName());
        defineDepartment.setParentId(defineDepartmentVo.getParentId());
        defineDepartment.setCode(defineDepartmentVo.getCode());
        defineDepartment.setLevel(defineDepartmentVo.getLevel());
        defineDepartment.setOrderNum(defineDepartmentVo.getOrderNum());
        defineDepartment.setDescription(defineDepartmentVo.getDescription());

        defineDepartment.setRemark(defineDepartmentVo.getRemark());
        defineDepartment.setState(defineDepartmentVo.getState());
        defineDepartment.setCreateTime(defineDepartmentVo.getCreateTime());
        defineDepartment.setUpdateTime(defineDepartmentVo.getUpdateTime());
        defineDepartment.setCreateUserId(defineDepartmentVo.getCreateUserId());
        defineDepartment.setLastModifyerId(defineDepartmentVo.getLastModifyerId());
        return defineDepartment;
    }

    public static DefineDepartmentMysqlVo transferEntityToVo(DefineDepartment defineDepartment) {
        if (defineDepartment == null) {
            return null;
        }
        DefineDepartmentMysqlVo defineDepartmentVo = new DefineDepartmentMysqlVo();
        defineDepartmentVo.setFid(defineDepartment.getFid());
        defineDepartmentVo.setName(defineDepartment.getName());
        defineDepartmentVo.setParentId(defineDepartment.getParentId());
        defineDepartmentVo.setCode(defineDepartment.getCode());
        defineDepartmentVo.setLevel(defineDepartment.getLevel());
        defineDepartmentVo.setOrderNum(defineDepartment.getOrderNum());
        defineDepartmentVo.setDescription(defineDepartment.getDescription());

        defineDepartmentVo.setRemark(defineDepartment.getRemark());
        defineDepartmentVo.setState(defineDepartment.getState());
        defineDepartmentVo.setCreateTime(defineDepartment.getCreateTime());
        defineDepartmentVo.setUpdateTime(defineDepartment.getUpdateTime());
        defineDepartmentVo.setCreateUserId(defineDepartment.getCreateUserId());
        defineDepartmentVo.setLastModifyerId(defineDepartment.getLastModifyerId());
        return defineDepartmentVo;
    }


    public static DefineDepartmentMysqlVo transferDtoToVo(DefineDepartmentMysqlDto defineDepartmentDto) {
        if (defineDepartmentDto == null) {
            return null;
        }
        DefineDepartmentMysqlVo defineDepartmentVo = new DefineDepartmentMysqlVo();
        defineDepartmentVo.setFid(defineDepartmentDto.getFid());
        defineDepartmentVo.setName(defineDepartmentDto.getName());
        defineDepartmentVo.setParentId(defineDepartmentDto.getParentId());
        defineDepartmentVo.setParentDepartment(DefineDepartmentMysqlTransfer.transferDtoToVo(defineDepartmentDto.getParentDepartment()));
        defineDepartmentVo.setCode(defineDepartmentDto.getCode());
        defineDepartmentVo.setLevel(defineDepartmentDto.getLevel());
        defineDepartmentVo.setOrderNum(defineDepartmentDto.getOrderNum());
        defineDepartmentVo.setDescription(defineDepartmentDto.getDescription());

        defineDepartmentVo.setRemark(defineDepartmentDto.getRemark());
        defineDepartmentVo.setState(defineDepartmentDto.getState());
        defineDepartmentVo.setCreateTime(defineDepartmentDto.getCreateTime());
        defineDepartmentVo.setUpdateTime(defineDepartmentDto.getUpdateTime());
        defineDepartmentVo.setCreateUserId(defineDepartmentDto.getCreateUserId());
        defineDepartmentVo.setLastModifyerId(defineDepartmentDto.getLastModifyerId());
        defineDepartmentVo.setCreateUser(UserAccountMysqlTransfer.transferEntityToVo(defineDepartmentDto.getCreateUser()));
        defineDepartmentVo.setLastModifyer(UserAccountMysqlTransfer.transferEntityToVo(defineDepartmentDto.getLastModifyer()));
        return defineDepartmentVo;
    }

    public static List<DefineDepartmentMysqlVo> transferEntityToVoList(List<DefineDepartment> defineDepartments) {
        if (defineDepartments == null) {
            return null;
        } else {
            List<DefineDepartmentMysqlVo> list = new ArrayList<>();
            for (DefineDepartment defineDepartment : defineDepartments) {
                list.add(transferEntityToVo(defineDepartment));
            }
            return list;
        }
    }

    public static List<DefineDepartmentMysqlVo> transferDtoToVoList(List<DefineDepartmentMysqlDto> defineDepartmentDtos) {
        if (defineDepartmentDtos == null) {
            return null;
        } else {
            List<DefineDepartmentMysqlVo> list = new ArrayList<>();
            for (DefineDepartmentMysqlDto defineDepartmentDto : defineDepartmentDtos) {
                list.add(transferDtoToVo(defineDepartmentDto));
            }
            return list;
        }
    }

}
