package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.common.base.constant.define.UserAccountConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.base.UserSexEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserAccountDto;
import com.egg.manager.persistence.pojo.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user.UserAccountVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Named("UserAccountTransfer")
public class UserAccountTransfer extends MyBaseMysqlTransfer {

    static UserAccountVoMapstruct userAccountVoMapstruct = UserAccountVoMapstruct.INSTANCE;

    public static UserAccount transferVoToEntity(UserAccountVo vo) {
        if (vo == null) {
            return null;
        }
        UserAccount entity = userAccountVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static UserAccountVo transferEntityToVo(UserAccount entity) {
        if (entity == null) {
            return null;
        }
        UserAccountVo vo = userAccountVoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static UserAccountVo transferDtoToVo(UserAccountDto dto) {
        if (dto == null) {
            return null;
        }
        UserAccountVo vo = userAccountVoMapstruct.transferDtoToVo(dto);
        UserAccountBaseTypeEnum userAccountBaseTypeEnums = UserAccountBaseTypeEnum.doGetEnumByValue(dto.getUserType());
        if (userAccountBaseTypeEnums != null) {
            vo.setUserTypeStr(userAccountBaseTypeEnums.getLabel());
        }
        DefineTenantVo belongTenantVo = DefineTenantTransfer.transferEntityToVo(dto.getBelongTenant());
        if (belongTenantVo != null) {
            vo.setBelongTenant(belongTenantVo);
            if (StringUtils.isBlank(vo.getBelongTenantId())) {
                vo.setBelongTenantId(belongTenantVo.getFid());
            }
        }
        return vo;
    }

    public static List<UserAccountVo> transferEntityToVoList(List<UserAccount> userAccounts) {
        if (userAccounts == null) {
            return null;
        } else {
            List<UserAccountVo> list = new ArrayList<>();
            for (UserAccount account : userAccounts) {
                list.add(transferEntityToVo(account));
            }
            return list;
        }
    }

    public static List<UserAccountVo> transferDtoToVoList(List<UserAccountDto> userAccountDtos) {
        if (userAccountDtos == null) {
            return null;
        } else {
            List<UserAccountVo> list = new ArrayList<>();
            for (UserAccountDto userAccountDto : userAccountDtos) {
                list.add(transferDtoToVo(userAccountDto));
            }
            return list;
        }
    }

    //TODO
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
