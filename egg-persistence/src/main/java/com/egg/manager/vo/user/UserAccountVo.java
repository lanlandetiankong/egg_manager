package com.egg.manager.vo.user;

import com.egg.manager.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccountVo {
    private String fid ;
    private String userName ;
    private String account ;
    private String nickName ;
    private String avatarUrl ;
    private String password ;
    private String phone ;
    private String email ;
    private Integer sex ;
    private String userType ;
    private String userTypeNum;
    private Integer version ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;




    public static UserAccount transferVoToEntity(UserAccountVo userAccountVo) {
        if(userAccountVo == null){
            return null ;
        }
        UserAccount userAccount = new UserAccount() ;
        userAccount.setFid(userAccountVo.getFid());
        userAccount.setUserName(userAccountVo.getUserName());
        userAccount.setAccount(userAccountVo.getAccount());
        userAccount.setNickName(userAccountVo.getNickName());
        userAccount.setAvatarUrl(userAccountVo.getAvatarUrl());
        userAccount.setPassword(userAccountVo.getPassword());
        userAccount.setPhone(userAccountVo.getPhone());
        userAccount.setEmail(userAccountVo.getEmail());
        userAccount.setSex(userAccountVo.getSex());
        userAccount.setUserType(userAccountVo.getUserType());
        userAccount.setUserTypeNum(userAccountVo.getUserTypeNum());
        userAccount.setVersion(userAccountVo.getVersion());
        userAccount.setState(userAccountVo.getState());
        userAccount.setCreateTime(userAccountVo.getCreateTime());
        userAccount.setUpdateTime(userAccountVo.getUpdateTime());
        userAccount.setCreateUser(userAccountVo.getCreateUser());
        userAccount.setLastModifyer(userAccountVo.getLastModifyer());
        return userAccount ;
    }
}
