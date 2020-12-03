package com.egg.manager.persistence.obl.article.pojo.transfer;


import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserDefCollectCategoryEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserDefCollectCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.mapstruct.imap.OblUserDefCollectCategoryMapstruct;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserDefCollectCategoryVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 用户定义的收藏类别-Transfer
 * @date 2020-12-03
 */
@Component
@Named("oblUserDefCollectCategoryTransfer")
public class OblUserDefCollectCategoryTransfer extends BaseMysqlTransfer {
    static OblUserDefCollectCategoryMapstruct oblUserDefCollectCategoryMapstruct = OblUserDefCollectCategoryMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblUserDefCollectCategoryEntity transferVoToEntity(OblUserDefCollectCategoryVo vo) {
        if (vo == null) {
            return null;
        }
        OblUserDefCollectCategoryEntity entity = oblUserDefCollectCategoryMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblUserDefCollectCategoryVo transferEntityToVo(OblUserDefCollectCategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        OblUserDefCollectCategoryVo vo = oblUserDefCollectCategoryMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblUserDefCollectCategoryVo transferDtoToVo(OblUserDefCollectCategoryDto dto) {
        if (dto == null) {
            return null;
        }
        OblUserDefCollectCategoryVo vo = oblUserDefCollectCategoryMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblUserDefCollectCategoryVo> transferEntityToVoList(List<OblUserDefCollectCategoryEntity> oblUserDefCollectCategoryEntities) {
        if (oblUserDefCollectCategoryEntities == null) {
            return null;
        } else {
            List<OblUserDefCollectCategoryVo> list = new ArrayList<>();
            for (OblUserDefCollectCategoryEntity oblUserDefCollectCategoryEntity : oblUserDefCollectCategoryEntities) {
                list.add(transferEntityToVo(oblUserDefCollectCategoryEntity));
            }
            return list;
        }
    }

    public static List<OblUserDefCollectCategoryVo> transferDtoToVoList(List<OblUserDefCollectCategoryDto> oblUserDefCollectCategoryDtos) {
        if (oblUserDefCollectCategoryDtos == null) {
            return null;
        } else {
            List<OblUserDefCollectCategoryVo> list = new ArrayList<>();
            for (OblUserDefCollectCategoryDto oblUserDefCollectCategoryDto : oblUserDefCollectCategoryDtos) {
                list.add(transferDtoToVo(oblUserDefCollectCategoryDto));
            }
            return list;
        }
    }

}