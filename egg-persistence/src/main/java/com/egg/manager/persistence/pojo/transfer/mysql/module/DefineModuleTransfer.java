package com.egg.manager.persistence.pojo.transfer.mysql.module;

import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.pojo.dto.mysql.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.module.DefineModuleVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.module.DefineModuleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("DefineModuleTransfer")
public class DefineModuleTransfer extends MyBaseMysqlTransfer {

    static DefineModuleVoMapstruct defineModuleVoMapstruct = DefineModuleVoMapstruct.INSTANCE ;

    public static DefineModule transferVoToEntity(DefineModuleVo vo) {
        if (vo == null) {
            return null;
        }
        DefineModule entity = defineModuleVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineModuleVo transferEntityToVo(DefineModule entity) {
        if (entity == null) {
            return null;
        }
        DefineModuleVo vo = defineModuleVoMapstruct.transferEntityToVo(entity);
        //TODO
        if (entity.getType() != null) {
            DefineModuleTypeEnum typeEnum = DefineModuleTypeEnum.doGetEnumByValue(entity.getType());
            if (typeEnum != null) {
                vo.setTypeStr(typeEnum.getLabel());
            } else {
                vo.setTypeStr("");
            }
        }
        return vo;
    }

    public static DefineModuleVo transferDtoToVo(DefineModuleDto dto) {
        if (dto == null) {
            return null;
        }
        DefineModuleVo vo = defineModuleVoMapstruct.transferDtoToVo(dto);
        //TODO
        if (dto.getType() != null) {
            DefineModuleTypeEnum typeEnum = DefineModuleTypeEnum.doGetEnumByValue(dto.getType());
            if (typeEnum != null) {
                vo.setTypeStr(typeEnum.getLabel());
            } else {
                vo.setTypeStr("");
            }
        }
        return vo;
    }

    public static List<DefineModuleVo> transferEntityToVoList(List<DefineModule> defineModules) {
        if (defineModules == null) {
            return null;
        } else {
            List<DefineModuleVo> list = new ArrayList<>();
            for (DefineModule defineModule : defineModules) {
                list.add(transferEntityToVo(defineModule));
            }
            return list;
        }
    }

    public static List<DefineModuleVo> transferDtoToVoList(List<DefineModuleDto> defineModuleDtos) {
        if (defineModuleDtos == null) {
            return null;
        } else {
            List<DefineModuleVo> list = new ArrayList<>();
            for (DefineModuleDto defineModuleDto : defineModuleDtos) {
                list.add(transferDtoToVo(defineModuleDto));
            }
            return list;
        }
    }


}
