package com.egg.manager.vo.user;

import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
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
public class UserRoleVo {
    private String fid;
    private String userAccountId;
    private String defineRoleId;
    private Integer type;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String lastModifyer;



    public static UserRole transferVoToEntity(UserRoleVo userRoleVo) {
        if(userRoleVo == null){
            return null ;
        }
        UserRole userRole = new UserRole() ;
        userRole.setFid(userRoleVo.getFid());
        userRole.setUserAccountId(userRoleVo.getUserAccountId());
        userRole.setDefineRoleId(userRoleVo.getDefineRoleId());
        userRole.setType(userRoleVo.getType());
        userRole.setState(userRoleVo.getState());
        userRole.setCreateTime(userRoleVo.getCreateTime());
        userRole.setUpdateTime(userRoleVo.getUpdateTime());
        userRole.setCreateUser(userRoleVo.getCreateUser());
        userRole.setLastModifyer(userRoleVo.getLastModifyer());
        return userRole ;
    }


    public static UserRoleVo transferEntityToVo(UserRole userRole) {
        if(userRole == null){
            return null ;
        }
        UserRoleVo userRoleVo = new UserRoleVo() ;
        userRoleVo.setFid(userRole.getFid());
        userRoleVo.setUserAccountId(userRole.getUserAccountId());
        userRoleVo.setDefineRoleId(userRole.getDefineRoleId());
        userRoleVo.setType(userRole.getType());
        userRoleVo.setState(userRole.getState());
        userRoleVo.setCreateTime(userRole.getCreateTime());
        userRoleVo.setUpdateTime(userRole.getUpdateTime());
        userRoleVo.setCreateUser(userRole.getCreateUser());
        userRoleVo.setLastModifyer(userRole.getLastModifyer());
        return userRoleVo ;
    }

    public static List<UserRoleVo> transferEntityToVoList(List<UserRole> userRoles){
        if(userRoles == null){
            return null ;
        }   else {
            List<UserRoleVo> list = new ArrayList<>() ;
            for (UserRole role : userRoles){
                list.add(transferEntityToVo(role));
            }
            return list ;
        }
    }

}
