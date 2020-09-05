package com.egg.manager.persistence.pojo.mysql.transfer.module;

import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.module.DefineModuleMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("defineModuleTransfer")
public class DefineModuleTransfer extends MyBaseMysqlTransfer {

    static DefineModuleMapstruct defineModuleVoMapstruct = DefineModuleMapstruct.INSTANCE ;

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
        return vo;
    }

    public static DefineModuleVo transferDtoToVo(DefineModuleDto dto) {
        if (dto == null) {
            return null;
        }
        DefineModuleVo vo = defineModuleVoMapstruct.transferDtoToVo(dto);
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


    @Named("handleDefineModuleTypeGetLabel")
    public String handleDefineModuleTypeGetLabel(Integer type){
        if (type != null) {
            DefineModuleTypeEnum typeEnum = DefineModuleTypeEnum.doGetEnumByValue(type);
            if (typeEnum != null) {
                return typeEnum.getLabel();
            }
        }
        return "" ;
    }

}
