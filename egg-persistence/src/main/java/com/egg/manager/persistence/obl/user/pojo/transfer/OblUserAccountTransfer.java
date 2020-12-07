package com.egg.manager.persistence.obl.user.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAccountEntity;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAccountDto;
import com.egg.manager.persistence.obl.user.pojo.mapstruct.imap.OblUserAccountMapstruct;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAccountVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 用户表-Transfer
 * @date 2020-12-07
 */
@Component
@Named("oblUserAccountTransfer")
public class OblUserAccountTransfer extends BaseMysqlTransfer {
    static OblUserAccountMapstruct oblUserAccountMapstruct = OblUserAccountMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblUserAccountEntity transferVoToEntity(OblUserAccountVo vo) {
        if (vo == null) {
            return null;
        }
        OblUserAccountEntity entity = oblUserAccountMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblUserAccountVo transferEntityToVo(OblUserAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        OblUserAccountVo vo = oblUserAccountMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblUserAccountVo transferDtoToVo(OblUserAccountDto dto) {
        if (dto == null) {
            return null;
        }
        OblUserAccountVo vo = oblUserAccountMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblUserAccountVo> transferEntityToVoList(List<OblUserAccountEntity> oblUserAccountEntities) {
        if (oblUserAccountEntities == null) {
            return null;
        } else {
            List<OblUserAccountVo> list = new ArrayList<>();
            for (OblUserAccountEntity oblUserAccountEntity : oblUserAccountEntities) {
                list.add(transferEntityToVo(oblUserAccountEntity));
            }
            return list;
        }
    }

    public static List<OblUserAccountVo> transferDtoToVoList(List<OblUserAccountDto> oblUserAccountDtos) {
        if (oblUserAccountDtos == null) {
            return null;
        } else {
            List<OblUserAccountVo> list = new ArrayList<>();
            for (OblUserAccountDto oblUserAccountDto : oblUserAccountDtos) {
                list.add(transferDtoToVo(oblUserAccountDto));
            }
            return list;
        }
    }

}