package com.egg.manager.persistence.obl.article.pojo.transfer;

import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryRelatedEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleCategoryRelatedMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryRelatedVo;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 文章分类关联表-Transfer
 * @date 2020-11-30
 */
@Component
@Named("oblArticleCategoryRelatedTransfer")
public class OblArticleCategoryRelatedTransfer extends BaseMysqlTransfer {
    static OblArticleCategoryRelatedMapstruct oblArticleCategoryRelatedMapstruct = OblArticleCategoryRelatedMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleCategoryRelatedEntity transferVoToEntity(OblArticleCategoryRelatedVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleCategoryRelatedEntity entity = oblArticleCategoryRelatedMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleCategoryRelatedVo transferEntityToVo(OblArticleCategoryRelatedEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleCategoryRelatedVo vo = oblArticleCategoryRelatedMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleCategoryRelatedVo transferDtoToVo(OblArticleCategoryRelatedDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleCategoryRelatedVo vo = oblArticleCategoryRelatedMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleCategoryRelatedVo> transferEntityToVoList(List<OblArticleCategoryRelatedEntity> oblArticleCategoryRelatedEntities) {
        if (oblArticleCategoryRelatedEntities == null) {
            return null;
        } else {
            List<OblArticleCategoryRelatedVo> list = new ArrayList<>();
            for (OblArticleCategoryRelatedEntity oblArticleCategoryRelatedEntity : oblArticleCategoryRelatedEntities) {
                list.add(transferEntityToVo(oblArticleCategoryRelatedEntity));
            }
            return list;
        }
    }

    public static List<OblArticleCategoryRelatedVo> transferDtoToVoList(List<OblArticleCategoryRelatedDto> oblArticleCategoryRelatedDtos) {
        if (oblArticleCategoryRelatedDtos == null) {
            return null;
        } else {
            List<OblArticleCategoryRelatedVo> list = new ArrayList<>();
            for (OblArticleCategoryRelatedDto oblArticleCategoryRelatedDto : oblArticleCategoryRelatedDtos) {
                list.add(transferDtoToVo(oblArticleCategoryRelatedDto));
            }
            return list;
        }
    }

}