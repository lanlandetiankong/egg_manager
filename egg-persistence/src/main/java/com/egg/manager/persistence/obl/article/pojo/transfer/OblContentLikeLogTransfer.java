package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeLogEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblContentLikeLogMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeLogVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 评论点赞表-Transfer
 * @date 2020-12-02
 */
@Component
@Named("oblContentLikeLogTransfer")
public class OblContentLikeLogTransfer extends BaseMysqlTransfer {
    static OblContentLikeLogMapstruct oblContentLikeLogMapstruct = OblContentLikeLogMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblContentLikeLogEntity transferVoToEntity(OblContentLikeLogVo vo) {
        if (vo == null) {
            return null;
        }
        OblContentLikeLogEntity entity = oblContentLikeLogMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblContentLikeLogVo transferEntityToVo(OblContentLikeLogEntity entity) {
        if (entity == null) {
            return null;
        }
        OblContentLikeLogVo vo = oblContentLikeLogMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblContentLikeLogVo transferDtoToVo(OblContentLikeLogDto dto) {
        if (dto == null) {
            return null;
        }
        OblContentLikeLogVo vo = oblContentLikeLogMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblContentLikeLogVo> transferEntityToVoList(List<OblContentLikeLogEntity> oblContentLikeLogEntities) {
        if (oblContentLikeLogEntities == null) {
            return null;
        } else {
            List<OblContentLikeLogVo> list = new ArrayList<>();
            for (OblContentLikeLogEntity oblContentLikeLogEntity : oblContentLikeLogEntities) {
                list.add(transferEntityToVo(oblContentLikeLogEntity));
            }
            return list;
        }
    }

    public static List<OblContentLikeLogVo> transferDtoToVoList(List<OblContentLikeLogDto> oblContentLikeLogDtos) {
        if (oblContentLikeLogDtos == null) {
            return null;
        } else {
            List<OblContentLikeLogVo> list = new ArrayList<>();
            for (OblContentLikeLogDto oblContentLikeLogDto : oblContentLikeLogDtos) {
                list.add(transferDtoToVo(oblContentLikeLogDto));
            }
            return list;
        }
    }

}