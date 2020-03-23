package com.egg.manager.persistence.vo.organization;

import com.egg.manager.persistence.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.entity.organization.DefineTenant;
import com.egg.manager.persistence.entity.user.UserAccount;
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

    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;













    public static DefineTenant transferVoToEntity(DefineTenantVo defineTenantVo) {
        if(defineTenantVo == null){
            return null ;
        }
        DefineTenant defineTenant = new DefineTenant() ;
        defineTenant.setFid(defineTenantVo.getFid());
        defineTenant.setName(defineTenantVo.getName());
        defineTenant.setCode(defineTenantVo.getCode());
        defineTenant.setDbCode(defineTenantVo.getDbCode());
        defineTenant.setRemark(defineTenantVo.getRemark());
        defineTenant.setState(defineTenantVo.getState());
        defineTenant.setCreateTime(defineTenantVo.getCreateTime());
        defineTenant.setUpdateTime(defineTenantVo.getUpdateTime());
        defineTenant.setCreateUserId(defineTenantVo.getCreateUserId());
        defineTenant.setLastModifyerId(defineTenantVo.getLastModifyerId());
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
        defineTenantVo.setRemark(defineTenant.getRemark());
        defineTenantVo.setState(defineTenant.getState());
        defineTenantVo.setCreateTime(defineTenant.getCreateTime());
        defineTenantVo.setUpdateTime(defineTenant.getUpdateTime());
        defineTenantVo.setCreateUserId(defineTenant.getCreateUserId());
        defineTenantVo.setLastModifyerId(defineTenant.getLastModifyerId());
        return defineTenantVo ;
    }

    public static DefineTenantVo transferDtoToVo(DefineTenantDto defineTenantDto) {
        if(defineTenantDto == null){
            return null ;
        }
        DefineTenantVo defineTenantVo = new DefineTenantVo() ;
        defineTenantVo.setFid(defineTenantDto.getFid());
        defineTenantVo.setName(defineTenantDto.getName());
        defineTenantVo.setCode(defineTenantDto.getCode());
        defineTenantVo.setDbCode(defineTenantDto.getDbCode());
        defineTenantVo.setRemark(defineTenantDto.getRemark());
        defineTenantVo.setState(defineTenantDto.getState());
        defineTenantVo.setCreateTime(defineTenantDto.getCreateTime());
        defineTenantVo.setUpdateTime(defineTenantDto.getUpdateTime());
        defineTenantVo.setCreateUserId(defineTenantDto.getCreateUserId());
        defineTenantVo.setLastModifyerId(defineTenantDto.getLastModifyerId());
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

    public static List<DefineTenantVo> transferDtoToVoList(List<DefineTenantDto> defineTenantDtoList){
        if(defineTenantDtoList == null){
            return null ;
        }   else {
            List<DefineTenantVo> list = new ArrayList<>() ;
            for (DefineTenantDto defineTenantDto : defineTenantDtoList){
                list.add(transferDtoToVo(defineTenantDto));
            }
            return list ;
        }
    }

}
