package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermission;
import com.egg.manager.persistence.em.define.pojo.dto.DefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.DefinePermissionMapstruct;
import com.egg.manager.persistence.expand.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefinePermissionVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("definePermissionTransfer")
public class DefinePermissionTransfer extends BaseMysqlTransfer {

    static DefinePermissionMapstruct definePermissionMapstruct = DefinePermissionMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static DefinePermission transferVoToEntity(DefinePermissionVo vo) {
        if (vo == null) {
            return null;
        }
        DefinePermission entity = definePermissionMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermission entity) {
        if (entity == null) {
            return null;
        }
        DefinePermissionVo vo = definePermissionMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermissionDto dto) {
        if (dto == null) {
            return null;
        }
        DefinePermissionVo vo = definePermissionMapstruct.transferDtoToVo(dto);
        return vo;
    }

    /**
     * 已启用entity 值回设
     * @param updateEntity
     * @param oldEntity
     */
    public static void handleSwitchOpenChangeFieldChange(DefinePermission updateEntity, DefinePermission oldEntity) {
        if (updateEntity != null && oldEntity != null) {
            //避免前端可能篡改了数据！
            updateEntity.setEnsure(SwitchStateEnum.Open.getValue());
            updateEntity.setCode(oldEntity.getCode());
        }
    }

    public static List<DefinePermissionVo> transferEntityToVoList(List<DefinePermission> definePermissions) {
        if (definePermissions == null) {
            return null;
        } else {
            List<DefinePermissionVo> list = new ArrayList<>();
            for (DefinePermission definePermission : definePermissions) {
                list.add(transferEntityToVo(definePermission));
            }
            return list;
        }
    }

    public static List<DefinePermissionVo> transferDtoToVoList(List<DefinePermissionDto> definePermissionDtos) {
        if (definePermissionDtos == null) {
            return null;
        } else {
            List<DefinePermissionVo> list = new ArrayList<>();
            for (DefinePermissionDto definePermissionDto : definePermissionDtos) {
                list.add(transferEntityToVo(definePermissionDto));
            }
            return list;
        }
    }


}
