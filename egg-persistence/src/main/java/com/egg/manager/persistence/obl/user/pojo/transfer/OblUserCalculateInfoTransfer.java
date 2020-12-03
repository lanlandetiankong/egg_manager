package com.egg.manager.persistence.obl.user.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserCalculateInfoEntity;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserCalculateInfoDto;
import com.egg.manager.persistence.obl.user.pojo.mapstruct.imap.OblUserCalculateInfoMapstruct;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserCalculateInfoVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 用户的计算信息-Transfer
 * @date 2020-12-03
 */
@Component
@Named("oblUserCalculateInfoTransfer")
public class OblUserCalculateInfoTransfer extends BaseMysqlTransfer {
    static OblUserCalculateInfoMapstruct oblUserCalculateInfoMapstruct = OblUserCalculateInfoMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblUserCalculateInfoEntity transferVoToEntity(OblUserCalculateInfoVo vo) {
        if (vo == null) {
            return null;
        }
        OblUserCalculateInfoEntity entity = oblUserCalculateInfoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblUserCalculateInfoVo transferEntityToVo(OblUserCalculateInfoEntity entity) {
        if (entity == null) {
            return null;
        }
        OblUserCalculateInfoVo vo = oblUserCalculateInfoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblUserCalculateInfoVo transferDtoToVo(OblUserCalculateInfoDto dto) {
        if (dto == null) {
            return null;
        }
        OblUserCalculateInfoVo vo = oblUserCalculateInfoMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblUserCalculateInfoVo> transferEntityToVoList(List<OblUserCalculateInfoEntity> oblUserCalculateInfoEntities) {
        if (oblUserCalculateInfoEntities == null) {
            return null;
        } else {
            List<OblUserCalculateInfoVo> list = new ArrayList<>();
            for (OblUserCalculateInfoEntity oblUserCalculateInfoEntity : oblUserCalculateInfoEntities) {
                list.add(transferEntityToVo(oblUserCalculateInfoEntity));
            }
            return list;
        }
    }

    public static List<OblUserCalculateInfoVo> transferDtoToVoList(List<OblUserCalculateInfoDto> oblUserCalculateInfoDtos) {
        if (oblUserCalculateInfoDtos == null) {
            return null;
        } else {
            List<OblUserCalculateInfoVo> list = new ArrayList<>();
            for (OblUserCalculateInfoDto oblUserCalculateInfoDto : oblUserCalculateInfoDtos) {
                list.add(transferDtoToVo(oblUserCalculateInfoDto));
            }
            return list;
        }
    }

}