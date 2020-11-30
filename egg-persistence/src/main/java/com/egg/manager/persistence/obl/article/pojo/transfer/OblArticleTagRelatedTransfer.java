package com.egg.manager.persistence.obl.article.pojo.transfer;

import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleTagRelatedEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleTagRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleTagRelatedMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleTagRelatedVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 文章与标签关联表-Transfer
 * @date 2020-11-30
 */
@Component
@Named("oblArticleTagRelatedTransfer")
public class OblArticleTagRelatedTransfer extends BaseMysqlTransfer {
    static OblArticleTagRelatedMapstruct oblArticleTagRelatedMapstruct = OblArticleTagRelatedMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleTagRelatedEntity transferVoToEntity(OblArticleTagRelatedVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleTagRelatedEntity entity = oblArticleTagRelatedMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleTagRelatedVo transferEntityToVo(OblArticleTagRelatedEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleTagRelatedVo vo = oblArticleTagRelatedMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleTagRelatedVo transferDtoToVo(OblArticleTagRelatedDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleTagRelatedVo vo = oblArticleTagRelatedMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleTagRelatedVo> transferEntityToVoList(List<OblArticleTagRelatedEntity> oblArticleTagRelatedEntities) {
        if (oblArticleTagRelatedEntities == null) {
            return null;
        } else {
            List<OblArticleTagRelatedVo> list = new ArrayList<>();
            for (OblArticleTagRelatedEntity oblArticleTagRelatedEntity : oblArticleTagRelatedEntities) {
                list.add(transferEntityToVo(oblArticleTagRelatedEntity));
            }
            return list;
        }
    }

    public static List<OblArticleTagRelatedVo> transferDtoToVoList(List<OblArticleTagRelatedDto> oblArticleTagRelatedDtos) {
        if (oblArticleTagRelatedDtos == null) {
            return null;
        } else {
            List<OblArticleTagRelatedVo> list = new ArrayList<>();
            for (OblArticleTagRelatedDto oblArticleTagRelatedDto : oblArticleTagRelatedDtos) {
                list.add(transferDtoToVo(oblArticleTagRelatedDto));
            }
            return list;
        }
    }

}