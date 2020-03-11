package com.egg.manager.vo.organization;

import com.egg.manager.entity.organization.DefineTenant;
import com.egg.manager.entity.user.UserAccount;
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
    private String remark ;
    private Date createTime ;
    private Date updateTime ;

    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;













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
        defineTenant.setCreateUserId(defineTenantVo.getCreateUserId());
        defineTenant.setLastModifyerId(defineTenantVo.getLastModifyerId());
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
        defineTenantVo.setCreateUserId(defineTenant.getCreateUserId());
        defineTenantVo.setLastModifyerId(defineTenant.getLastModifyerId());
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
