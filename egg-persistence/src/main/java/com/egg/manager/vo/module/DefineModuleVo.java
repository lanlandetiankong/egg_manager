package com.egg.manager.vo.module;

import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.entity.module.DefineModule;
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
    private Integer state ;
    private String remark ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;




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
        defineModule.setCreateUser(defineModuleVo.getCreateUser());
        defineModule.setLastModifyer(defineModuleVo.getLastModifyer());
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
        defineModuleVo.setCreateUser(defineModule.getCreateUser());
        defineModuleVo.setLastModifyer(defineModule.getLastModifyer());
        defineModuleVo.setRemark(defineModule.getRemark());
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
    
}
