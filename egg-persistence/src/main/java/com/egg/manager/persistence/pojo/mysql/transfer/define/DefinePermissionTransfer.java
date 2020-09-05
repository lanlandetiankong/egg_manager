package com.egg.manager.persistence.pojo.mysql.transfer.define;

import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.define.DefinePermissionMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("definePermissionTransfer")
public class DefinePermissionTransfer extends MyBaseMysqlTransfer {

    static DefinePermissionMapstruct definePermissionVoMapstruct = DefinePermissionMapstruct.INSTANCE ;

    public static DefinePermission transferVoToEntity(DefinePermissionVo vo) {
        if (vo == null) {
            return null;
        }
        DefinePermission entity = definePermissionVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermission entity) {
        if (entity == null) {
            return null;
        }
        DefinePermissionVo vo = definePermissionVoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermissionDto dto) {
        if (dto == null) {
            return null;
        }
        DefinePermissionVo vo = definePermissionVoMapstruct.transferDtoToVo(dto);
        return vo;
    }

    //已启用entity 值回设
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














    /**
     * 类型label
     * @param value
     * @return
     */
    @Named("doGetLabelOfDefinePermissionTypeEnum")
    public String doGetLabelOfDefinePermissionTypeEnum(Integer value){
        if(value == null){
            return "";
        }
        DefinePermissionTypeEnum typeEnum = DefinePermissionTypeEnum.doGetEnumByValue(value);
        return typeEnum == null ? "" : typeEnum.getLabel() ;
    }



}
