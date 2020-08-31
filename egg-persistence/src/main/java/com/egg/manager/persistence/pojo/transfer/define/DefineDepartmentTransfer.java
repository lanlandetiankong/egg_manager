package com.egg.manager.persistence.pojo.transfer.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.transfer.MyBaseTransfer;
import com.egg.manager.persistence.pojo.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.define.DefineDepartmentVo;

import java.util.ArrayList;
import java.util.List;

public class DefineDepartmentTransfer extends MyBaseTransfer {
    public static DefineDepartment transferVoToEntity(DefineDepartmentVo defineDepartmentVo) {
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

    public static DefineDepartmentVo transferEntityToVo(DefineDepartment defineDepartment) {
        if (defineDepartment == null) {
            return null;
        }
        DefineDepartmentVo defineDepartmentVo = new DefineDepartmentVo();
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


    public static DefineDepartmentVo transferDtoToVo(DefineDepartmentDto defineDepartmentDto) {
        if (defineDepartmentDto == null) {
            return null;
        }
        DefineDepartmentVo defineDepartmentVo = new DefineDepartmentVo();
        defineDepartmentVo.setFid(defineDepartmentDto.getFid());
        defineDepartmentVo.setName(defineDepartmentDto.getName());
        defineDepartmentVo.setParentId(defineDepartmentDto.getParentId());
        defineDepartmentVo.setParentDepartment(DefineDepartmentTransfer.transferDtoToVo(defineDepartmentDto.getParentDepartment()));
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
        defineDepartmentVo.setCreateUser(UserAccountTransfer.transferEntityToVo(defineDepartmentDto.getCreateUser()));
        defineDepartmentVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(defineDepartmentDto.getLastModifyer()));
        return defineDepartmentVo;
    }

    public static List<DefineDepartmentVo> transferEntityToVoList(List<DefineDepartment> defineDepartments) {
        if (defineDepartments == null) {
            return null;
        } else {
            List<DefineDepartmentVo> list = new ArrayList<>();
            for (DefineDepartment defineDepartment : defineDepartments) {
                list.add(transferEntityToVo(defineDepartment));
            }
            return list;
        }
    }

    public static List<DefineDepartmentVo> transferDtoToVoList(List<DefineDepartmentDto> defineDepartmentDtos) {
        if (defineDepartmentDtos == null) {
            return null;
        } else {
            List<DefineDepartmentVo> list = new ArrayList<>();
            for (DefineDepartmentDto defineDepartmentDto : defineDepartmentDtos) {
                list.add(transferDtoToVo(defineDepartmentDto));
            }
            return list;
        }
    }

}
