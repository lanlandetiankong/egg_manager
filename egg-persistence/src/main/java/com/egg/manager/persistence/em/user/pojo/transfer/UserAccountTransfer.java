package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.commons.util.str.MyUUIDUtil;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.user.pojo.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.em.user.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.UserAccountMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.UserAccountVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("userAccountTransfer")
public class UserAccountTransfer extends BaseMysqlTransfer {

    static UserAccountMapstruct userAccountMapstruct = UserAccountMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static UserAccountEntity transferVoToEntity(UserAccountVo vo) {
        if (vo == null) {
            return null;
        }
        UserAccountEntity entity = userAccountMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static UserAccountVo transferEntityToVo(UserAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        UserAccountVo vo = userAccountMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static UserAccountVo transferDtoToVo(UserAccountDto dto) {
        if (dto == null) {
            return null;
        }
        UserAccountVo vo = userAccountMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<UserAccountVo> transferEntityToVoList(List<UserAccountEntity> userAccountEntities) {
        if (userAccountEntities == null) {
            return null;
        } else {
            List<UserAccountVo> list = new ArrayList<>();
            for (UserAccountEntity account : userAccountEntities) {
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

    public static UserAccountXlsOutModel entityToXlsOutModel(UserAccountEntity entity) {
        UserAccountXlsOutModel userAccountXlsOutModel = userAccountMapstruct.entityToXlsOutModel(entity);
        return userAccountXlsOutModel;
    }

    public static List<UserAccountXlsOutModel> entityListToXlsOutModels(List<UserAccountEntity> entityList) {
        List<UserAccountXlsOutModel> list = new ArrayList<>();
        for (UserAccountEntity entity : entityList) {
            list.add(entityToXlsOutModel(entity));
        }
        return list;
    }


    public static UserAccountEntity xlsInModelToEntity(UserAccountXlsInModel xlsInModel, UserAccountEntity loginUser) {     //excel导入默认转化
        UserAccountEntity entity = userAccountMapstruct.xlsInModelToEntity(xlsInModel, loginUser);
        return entity;
    }

    public static List<UserAccountEntity> xlsModelListToEntitys(List<UserAccountXlsInModel> xlsInModelList, UserAccountEntity loginUser, Set<String> accountSet) {
        List<UserAccountEntity> list = new ArrayList<>();
        if (xlsInModelList != null || xlsInModelList.isEmpty() == false) {
            accountSet = accountSet != null ? accountSet : new HashSet<>();
            for (UserAccountXlsInModel xlsInModel : xlsInModelList) {
                if (accountSet.contains(xlsInModel.getAccount())) {
                    //如果用户的[account]出现重复，对新增行的account后面加上 uuid
                    xlsInModel.setAccount(xlsInModel.getAccount() + "_" + MyUUIDUtil.renderSimpleUuid());
                }
                accountSet.add(xlsInModel.getAccount());
                list.add(xlsInModelToEntity(xlsInModel, loginUser));
            }
        }
        return list;
    }
}
