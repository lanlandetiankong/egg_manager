package com.egg.manager.persistence.pojo.transfer.mysql.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.define.DefineDepartmentMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineDepartmentVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("DefineDepartmentTransfer")
public class DefineDepartmentTransfer extends MyBaseMysqlTransfer {
    static DefineDepartmentMapstruct defineDepartmentVoMapstruct = DefineDepartmentMapstruct.INSTANCE ;


    public static DefineDepartment transferVoToEntity(DefineDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        DefineDepartment entity = defineDepartmentVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineDepartmentVo transferEntityToVo(DefineDepartment entity) {
        if (entity == null) {
            return null;
        }
        DefineDepartmentVo vo = defineDepartmentVoMapstruct.transferEntityToVo(entity);
        return vo;
    }


    public static DefineDepartmentVo transferDtoToVo(DefineDepartmentDto dto) {
        if (dto == null) {
            return null;
        }
        DefineDepartmentVo vo = defineDepartmentVoMapstruct.transferDtoToVo(dto);
        return vo;
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
