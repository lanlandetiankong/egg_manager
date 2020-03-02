package com.egg.manager.vo.define;

import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.entity.define.DefinePermission;
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
public class DefinePermissionVo {
    private String fid ;
    private String name ;
    private String code ;
    private Integer type;
    private String typeStr ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;

    private String createUser ;
    private String lastModifyer;
    private String remark ;










    public static DefinePermission transferVoToEntity(DefinePermissionVo definePermissionVo) {
        if(definePermissionVo == null){
            return null ;
        }
        DefinePermission definePermission = new DefinePermission() ;
        definePermission.setFid(definePermissionVo.getFid());
        definePermission.setName(definePermissionVo.getName());
        definePermission.setCode(definePermissionVo.getCode());
        definePermission.setType(definePermissionVo.getType());
        definePermission.setState(definePermissionVo.getState());
        definePermission.setCreateTime(definePermissionVo.getCreateTime());
        definePermission.setUpdateTime(definePermissionVo.getUpdateTime());
        definePermission.setCreateUser(definePermissionVo.getCreateUser());
        definePermission.setLastModifyer(definePermissionVo.getLastModifyer());
        definePermission.setRemark(definePermissionVo.getRemark());
        return definePermission ;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermission definePermission) {
        if(definePermission == null){
            return null ;
        }
        DefinePermissionVo definePermissionVo = new DefinePermissionVo() ;
        definePermissionVo.setFid(definePermission.getFid());
        definePermissionVo.setName(definePermission.getName());
        definePermissionVo.setCode(definePermission.getCode());
        definePermissionVo.setType(definePermission.getType());
        if(definePermission.getType() != null){
            DefinePermissionTypeEnum typeEnum = DefinePermissionTypeEnum.doGetEnumByValue(definePermission.getType());
            if(typeEnum != null){
                definePermissionVo.setTypeStr(typeEnum.getLabel());
            }   else {
                definePermissionVo.setTypeStr("");
            }
        }
        definePermissionVo.setState(definePermission.getState());
        definePermissionVo.setCreateTime(definePermission.getCreateTime());
        definePermissionVo.setUpdateTime(definePermission.getUpdateTime());
        definePermissionVo.setCreateUser(definePermission.getCreateUser());
        definePermissionVo.setLastModifyer(definePermission.getLastModifyer());
        definePermissionVo.setRemark(definePermission.getRemark());
        return definePermissionVo ;
    }

    public static List<DefinePermissionVo> transferEntityToVoList(List<DefinePermission> definePermissions){
        if(definePermissions == null){
            return null ;
        }   else {
            List<DefinePermissionVo> list = new ArrayList<>() ;
            for (DefinePermission definePermission : definePermissions){
                list.add(transferEntityToVo(definePermission));
            }
            return list ;
        }
    }

}
