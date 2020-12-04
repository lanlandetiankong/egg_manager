package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeRecordEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleLikeRecordMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeRecordVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 文章点赞表-Transfer
 * @date 2020-12-02
 */
@Component
@Named("oblArticleLikeRecordTransfer")
public class OblArticleLikeRecordTransfer extends BaseMysqlTransfer {
    static OblArticleLikeRecordMapstruct oblArticleLikeRecordMapstruct = OblArticleLikeRecordMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleLikeRecordEntity transferVoToEntity(OblArticleLikeRecordVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleLikeRecordEntity entity = oblArticleLikeRecordMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleLikeRecordVo transferEntityToVo(OblArticleLikeRecordEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleLikeRecordVo vo = oblArticleLikeRecordMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleLikeRecordVo transferDtoToVo(OblArticleLikeRecordDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleLikeRecordVo vo = oblArticleLikeRecordMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleLikeRecordVo> transferEntityToVoList(List<OblArticleLikeRecordEntity> oblArticleLikeRecordEntities) {
        if (oblArticleLikeRecordEntities == null) {
            return null;
        } else {
            List<OblArticleLikeRecordVo> list = new ArrayList<>();
            for (OblArticleLikeRecordEntity oblArticleLikeRecordEntity : oblArticleLikeRecordEntities) {
                list.add(transferEntityToVo(oblArticleLikeRecordEntity));
            }
            return list;
        }
    }

    public static List<OblArticleLikeRecordVo> transferDtoToVoList(List<OblArticleLikeRecordDto> oblArticleLikeRecordDtos) {
        if (oblArticleLikeRecordDtos == null) {
            return null;
        } else {
            List<OblArticleLikeRecordVo> list = new ArrayList<>();
            for (OblArticleLikeRecordDto oblArticleLikeRecordDto : oblArticleLikeRecordDtos) {
                list.add(transferDtoToVo(oblArticleLikeRecordDto));
            }
            return list;
        }
    }

}