package com.egg.manager.persistence.obl.user.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAttentionPersonEntity;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAttentionPersonDto;
import com.egg.manager.persistence.obl.user.pojo.mapstruct.imap.OblUserAttentionPersonMapstruct;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAttentionPersonVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 用户的关注人关联-Transfer
 * @date 2020-12-03
 */
@Component
@Named("oblUserAttentionPersonTransfer")
public class OblUserAttentionPersonTransfer extends BaseMysqlTransfer {
    static OblUserAttentionPersonMapstruct oblUserAttentionPersonMapstruct = OblUserAttentionPersonMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblUserAttentionPersonEntity transferVoToEntity(OblUserAttentionPersonVo vo) {
        if (vo == null) {
            return null;
        }
        OblUserAttentionPersonEntity entity = oblUserAttentionPersonMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblUserAttentionPersonVo transferEntityToVo(OblUserAttentionPersonEntity entity) {
        if (entity == null) {
            return null;
        }
        OblUserAttentionPersonVo vo = oblUserAttentionPersonMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblUserAttentionPersonVo transferDtoToVo(OblUserAttentionPersonDto dto) {
        if (dto == null) {
            return null;
        }
        OblUserAttentionPersonVo vo = oblUserAttentionPersonMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblUserAttentionPersonVo> transferEntityToVoList(List<OblUserAttentionPersonEntity> oblUserAttentionPersonEntities) {
        if (oblUserAttentionPersonEntities == null) {
            return null;
        } else {
            List<OblUserAttentionPersonVo> list = new ArrayList<>();
            for (OblUserAttentionPersonEntity oblUserAttentionPersonEntity : oblUserAttentionPersonEntities) {
                list.add(transferEntityToVo(oblUserAttentionPersonEntity));
            }
            return list;
        }
    }

    public static List<OblUserAttentionPersonVo> transferDtoToVoList(List<OblUserAttentionPersonDto> oblUserAttentionPersonDtos) {
        if (oblUserAttentionPersonDtos == null) {
            return null;
        } else {
            List<OblUserAttentionPersonVo> list = new ArrayList<>();
            for (OblUserAttentionPersonDto oblUserAttentionPersonDto : oblUserAttentionPersonDtos) {
                list.add(transferDtoToVo(oblUserAttentionPersonDto));
            }
            return list;
        }
    }

}