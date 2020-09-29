package com.egg.manager.persistence.pojo.mysql.transfer.module;

import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.module.DefineModuleMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("defineModuleTransfer")
public class DefineModuleTransfer extends BaseMysqlTransfer {

    static DefineModuleMapstruct defineModuleMapstruct = DefineModuleMapstruct.INSTANCE;

    public static DefineModule transferVoToEntity(DefineModuleVo vo) {
        if (vo == null) {
            return null;
        }
        DefineModule entity = defineModuleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineModuleVo transferEntityToVo(DefineModule entity) {
        if (entity == null) {
            return null;
        }
        DefineModuleVo vo = defineModuleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static DefineModuleVo transferDtoToVo(DefineModuleDto dto) {
        if (dto == null) {
            return null;
        }
        DefineModuleVo vo = defineModuleMapstruct.transferDtoToVo(dto);
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
