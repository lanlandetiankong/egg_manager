package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserCollectArticleEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserCollectArticleDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblUserCollectArticleMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserCollectArticleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 用户收藏的文章-Transfer
 * @date 2020-12-03
 */
@Component
@Named("oblUserCollectArticleTransfer")
public class OblUserCollectArticleTransfer extends BaseMysqlTransfer {
    static OblUserCollectArticleMapstruct oblUserCollectArticleMapstruct = OblUserCollectArticleMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblUserCollectArticleEntity transferVoToEntity(OblUserCollectArticleVo vo) {
        if (vo == null) {
            return null;
        }
        OblUserCollectArticleEntity entity = oblUserCollectArticleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblUserCollectArticleVo transferEntityToVo(OblUserCollectArticleEntity entity) {
        if (entity == null) {
            return null;
        }
        OblUserCollectArticleVo vo = oblUserCollectArticleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblUserCollectArticleVo transferDtoToVo(OblUserCollectArticleDto dto) {
        if (dto == null) {
            return null;
        }
        OblUserCollectArticleVo vo = oblUserCollectArticleMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblUserCollectArticleVo> transferEntityToVoList(List<OblUserCollectArticleEntity> oblUserCollectArticleEntities) {
        if (oblUserCollectArticleEntities == null) {
            return null;
        } else {
            List<OblUserCollectArticleVo> list = new ArrayList<>();
            for (OblUserCollectArticleEntity oblUserCollectArticleEntity : oblUserCollectArticleEntities) {
                list.add(transferEntityToVo(oblUserCollectArticleEntity));
            }
            return list;
        }
    }

    public static List<OblUserCollectArticleVo> transferDtoToVoList(List<OblUserCollectArticleDto> oblUserCollectArticleDtos) {
        if (oblUserCollectArticleDtos == null) {
            return null;
        } else {
            List<OblUserCollectArticleVo> list = new ArrayList<>();
            for (OblUserCollectArticleDto oblUserCollectArticleDto : oblUserCollectArticleDtos) {
                list.add(transferDtoToVo(oblUserCollectArticleDto));
            }
            return list;
        }
    }

}