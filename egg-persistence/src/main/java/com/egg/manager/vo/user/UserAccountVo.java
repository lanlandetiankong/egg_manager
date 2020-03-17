package com.egg.manager.vo.user;

import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.dto.user.UserAccountDto;
import com.egg.manager.entity.organization.DefineTenant;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.organization.DefineTenantVo;
import lombok.*;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;

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
    private FileResBean uploadImgBean ;
    private Integer locked ;


    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;


    private String belongTenantId ;
    private DefineTenantVo belongTenant ;





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
        userAccount.setRemark(userAccountVo.getRemark());
        userAccount.setState(userAccountVo.getState());
        userAccount.setLocked(userAccountVo.getLocked());
        userAccount.setCreateTime(userAccountVo.getCreateTime());
        userAccount.setUpdateTime(userAccountVo.getUpdateTime());
        userAccount.setCreateUserId(userAccountVo.getCreateUserId());
        userAccount.setLastModifyerId(userAccountVo.getLastModifyerId());
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
        userAccountVo.setRemark(userAccount.getRemark());
        userAccountVo.setState(userAccount.getState());
        userAccountVo.setLocked(userAccount.getLocked());
        userAccountVo.setCreateTime(userAccount.getCreateTime());
        userAccountVo.setUpdateTime(userAccount.getUpdateTime());
        userAccountVo.setCreateUserId(userAccount.getCreateUserId());
        userAccountVo.setLastModifyerId(userAccount.getLastModifyerId());
        return userAccountVo ;
    }

    public static UserAccountVo transferDtoToVo(UserAccountDto userAccountDto) {
        if(userAccountDto == null){
            return null ;
        }
        UserAccountVo userAccountVo = new UserAccountVo() ;
        userAccountVo.setFid(userAccountDto.getFid());
        userAccountVo.setUserName(userAccountDto.getUserName());
        userAccountVo.setAccount(userAccountDto.getAccount());
        userAccountVo.setNickName(userAccountDto.getNickName());
        userAccountVo.setAvatarUrl(userAccountDto.getAvatarUrl());
        userAccountVo.setPassword(userAccountDto.getPassword());
        userAccountVo.setPhone(userAccountDto.getPhone());
        userAccountVo.setEmail(userAccountDto.getEmail());
        userAccountVo.setSex(userAccountDto.getSex());
        userAccountVo.setUserType(userAccountDto.getUserType());
        userAccountVo.setUserTypeNum(userAccountDto.getUserTypeNum());
        UserAccountBaseTypeEnum userAccountBaseTypeEnums = UserAccountBaseTypeEnum.doGetEnumByValue(userAccountDto.getUserType());
        if(userAccountBaseTypeEnums != null){
            userAccountVo.setUserTypeStr(userAccountBaseTypeEnums.getLabel());
        }
        userAccountVo.setRemark(userAccountDto.getRemark());
        userAccountVo.setState(userAccountDto.getState());
        userAccountVo.setLocked(userAccountDto.getLocked());
        userAccountVo.setCreateTime(userAccountDto.getCreateTime());
        userAccountVo.setUpdateTime(userAccountDto.getUpdateTime());
        userAccountVo.setCreateUserId(userAccountDto.getCreateUserId());
        userAccountVo.setLastModifyerId(userAccountDto.getLastModifyerId());

        userAccountVo.setBelongTenantId(userAccountDto.getBelongTenantId());
        DefineTenantVo belongTenantVo = DefineTenantVo.transferEntityToVo(userAccountDto.getBelongTenant()) ;
        if(belongTenantVo != null){
            userAccountVo.setBelongTenant(belongTenantVo);
            if(StringUtils.isBlank(userAccountVo.getBelongTenantId())){
                userAccountVo.setBelongTenantId(belongTenantVo.getFid());
            }
        }
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

    public static List<UserAccountVo> transferDtoToVoList(List<UserAccountDto> userAccountDtos){
        if(userAccountDtos == null){
            return null ;
        }   else {
            List<UserAccountVo> list = new ArrayList<>() ;
            for (UserAccountDto userAccountDto : userAccountDtos){
                list.add(transferDtoToVo(userAccountDto));
            }
            return list ;
        }
    }
}
