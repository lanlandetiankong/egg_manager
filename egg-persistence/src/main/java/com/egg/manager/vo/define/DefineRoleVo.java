package com.egg.manager.vo.define;


import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.entity.define.DefineRole;
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
public class DefineRoleVo {
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




    public static DefineRole transferVoToEntity(DefineRoleVo defineRoleVo) {
        if(defineRoleVo == null){
            return null ;
        }
        DefineRole defineRole = new DefineRole() ;
        defineRole.setFid(defineRoleVo.getFid());
        defineRole.setName(defineRoleVo.getName());
        defineRole.setCode(defineRoleVo.getCode());
        defineRole.setType(defineRoleVo.getType());
        defineRole.setState(defineRoleVo.getState());
        defineRole.setCreateTime(defineRoleVo.getCreateTime());
        defineRole.setUpdateTime(defineRoleVo.getUpdateTime());
        defineRole.setCreateUser(defineRoleVo.getCreateUser());
        defineRole.setLastModifyer(defineRoleVo.getLastModifyer());
        defineRole.setRemark(defineRoleVo.getRemark());
        return defineRole ;
    }

    public static DefineRoleVo transferEntityToVo(DefineRole defineRole) {
        if(defineRole == null){
            return null ;
        }
        DefineRoleVo defineRoleVo = new DefineRoleVo() ;
        defineRoleVo.setFid(defineRole.getFid());
        defineRoleVo.setName(defineRole.getName());
        defineRoleVo.setCode(defineRole.getCode());
        defineRoleVo.setType(defineRole.getType());
        if(defineRole.getType() != null){
            DefineRoleTypeEnum typeEnum = DefineRoleTypeEnum.doGetEnumByValue(defineRole.getType());
            if(typeEnum != null){
                defineRoleVo.setTypeStr(typeEnum.getLabel());
            }   else {
                defineRoleVo.setTypeStr("");
            }
        }
        defineRoleVo.setState(defineRole.getState());
        defineRoleVo.setCreateTime(defineRole.getCreateTime());
        defineRoleVo.setUpdateTime(defineRole.getUpdateTime());
        defineRoleVo.setCreateUser(defineRole.getCreateUser());
        defineRoleVo.setLastModifyer(defineRole.getLastModifyer());
        defineRoleVo.setRemark(defineRole.getRemark());
        return defineRoleVo ;
    }

    public static List<DefineRoleVo> transferEntityToVoList(List<DefineRole> definePermissions){
        if(definePermissions == null){
            return null ;
        }   else {
            List<DefineRoleVo> list = new ArrayList<>() ;
            for (DefineRole definePermission : definePermissions){
                list.add(transferEntityToVo(definePermission));
            }
            return list ;
        }
    }
}
