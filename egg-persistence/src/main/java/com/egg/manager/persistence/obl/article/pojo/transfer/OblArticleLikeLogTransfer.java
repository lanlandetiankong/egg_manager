package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeLogEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblArticleLikeLogMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeLogVo;
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
@Named("oblArticleLikeLogTransfer")
public class OblArticleLikeLogTransfer extends BaseMysqlTransfer {
    static OblArticleLikeLogMapstruct oblArticleLikeLogMapstruct = OblArticleLikeLogMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblArticleLikeLogEntity transferVoToEntity(OblArticleLikeLogVo vo) {
        if (vo == null) {
            return null;
        }
        OblArticleLikeLogEntity entity = oblArticleLikeLogMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblArticleLikeLogVo transferEntityToVo(OblArticleLikeLogEntity entity) {
        if (entity == null) {
            return null;
        }
        OblArticleLikeLogVo vo = oblArticleLikeLogMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblArticleLikeLogVo transferDtoToVo(OblArticleLikeLogDto dto) {
        if (dto == null) {
            return null;
        }
        OblArticleLikeLogVo vo = oblArticleLikeLogMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblArticleLikeLogVo> transferEntityToVoList(List<OblArticleLikeLogEntity> oblArticleLikeLogEntities) {
        if (oblArticleLikeLogEntities == null) {
            return null;
        } else {
            List<OblArticleLikeLogVo> list = new ArrayList<>();
            for (OblArticleLikeLogEntity oblArticleLikeLogEntity : oblArticleLikeLogEntities) {
                list.add(transferEntityToVo(oblArticleLikeLogEntity));
            }
            return list;
        }
    }

    public static List<OblArticleLikeLogVo> transferDtoToVoList(List<OblArticleLikeLogDto> oblArticleLikeLogDtos) {
        if (oblArticleLikeLogDtos == null) {
            return null;
        } else {
            List<OblArticleLikeLogVo> list = new ArrayList<>();
            for (OblArticleLikeLogDto oblArticleLikeLogDto : oblArticleLikeLogDtos) {
                list.add(transferDtoToVo(oblArticleLikeLogDto));
            }
            return list;
        }
    }

}