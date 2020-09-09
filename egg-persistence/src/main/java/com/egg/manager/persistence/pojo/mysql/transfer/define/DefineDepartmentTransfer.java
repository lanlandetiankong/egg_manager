package com.egg.manager.persistence.pojo.mysql.transfer.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.define.DefineDepartmentMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineDepartmentVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("defineDepartmentTransfer")
public class DefineDepartmentTransfer extends MyBaseMysqlTransfer {
    static DefineDepartmentMapstruct defineDepartmentMapstruct = DefineDepartmentMapstruct.INSTANCE ;


    public static DefineDepartment transferVoToEntity(DefineDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        DefineDepartment entity = defineDepartmentMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineDepartmentVo transferEntityToVo(DefineDepartment entity) {
        if (entity == null) {
            return null;
        }
        DefineDepartmentVo vo = defineDepartmentMapstruct.transferEntityToVo(entity);
        return vo;
    }


    public static DefineDepartmentVo transferDtoToVo(DefineDepartmentDto dto) {
        if (dto == null) {
            return null;
        }
        DefineDepartmentVo vo = defineDepartmentMapstruct.transferDtoToVo(dto);
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
