package com.egg.manager.vo.organization;

import com.egg.manager.entity.organization.DefineTenant;
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
public class DefineTenantVo {
    private String fid ;
    private String name ;
    private String code ;
    private String dbCode;
    private String typeStr ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;

    private String createUser ;
    private String lastModifyer;
    private String remark ;










    public static DefineTenant transferVoToEntity(DefineTenantVo defineTenantVo) {
        if(defineTenantVo == null){
            return null ;
        }
        DefineTenant defineTenant = new DefineTenant() ;
        defineTenant.setFid(defineTenantVo.getFid());
        defineTenant.setName(defineTenantVo.getName());
        defineTenant.setCode(defineTenantVo.getCode());
        defineTenant.setDbCode(defineTenantVo.getDbCode());
        defineTenant.setState(defineTenantVo.getState());
        defineTenant.setCreateTime(defineTenantVo.getCreateTime());
        defineTenant.setUpdateTime(defineTenantVo.getUpdateTime());
        defineTenant.setCreateUser(defineTenantVo.getCreateUser());
        defineTenant.setLastModifyer(defineTenantVo.getLastModifyer());
        defineTenant.setRemark(defineTenantVo.getRemark());
        return defineTenant ;
    }

    public static DefineTenantVo transferEntityToVo(DefineTenant defineTenant) {
        if(defineTenant == null){
            return null ;
        }
        DefineTenantVo defineTenantVo = new DefineTenantVo() ;
        defineTenantVo.setFid(defineTenant.getFid());
        defineTenantVo.setName(defineTenant.getName());
        defineTenantVo.setCode(defineTenant.getCode());
        defineTenantVo.setDbCode(defineTenant.getDbCode());
        defineTenantVo.setState(defineTenant.getState());
        defineTenantVo.setCreateTime(defineTenant.getCreateTime());
        defineTenantVo.setUpdateTime(defineTenant.getUpdateTime());
        defineTenantVo.setCreateUser(defineTenant.getCreateUser());
        defineTenantVo.setLastModifyer(defineTenant.getLastModifyer());
        defineTenantVo.setRemark(defineTenant.getRemark());
        return defineTenantVo ;
    }

    public static List<DefineTenantVo> transferEntityToVoList(List<DefineTenant> defineTenants){
        if(defineTenants == null){
            return null ;
        }   else {
            List<DefineTenantVo> list = new ArrayList<>() ;
            for (DefineTenant defineTenant : defineTenants){
                list.add(transferEntityToVo(defineTenant));
            }
            return list ;
        }
    }

}
