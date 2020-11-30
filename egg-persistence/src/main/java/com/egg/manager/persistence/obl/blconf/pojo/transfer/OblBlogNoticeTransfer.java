package com.egg.manager.persistence.obl.blconf.pojo.transfer;

import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogNoticeEntity;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogNoticeDto;
import com.egg.manager.persistence.obl.blconf.pojo.mapstruct.imap.OblBlogNoticeMapstruct;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogNoticeVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 博客通知表-Transfer
 * @date 2020-11-30
 */
@Component
@Named("oblBlogNoticeTransfer")
public class OblBlogNoticeTransfer extends BaseMysqlTransfer {
    static OblBlogNoticeMapstruct oblBlogNoticeMapstruct = OblBlogNoticeMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblBlogNoticeEntity transferVoToEntity(OblBlogNoticeVo vo) {
        if (vo == null) {
            return null;
        }
        OblBlogNoticeEntity entity = oblBlogNoticeMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblBlogNoticeVo transferEntityToVo(OblBlogNoticeEntity entity) {
        if (entity == null) {
            return null;
        }
        OblBlogNoticeVo vo = oblBlogNoticeMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblBlogNoticeVo transferDtoToVo(OblBlogNoticeDto dto) {
        if (dto == null) {
            return null;
        }
        OblBlogNoticeVo vo = oblBlogNoticeMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblBlogNoticeVo> transferEntityToVoList(List<OblBlogNoticeEntity> oblBlogNoticeEntities) {
        if (oblBlogNoticeEntities == null) {
            return null;
        } else {
            List<OblBlogNoticeVo> list = new ArrayList<>();
            for (OblBlogNoticeEntity oblBlogNoticeEntity : oblBlogNoticeEntities) {
                list.add(transferEntityToVo(oblBlogNoticeEntity));
            }
            return list;
        }
    }

    public static List<OblBlogNoticeVo> transferDtoToVoList(List<OblBlogNoticeDto> oblBlogNoticeDtos) {
        if (oblBlogNoticeDtos == null) {
            return null;
        } else {
            List<OblBlogNoticeVo> list = new ArrayList<>();
            for (OblBlogNoticeDto oblBlogNoticeDto : oblBlogNoticeDtos) {
                list.add(transferDtoToVo(oblBlogNoticeDto));
            }
            return list;
        }
    }

}