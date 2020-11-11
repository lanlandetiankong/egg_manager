package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.persistence.em.define.db.mysql.entity.DefineModuleEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefineModuleDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.DefineModuleMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.DefineModuleVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("defineModuleTransfer")
public class DefineModuleTransfer extends BaseMysqlTransfer {

    static DefineModuleMapstruct defineModuleMapstruct = DefineModuleMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static DefineModuleEntity transferVoToEntity(DefineModuleVo vo) {
        if (vo == null) {
            return null;
        }
        DefineModuleEntity entity = defineModuleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineModuleVo transferEntityToVo(DefineModuleEntity entity) {
        if (entity == null) {
            return null;
        }
        DefineModuleVo vo = defineModuleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static DefineModuleVo transferDtoToVo(DefineModuleDto dto) {
        if (dto == null) {
            return null;
        }
        DefineModuleVo vo = defineModuleMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<DefineModuleVo> transferEntityToVoList(List<DefineModuleEntity> defineModuleEntities) {
        if (defineModuleEntities == null) {
            return null;
        } else {
            List<DefineModuleVo> list = new ArrayList<>();
            for (DefineModuleEntity defineModuleEntity : defineModuleEntities) {
                list.add(transferEntityToVo(defineModuleEntity));
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
