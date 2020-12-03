package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserAttentionArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserAttentionArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblUserAttentionArticleCategoryMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserAttentionArticleCategoryVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 用户关注的文章收藏类别-Transfer
 * @date 2020-12-03
 */
@Component
@Named("oblUserAttentionArticleCategoryTransfer")
public class OblUserAttentionArticleCategoryTransfer extends BaseMysqlTransfer {
    static OblUserAttentionArticleCategoryMapstruct oblUserAttentionArticleCategoryMapstruct = OblUserAttentionArticleCategoryMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblUserAttentionArticleCategoryEntity transferVoToEntity(OblUserAttentionArticleCategoryVo vo) {
        if (vo == null) {
            return null;
        }
        OblUserAttentionArticleCategoryEntity entity = oblUserAttentionArticleCategoryMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblUserAttentionArticleCategoryVo transferEntityToVo(OblUserAttentionArticleCategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        OblUserAttentionArticleCategoryVo vo = oblUserAttentionArticleCategoryMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblUserAttentionArticleCategoryVo transferDtoToVo(OblUserAttentionArticleCategoryDto dto) {
        if (dto == null) {
            return null;
        }
        OblUserAttentionArticleCategoryVo vo = oblUserAttentionArticleCategoryMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblUserAttentionArticleCategoryVo> transferEntityToVoList(List<OblUserAttentionArticleCategoryEntity> oblUserAttentionArticleCategoryEntities) {
        if (oblUserAttentionArticleCategoryEntities == null) {
            return null;
        } else {
            List<OblUserAttentionArticleCategoryVo> list = new ArrayList<>();
            for (OblUserAttentionArticleCategoryEntity oblUserAttentionArticleCategoryEntity : oblUserAttentionArticleCategoryEntities) {
                list.add(transferEntityToVo(oblUserAttentionArticleCategoryEntity));
            }
            return list;
        }
    }

    public static List<OblUserAttentionArticleCategoryVo> transferDtoToVoList(List<OblUserAttentionArticleCategoryDto> oblUserAttentionArticleCategoryDtos) {
        if (oblUserAttentionArticleCategoryDtos == null) {
            return null;
        } else {
            List<OblUserAttentionArticleCategoryVo> list = new ArrayList<>();
            for (OblUserAttentionArticleCategoryDto oblUserAttentionArticleCategoryDto : oblUserAttentionArticleCategoryDtos) {
                list.add(transferDtoToVo(oblUserAttentionArticleCategoryDto));
            }
            return list;
        }
    }

}