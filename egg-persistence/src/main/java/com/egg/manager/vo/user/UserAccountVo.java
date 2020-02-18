package com.egg.manager.vo.user;

import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.entity.user.UserAccount;
import lombok.*;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Integer userType ;
    private Integer userTypeNum;
    private String userTypeStr;
    private Integer version ;
    private Integer state ;
    private Integer locked ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;
    private FileResBean uploadImgBean ;




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
        userAccount.setLocked(userAccountVo.getLocked());
        userAccount.setCreateTime(userAccountVo.getCreateTime());
        userAccount.setUpdateTime(userAccountVo.getUpdateTime());
        userAccount.setCreateUser(userAccountVo.getCreateUser());
        userAccount.setLastModifyer(userAccountVo.getLastModifyer());
        return userAccount ;
    }

    public static UserAccountVo transferEntityToVo(UserAccount userAccount) {
        if(userAccount == null){
            return null ;
        }
        UserAccountVo userAccountVo = new UserAccountVo() ;
        userAccountVo.setFid(userAccount.getFid());
        userAccountVo.setUserName(userAccount.getUserName());
        userAccountVo.setAccount(userAccount.getAccount());
        userAccountVo.setNickName(userAccount.getNickName());
        userAccountVo.setAvatarUrl(userAccount.getAvatarUrl());
        userAccountVo.setPassword(userAccount.getPassword());
        userAccountVo.setPhone(userAccount.getPhone());
        userAccountVo.setEmail(userAccount.getEmail());
        userAccountVo.setSex(userAccount.getSex());
        userAccountVo.setUserType(userAccount.getUserType());
        userAccountVo.setUserTypeNum(userAccount.getUserTypeNum());
        UserAccountBaseTypeEnum userAccountBaseTypeEnums = UserAccountBaseTypeEnum.doGetEnumByValue(userAccount.getUserType());
        if(userAccountBaseTypeEnums != null){
            userAccountVo.setUserTypeStr(userAccountBaseTypeEnums.getLabel());
        }
        userAccountVo.setVersion(userAccount.getVersion());
        userAccountVo.setState(userAccount.getState());
        userAccountVo.setLocked(userAccount.getLocked());
        userAccountVo.setCreateTime(userAccount.getCreateTime());
        userAccountVo.setUpdateTime(userAccount.getUpdateTime());
        userAccountVo.setCreateUser(userAccount.getCreateUser());
        userAccountVo.setLastModifyer(userAccount.getLastModifyer());
        return userAccountVo ;
    }

    public static List<UserAccountVo> transferEntityToVoList(List<UserAccount> userAccounts){
        if(userAccounts == null){
            return null ;
        }   else {
            List<UserAccountVo> list = new ArrayList<>() ;
            for (UserAccount account : userAccounts){
                list.add(transferEntityToVo(account));
            }
            return list ;
        }
    }
}
