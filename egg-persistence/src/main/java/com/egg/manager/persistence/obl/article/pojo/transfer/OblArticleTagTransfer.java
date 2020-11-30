package com.egg.manager.persistence.obl.article.pojo.transfer;

import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleTagEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleTagDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleTagMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleTagVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 文章标签定义表-Transfer
 * @date 2020-11-30
 */
@Component
@Named("oblArticleTagTransfer")
public class OblArticleTagTransfer extends BaseMysqlTransfer {
    static OblArticleTagMapstruct oblArticleTagMapstruct = OblArticleTagMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleTagEntity transferVoToEntity(OblArticleTagVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleTagEntity entity = oblArticleTagMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleTagVo transferEntityToVo(OblArticleTagEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleTagVo vo = oblArticleTagMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleTagVo transferDtoToVo(OblArticleTagDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleTagVo vo = oblArticleTagMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleTagVo> transferEntityToVoList(List<OblArticleTagEntity> oblArticleTagEntities) {
        if (oblArticleTagEntities == null) {
            return null;
        } else {
            List<OblArticleTagVo> list = new ArrayList<>();
            for (OblArticleTagEntity oblArticleTagEntity : oblArticleTagEntities) {
                list.add(transferEntityToVo(oblArticleTagEntity));
            }
            return list;
        }
    }

    public static List<OblArticleTagVo> transferDtoToVoList(List<OblArticleTagDto> oblArticleTagDtos) {
        if (oblArticleTagDtos == null) {
            return null;
        } else {
            List<OblArticleTagVo> list = new ArrayList<>();
            for (OblArticleTagDto oblArticleTagDto : oblArticleTagDtos) {
                list.add(transferDtoToVo(oblArticleTagDto));
            }
            return list;
        }
    }

}