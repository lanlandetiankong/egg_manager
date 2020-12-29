package com.egg.manager.facade.persistence.em.user.pojo.transfer;

import cn.hutool.core.collection.CollectionUtil;
import com.egg.manager.facade.persistence.commons.util.data.str.MyUUIDUtil;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.facade.persistence.em.user.pojo.excel.export.user.EmUserAccountXlsOutModel;
import com.egg.manager.facade.persistence.em.user.pojo.excel.introduce.user.EmUserAccountXlsInModel;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap.EmUserAccountMapstruct;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAccountVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
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
public class EmUserAccountTransfer extends BaseMysqlTransfer {

    static EmUserAccountMapstruct emUserAccountMapstruct = EmUserAccountMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmUserAccountEntity transferVoToEntity(EmUserAccountVo vo) {
        if (vo == null) {
            return null;
        }
        EmUserAccountEntity entity = emUserAccountMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmUserAccountVo transferEntityToVo(EmUserAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserAccountVo vo = emUserAccountMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmUserAccountVo transferDtoToVo(EmUserAccountDto dto) {
        if (dto == null) {
            return null;
        }
        EmUserAccountVo vo = emUserAccountMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmUserAccountVo> transferEntityToVoList(List<EmUserAccountEntity> userAccountEntities) {
        if (userAccountEntities == null) {
            return null;
        } else {
            List<EmUserAccountVo> list = new ArrayList<>();
            for (EmUserAccountEntity account : userAccountEntities) {
                list.add(transferEntityToVo(account));
            }
            return list;
        }
    }

    public static List<EmUserAccountVo> transferDtoToVoList(List<EmUserAccountDto> emUserAccountDtos) {
        if (emUserAccountDtos == null) {
            return null;
        } else {
            List<EmUserAccountVo> list = new ArrayList<>();
            for (EmUserAccountDto emUserAccountDto : emUserAccountDtos) {
                list.add(transferDtoToVo(emUserAccountDto));
            }
            return list;
        }
    }

    public static EmUserAccountXlsOutModel entityToXlsOutModel(EmUserAccountEntity entity) {
        EmUserAccountXlsOutModel emUserAccountXlsOutModel = emUserAccountMapstruct.entityToXlsOutModel(entity);
        return emUserAccountXlsOutModel;
    }

    public static List<EmUserAccountXlsOutModel> entityListToXlsOutModels(List<EmUserAccountEntity> entityList) {
        List<EmUserAccountXlsOutModel> list = new ArrayList<>();
        for (EmUserAccountEntity entity : entityList) {
            list.add(entityToXlsOutModel(entity));
        }
        return list;
    }


    public static EmUserAccountEntity xlsInModelToEntity(EmUserAccountXlsInModel xlsInModel, EmUserAccountEntity loginUser) {     //excel导入默认转化
        EmUserAccountEntity entity = emUserAccountMapstruct.xlsInModelToEntity(xlsInModel, loginUser);
        return entity;
    }

    public static List<EmUserAccountEntity> xlsModelListToEntitys(List<EmUserAccountXlsInModel> xlsInModelList, EmUserAccountEntity loginUser, Set<String> accountSet) {
        List<EmUserAccountEntity> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(xlsInModelList)) {
            accountSet = accountSet != null ? accountSet : new HashSet<>();
            for (EmUserAccountXlsInModel xlsInModel : xlsInModelList) {
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
