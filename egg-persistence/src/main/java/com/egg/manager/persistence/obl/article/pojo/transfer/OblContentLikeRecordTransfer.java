package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeRecordEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblContentLikeRecordMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeRecordVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 评论点赞表-Transfer
 * @date 2020-12-02
 */
@Component
@Named("oblContentLikeRecordTransfer")
public class OblContentLikeRecordTransfer extends BaseMysqlTransfer {
    static OblContentLikeRecordMapstruct oblContentLikeRecordMapstruct = OblContentLikeRecordMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblContentLikeRecordEntity transferVoToEntity(OblContentLikeRecordVo vo) {
        if (vo == null) {
            return null;
        }
        OblContentLikeRecordEntity entity = oblContentLikeRecordMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblContentLikeRecordVo transferEntityToVo(OblContentLikeRecordEntity entity) {
        if (entity == null) {
            return null;
        }
        OblContentLikeRecordVo vo = oblContentLikeRecordMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblContentLikeRecordVo transferDtoToVo(OblContentLikeRecordDto dto) {
        if (dto == null) {
            return null;
        }
        OblContentLikeRecordVo vo = oblContentLikeRecordMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblContentLikeRecordVo> transferEntityToVoList(List<OblContentLikeRecordEntity> oblContentLikeRecordEntities) {
        if (oblContentLikeRecordEntities == null) {
            return null;
        } else {
            List<OblContentLikeRecordVo> list = new ArrayList<>();
            for (OblContentLikeRecordEntity oblContentLikeRecordEntity : oblContentLikeRecordEntities) {
                list.add(transferEntityToVo(oblContentLikeRecordEntity));
            }
            return list;
        }
    }

    public static List<OblContentLikeRecordVo> transferDtoToVoList(List<OblContentLikeRecordDto> oblContentLikeRecordDtos) {
        if (oblContentLikeRecordDtos == null) {
            return null;
        } else {
            List<OblContentLikeRecordVo> list = new ArrayList<>();
            for (OblContentLikeRecordDto oblContentLikeRecordDto : oblContentLikeRecordDtos) {
                list.add(transferDtoToVo(oblContentLikeRecordDto));
            }
            return list;
        }
    }

}