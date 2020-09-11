package com.egg.manager.persistence.pojo.mysql.transfer.user;

import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.pojo.common.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserAccountMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Named("userAccountTransfer")
public class UserAccountTransfer extends MyBaseMysqlTransfer {

    static UserAccountMapstruct userAccountMapstruct = UserAccountMapstruct.INSTANCE;

    public static UserAccount transferVoToEntity(UserAccountVo vo) {
        if (vo == null) {
            return null;
        }
        UserAccount entity = userAccountMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static UserAccountVo transferEntityToVo(UserAccount entity) {
        if (entity == null) {
            return null;
        }
        UserAccountVo vo = userAccountMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static UserAccountVo transferDtoToVo(UserAccountDto dto) {
        if (dto == null) {
            return null;
        }
        UserAccountVo vo = userAccountMapstruct.transferDtoToVo(dto);
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

    public static UserAccountXlsOutModel entityToXlsOutModel(UserAccount entity) {
        UserAccountXlsOutModel userAccountXlsOutModel = userAccountMapstruct.entityToXlsOutModel(entity);
        return userAccountXlsOutModel;
    }

    public static List<UserAccountXlsOutModel> entityListToXlsOutModels(List<UserAccount> entityList) {
        List<UserAccountXlsOutModel> list = new ArrayList<>();
        for (UserAccount entity : entityList) {
            list.add(entityToXlsOutModel(entity));
        }
        return list;
    }


    public static UserAccount xlsInModelToEntity(UserAccountXlsInModel xlsInModel,UserAccount loginUser) {     //excel导入默认转化
        UserAccount entity = userAccountMapstruct.xlsInModelToEntity(xlsInModel,loginUser);
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
                list.add(xlsInModelToEntity(xlsInModel, loginUser));
            }
        }
        return list;
    }
}
