package com.egg.manager.persistence.obl.article.pojo.transfer;

import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCommentEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCommentDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleCommentMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCommentVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 文章评论表-Transfer
 * @date 2020-11-30
 */
@Component
@Named("oblArticleCommentTransfer")
public class OblArticleCommentTransfer extends BaseMysqlTransfer {
    static OblArticleCommentMapstruct oblArticleCommentMapstruct = OblArticleCommentMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleCommentEntity transferVoToEntity(OblArticleCommentVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleCommentEntity entity = oblArticleCommentMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleCommentVo transferEntityToVo(OblArticleCommentEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleCommentVo vo = oblArticleCommentMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleCommentVo transferDtoToVo(OblArticleCommentDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleCommentVo vo = oblArticleCommentMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleCommentVo> transferEntityToVoList(List<OblArticleCommentEntity> oblArticleCommentEntities) {
        if (oblArticleCommentEntities == null) {
            return null;
        } else {
            List<OblArticleCommentVo> list = new ArrayList<>();
            for (OblArticleCommentEntity oblArticleCommentEntity : oblArticleCommentEntities) {
                list.add(transferEntityToVo(oblArticleCommentEntity));
            }
            return list;
        }
    }

    public static List<OblArticleCommentVo> transferDtoToVoList(List<OblArticleCommentDto> oblArticleCommentDtos) {
        if (oblArticleCommentDtos == null) {
            return null;
        } else {
            List<OblArticleCommentVo> list = new ArrayList<>();
            for (OblArticleCommentDto oblArticleCommentDto : oblArticleCommentDtos) {
                list.add(transferDtoToVo(oblArticleCommentDto));
            }
            return list;
        }
    }

}