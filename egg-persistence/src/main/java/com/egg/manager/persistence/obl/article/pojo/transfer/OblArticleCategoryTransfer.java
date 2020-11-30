package com.egg.manager.persistence.obl.article.pojo.transfer;

import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleCategoryMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 文章分类定义表-Transfer
 * @date 2020-11-30
 */
@Component
@Named("oblArticleCategoryTransfer")
public class OblArticleCategoryTransfer extends BaseMysqlTransfer {
    static OblArticleCategoryMapstruct oblArticleCategoryMapstruct = OblArticleCategoryMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleCategoryEntity transferVoToEntity(OblArticleCategoryVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleCategoryEntity entity = oblArticleCategoryMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleCategoryVo transferEntityToVo(OblArticleCategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleCategoryVo vo = oblArticleCategoryMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleCategoryVo transferDtoToVo(OblArticleCategoryDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleCategoryVo vo = oblArticleCategoryMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleCategoryVo> transferEntityToVoList(List<OblArticleCategoryEntity> oblArticleCategoryEntities) {
        if (oblArticleCategoryEntities == null) {
            return null;
        } else {
            List<OblArticleCategoryVo> list = new ArrayList<>();
            for (OblArticleCategoryEntity oblArticleCategoryEntity : oblArticleCategoryEntities) {
                list.add(transferEntityToVo(oblArticleCategoryEntity));
            }
            return list;
        }
    }

    public static List<OblArticleCategoryVo> transferDtoToVoList(List<OblArticleCategoryDto> oblArticleCategoryDtos) {
        if (oblArticleCategoryDtos == null) {
            return null;
        } else {
            List<OblArticleCategoryVo> list = new ArrayList<>();
            for (OblArticleCategoryDto oblArticleCategoryDto : oblArticleCategoryDtos) {
                list.add(transferDtoToVo(oblArticleCategoryDto));
            }
            return list;
        }
    }

}