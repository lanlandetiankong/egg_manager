package com.egg.manager.persistence.transfer.user;

import com.egg.manager.common.base.enums.base.UserSexEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.persistence.dto.user.UserAccountDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.excel.user.UserAccountXlsModel;
import com.egg.manager.persistence.transfer.organization.DefineTenantTransfer;
import com.egg.manager.persistence.vo.organization.DefineTenantVo;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class UserAccountTransfer {

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
        DefineTenantVo belongTenantVo = DefineTenantTransfer.transferEntityToVo(userAccountDto.getBelongTenant()) ;
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


    public  static UserAccountXlsModel voToXlsModel(UserAccount vo,UserAccountXlsModel userAccountXlsModel){
        userAccountXlsModel = userAccountXlsModel != null ? userAccountXlsModel : new UserAccountXlsModel() ;
        userAccountXlsModel.setFid(vo.getFid());
        userAccountXlsModel.setUserName(vo.getUserName());
        userAccountXlsModel.setAccount(vo.getAccount());
        userAccountXlsModel.setNickName(vo.getNickName());
        userAccountXlsModel.setAvatarUrl(vo.getAvatarUrl());
        userAccountXlsModel.setPassword(vo.getPassword());
        userAccountXlsModel.setPhone(vo.getPhone());
        userAccountXlsModel.setEmail(vo.getEmail());
        //性别
        userAccountXlsModel.setSexStr(UserSexEnum.dealGetNameByVal(vo.getSex()));
        //用户类型
        userAccountXlsModel.setUserTypeStr(UserAccountBaseTypeEnum.doGetEnumNameByValue(vo.getUserType(),""));
        userAccountXlsModel.setRemark(vo.getRemark());
        userAccountXlsModel.setState(vo.getState());
        userAccountXlsModel.setLockedStr(UserAccountStateEnum.doGetEnumInfoByValue(vo.getLocked()));
        userAccountXlsModel.setCreateTime(vo.getCreateTime());
        userAccountXlsModel.setUpdateTime(vo.getUpdateTime());
        userAccountXlsModel.setCreateUserId(vo.getCreateUserId());
        userAccountXlsModel.setLastModifyerId(vo.getLastModifyerId());
        //userAccountXlsModel.setCellStyleMap();
        return userAccountXlsModel;
    }

    public static List<UserAccountXlsModel> voListToXlsModels(List<UserAccount> voList){
        List<UserAccountXlsModel> list = new ArrayList<>();
        for (UserAccount vo : voList){
            list.add(voToXlsModel(vo,null));
        }
        return list ;
    }
}
