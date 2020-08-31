package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.common.base.constant.define.UserAccountConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.base.UserSexEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserAccountMysqlDto;
import com.egg.manager.persistence.pojo.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.organization.DefineTenantMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantMysqlVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountMysqlVo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class UserAccountMysqlTransfer extends MyBaseMysqlTransfer {

    public static UserAccount transferVoToEntity(UserAccountMysqlVo userAccountVo) {
        if (userAccountVo == null) {
            return null;
        }
        UserAccount userAccount = new UserAccount();
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
        return userAccount;
    }

    public static UserAccountMysqlVo transferEntityToVo(UserAccount userAccount) {
        if (userAccount == null) {
            return null;
        }
        UserAccountMysqlVo userAccountVo = new UserAccountMysqlVo();
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
        if (userAccountBaseTypeEnums != null) {
            userAccountVo.setUserTypeStr(userAccountBaseTypeEnums.getLabel());
        }
        userAccountVo.setRemark(userAccount.getRemark());
        userAccountVo.setState(userAccount.getState());
        userAccountVo.setLocked(userAccount.getLocked());
        userAccountVo.setCreateTime(userAccount.getCreateTime());
        userAccountVo.setUpdateTime(userAccount.getUpdateTime());
        userAccountVo.setCreateUserId(userAccount.getCreateUserId());
        userAccountVo.setLastModifyerId(userAccount.getLastModifyerId());
        return userAccountVo;
    }

    public static UserAccountMysqlVo transferDtoToVo(UserAccountMysqlDto userAccountDto) {
        if (userAccountDto == null) {
            return null;
        }
        UserAccountMysqlVo userAccountVo = new UserAccountMysqlVo();
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
        if (userAccountBaseTypeEnums != null) {
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
        DefineTenantMysqlVo belongTenantVo = DefineTenantMysqlTransfer.transferEntityToVo(userAccountDto.getBelongTenant());
        if (belongTenantVo != null) {
            userAccountVo.setBelongTenant(belongTenantVo);
            if (StringUtils.isBlank(userAccountVo.getBelongTenantId())) {
                userAccountVo.setBelongTenantId(belongTenantVo.getFid());
            }
        }
        return userAccountVo;
    }

    public static List<UserAccountMysqlVo> transferEntityToVoList(List<UserAccount> userAccounts) {
        if (userAccounts == null) {
            return null;
        } else {
            List<UserAccountMysqlVo> list = new ArrayList<>();
            for (UserAccount account : userAccounts) {
                list.add(transferEntityToVo(account));
            }
            return list;
        }
    }

    public static List<UserAccountMysqlVo> transferDtoToVoList(List<UserAccountMysqlDto> userAccountDtos) {
        if (userAccountDtos == null) {
            return null;
        } else {
            List<UserAccountMysqlVo> list = new ArrayList<>();
            for (UserAccountMysqlDto userAccountDto : userAccountDtos) {
                list.add(transferDtoToVo(userAccountDto));
            }
            return list;
        }
    }


    public static UserAccountXlsOutModel entityToXlsOutModel(UserAccount entity, UserAccountXlsOutModel userAccountXlsOutModel) {
        userAccountXlsOutModel = userAccountXlsOutModel != null ? userAccountXlsOutModel : new UserAccountXlsOutModel();
        userAccountXlsOutModel.setFid(entity.getFid());
        userAccountXlsOutModel.setUserName(entity.getUserName());
        userAccountXlsOutModel.setAccount(entity.getAccount());
        userAccountXlsOutModel.setNickName(entity.getNickName());
        userAccountXlsOutModel.setAvatarUrl(entity.getAvatarUrl());
        userAccountXlsOutModel.setPassword(entity.getPassword());
        userAccountXlsOutModel.setPhone(entity.getPhone());
        userAccountXlsOutModel.setEmail(entity.getEmail());
        //性别
        userAccountXlsOutModel.setSexStr(UserSexEnum.dealGetNameByVal(entity.getSex()));
        //用户类型
        userAccountXlsOutModel.setUserTypeStr(UserAccountBaseTypeEnum.doGetEnumNameByValue(entity.getUserType(), ""));
        userAccountXlsOutModel.setRemark(entity.getRemark());
        userAccountXlsOutModel.setState(entity.getState());
        userAccountXlsOutModel.setLockedStr(UserAccountStateEnum.doGetEnumInfoByValue(entity.getLocked()));
        userAccountXlsOutModel.setCreateTime(entity.getCreateTime());
        userAccountXlsOutModel.setUpdateTime(entity.getUpdateTime());
        userAccountXlsOutModel.setCreateUserId(entity.getCreateUserId());
        userAccountXlsOutModel.setLastModifyerId(entity.getLastModifyerId());
        //userAccountXlsOutModel.setCellStyleMap();
        return userAccountXlsOutModel;
    }

    public static List<UserAccountXlsOutModel> entityListToXlsOutModels(List<UserAccount> entityList) {
        List<UserAccountXlsOutModel> list = new ArrayList<>();
        for (UserAccount entity : entityList) {
            list.add(entityToXlsOutModel(entity, null));
        }
        return list;
    }


    public static UserAccount xlsInModelToEntity(UserAccountXlsInModel xlsInModel, UserAccount entity, UserAccount loginUser) {     //excel导入默认转化
        entity = entity != null ? entity : new UserAccount();
        Date now = new Date();
        entity.setFid(MyUUIDUtil.renderSimpleUUID());
        entity.setUserName(xlsInModel.getUserName());
        entity.setAccount(xlsInModel.getAccount());
        entity.setNickName(xlsInModel.getNickName());
        //entity.setAvatarUrl(xlsInModel.getAvatarUrl());
        entity.setPassword(UserAccountConstant.DEFAULT_PWD);    //使用默认密码
        entity.setPhone(xlsInModel.getPhone());
        entity.setEmail(xlsInModel.getEmail());
        entity.setSex(UserSexEnum.dealGetValByName(xlsInModel.getSexStr()));
        entity.setUserType(UserAccountBaseTypeEnum.SimpleUser.getValue());
        entity.setUserTypeNum(UserAccountBaseTypeEnum.SimpleUser.getValue());
        entity.setRemark(xlsInModel.getRemark());
        entity.setState(BaseStateEnum.ENABLED.getValue());
        entity.setLocked(SwitchStateEnum.Close.getValue());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        if (loginUser != null) {
            entity.setCreateUserId(loginUser.getFid());
            entity.setLastModifyerId(loginUser.getFid());
        }
        return entity;
    }

    public static List<UserAccount> xlsModelListToEntitys(List<UserAccountXlsInModel> xlsInModelList, UserAccount loginUser, Set<String> accountSet) {
        List<UserAccount> list = new ArrayList<>();
        if (xlsInModelList != null || xlsInModelList.isEmpty() == false) {
            accountSet = accountSet != null ? accountSet : new HashSet<>();
            for (UserAccountXlsInModel xlsInModel : xlsInModelList) {
                if (accountSet.contains(xlsInModel.getAccount())) {  //如果用户的[account]出现重复，对新增行的account后面加上 uuid
                    xlsInModel.setAccount(xlsInModel.getAccount() + "_" + MyUUIDUtil.renderSimpleUUID());
                }
                accountSet.add(xlsInModel.getAccount());
                list.add(xlsInModelToEntity(xlsInModel, null, loginUser));
            }
        }
        return list;
    }
}
