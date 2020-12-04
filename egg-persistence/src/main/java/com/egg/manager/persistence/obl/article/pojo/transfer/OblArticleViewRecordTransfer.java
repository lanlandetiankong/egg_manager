package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleViewRecordEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleViewRecordDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleViewRecordMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleViewRecordVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 文章查看记录-Transfer
 * @date 2020-12-04
 */
@Component
@Named("oblArticleViewRecordTransfer")
public class OblArticleViewRecordTransfer extends BaseMysqlTransfer {
    static OblArticleViewRecordMapstruct oblArticleViewRecordMapstruct = OblArticleViewRecordMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleViewRecordEntity transferVoToEntity(OblArticleViewRecordVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleViewRecordEntity entity = oblArticleViewRecordMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleViewRecordVo transferEntityToVo(OblArticleViewRecordEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleViewRecordVo vo = oblArticleViewRecordMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleViewRecordVo transferDtoToVo(OblArticleViewRecordDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleViewRecordVo vo = oblArticleViewRecordMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleViewRecordVo> transferEntityToVoList(List<OblArticleViewRecordEntity> oblArticleViewRecordEntities) {
        if (oblArticleViewRecordEntities == null) {
            return null;
        } else {
            List<OblArticleViewRecordVo> list = new ArrayList<>();
            for (OblArticleViewRecordEntity oblArticleViewRecordEntity : oblArticleViewRecordEntities) {
                list.add(transferEntityToVo(oblArticleViewRecordEntity));
            }
            return list;
        }
    }

    public static List<OblArticleViewRecordVo> transferDtoToVoList(List<OblArticleViewRecordDto> oblArticleViewRecordDtos) {
        if (oblArticleViewRecordDtos == null) {
            return null;
        } else {
            List<OblArticleViewRecordVo> list = new ArrayList<>();
            for (OblArticleViewRecordDto oblArticleViewRecordDto : oblArticleViewRecordDtos) {
                list.add(transferDtoToVo(oblArticleViewRecordDto));
            }
            return list;
        }
    }

}