package com.egg.manager.persistence.vo.module;

import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
import com.egg.manager.persistence.dto.module.DefineModuleDto;
import com.egg.manager.persistence.entity.module.DefineModule;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineModuleVo {
    private String fid ;

    private String code ;
    private String name ;
    private String iconVal ;
    private String styleVal ;
    private Integer typeVal;
    private String typeStr;

    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;






    public static DefineModule transferVoToEntity(DefineModuleVo defineModuleVo) {
        if(defineModuleVo == null){
            return null ;
        }
        DefineModule defineModule = new DefineModule() ;
        defineModule.setFid(defineModuleVo.getFid());
        defineModule.setName(defineModuleVo.getName());
        defineModule.setCode(defineModuleVo.getCode());
        defineModule.setIcon(defineModuleVo.getIconVal());
        defineModule.setStyle(defineModuleVo.getStyleVal());
        defineModule.setType(defineModuleVo.getTypeVal());
        defineModule.setState(defineModuleVo.getState());
        defineModule.setCreateTime(defineModuleVo.getCreateTime());
        defineModule.setUpdateTime(defineModuleVo.getUpdateTime());
        defineModule.setCreateUserId(defineModuleVo.getCreateUserId());
        defineModule.setLastModifyerId(defineModuleVo.getLastModifyerId());
        defineModule.setRemark(defineModuleVo.getRemark());
        return defineModule ;
    }

    public static DefineModuleVo transferEntityToVo(DefineModule defineModule) {
        if(defineModule == null){
            return null ;
        }
        DefineModuleVo defineModuleVo = new DefineModuleVo() ;
        defineModuleVo.setFid(defineModule.getFid());
        defineModuleVo.setName(defineModule.getName());
        defineModuleVo.setCode(defineModule.getCode());
        defineModuleVo.setIconVal(defineModule.getIcon());
        defineModuleVo.setStyleVal(defineModule.getStyle());
        defineModuleVo.setTypeVal(defineModule.getType());
        if(defineModule.getType() != null){
            DefineModuleTypeEnum typeEnum = DefineModuleTypeEnum.doGetEnumByValue(defineModule.getType());
            if(typeEnum != null){
                defineModuleVo.setTypeStr(typeEnum.getLabel());
            }   else {
                defineModuleVo.setTypeStr("");
            }
        }
        defineModuleVo.setState(defineModule.getState());
        defineModuleVo.setCreateTime(defineModule.getCreateTime());
        defineModuleVo.setUpdateTime(defineModule.getUpdateTime());
        defineModuleVo.setCreateUserId(defineModule.getCreateUserId());
        defineModuleVo.setLastModifyerId(defineModule.getLastModifyerId());
        defineModuleVo.setRemark(defineModule.getRemark());
        return defineModuleVo ;
    }

    public static DefineModuleVo transferDtoToVo(DefineModuleDto defineModuleDto) {
        if(defineModuleDto == null){
            return null ;
        }
        DefineModuleVo defineModuleVo = new DefineModuleVo() ;
        defineModuleVo.setFid(defineModuleDto.getFid());
        defineModuleVo.setName(defineModuleDto.getName());
        defineModuleVo.setCode(defineModuleDto.getCode());
        defineModuleVo.setIconVal(defineModuleDto.getIcon());
        defineModuleVo.setStyleVal(defineModuleDto.getStyle());
        defineModuleVo.setTypeVal(defineModuleDto.getType());
        if(defineModuleDto.getType() != null){
            DefineModuleTypeEnum typeEnum = DefineModuleTypeEnum.doGetEnumByValue(defineModuleDto.getType());
            if(typeEnum != null){
                defineModuleVo.setTypeStr(typeEnum.getLabel());
            }   else {
                defineModuleVo.setTypeStr("");
            }
        }
        defineModuleVo.setState(defineModuleDto.getState());
        defineModuleVo.setCreateTime(defineModuleDto.getCreateTime());
        defineModuleVo.setUpdateTime(defineModuleDto.getUpdateTime());
        defineModuleVo.setCreateUserId(defineModuleDto.getCreateUserId());
        defineModuleVo.setLastModifyerId(defineModuleDto.getLastModifyerId());
        defineModuleVo.setRemark(defineModuleDto.getRemark());
        return defineModuleVo ;
    }

    public static List<DefineModuleVo> transferEntityToVoList(List<DefineModule> defineModules){
        if(defineModules == null){
            return null ;
        }   else {
            List<DefineModuleVo> list = new ArrayList<>() ;
            for (DefineModule defineModule : defineModules){
                list.add(transferEntityToVo(defineModule));
            }
            return list ;
        }
    }

    public static List<DefineModuleVo> transferDtoToVoList(List<DefineModuleDto> defineModuleDtos){
        if(defineModuleDtos == null){
            return null ;
        }   else {
            List<DefineModuleVo> list = new ArrayList<>() ;
            for (DefineModuleDto defineModuleDto : defineModuleDtos){
                list.add(transferDtoToVo(defineModuleDto));
            }
            return list ;
        }
    }
    
}
