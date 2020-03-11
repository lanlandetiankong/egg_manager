package com.egg.manager.vo.define;

import com.egg.manager.entity.define.DefineDepartment;
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
public class DefineDepartmentVo {
    private String fid ;
    private String name ;
    private String code ;
    private String parentId ;
    private Integer level ;

    private Integer orderNum ;
    private String description ;

    private Integer state ;
    private String remark ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;






    public static DefineDepartment transferVoToEntity(DefineDepartmentVo defineDepartmentVo) {
        if(defineDepartmentVo == null){
            return null ;
        }
        DefineDepartment defineDepartment = new DefineDepartment() ;
        defineDepartment.setFid(defineDepartmentVo.getFid());
        defineDepartment.setName(defineDepartmentVo.getName());
        defineDepartment.setParentId(defineDepartmentVo.getParentId());
        defineDepartment.setCode(defineDepartmentVo.getCode());
        defineDepartment.setLevel(defineDepartmentVo.getLevel());
        defineDepartment.setOrderNum(defineDepartmentVo.getOrderNum());
        defineDepartment.setDescription(defineDepartmentVo.getDescription());
        defineDepartment.setState(defineDepartmentVo.getState());
        defineDepartment.setRemark(defineDepartmentVo.getRemark());
        defineDepartment.setCreateTime(defineDepartmentVo.getCreateTime());
        defineDepartment.setUpdateTime(defineDepartmentVo.getUpdateTime());
        defineDepartment.setCreateUserId(defineDepartmentVo.getCreateUserId());
        defineDepartment.setLastModifyerId(defineDepartmentVo.getLastModifyerId());
        return defineDepartment ;
    }

    public static DefineDepartmentVo transferEntityToVo(DefineDepartment defineDepartment) {
        if(defineDepartment == null){
            return null ;
        }
        DefineDepartmentVo defineDepartmentVo = new DefineDepartmentVo() ;
        defineDepartmentVo.setFid(defineDepartment.getFid());
        defineDepartmentVo.setName(defineDepartment.getName());
        defineDepartmentVo.setParentId(defineDepartment.getParentId());
        defineDepartmentVo.setCode(defineDepartment.getCode());
        defineDepartmentVo.setLevel(defineDepartment.getLevel());
        defineDepartmentVo.setOrderNum(defineDepartment.getOrderNum());
        defineDepartmentVo.setDescription(defineDepartment.getDescription());
        defineDepartmentVo.setState(defineDepartment.getState());
        defineDepartmentVo.setRemark(defineDepartment.getRemark());
        defineDepartmentVo.setCreateTime(defineDepartment.getCreateTime());
        defineDepartmentVo.setUpdateTime(defineDepartment.getUpdateTime());
        defineDepartmentVo.setCreateUserId(defineDepartment.getCreateUserId());
        defineDepartmentVo.setLastModifyerId(defineDepartment.getLastModifyerId());
        return defineDepartmentVo ;
    }

    public static List<DefineDepartmentVo> transferEntityToVoList(List<DefineDepartment> defineDepartments){
        if(defineDepartments == null){
            return null ;
        }   else {
            List<DefineDepartmentVo> list = new ArrayList<>() ;
            for (DefineDepartment defineDepartment : defineDepartments){
                list.add(transferEntityToVo(defineDepartment));
            }
            return list ;
        }
    }

}
