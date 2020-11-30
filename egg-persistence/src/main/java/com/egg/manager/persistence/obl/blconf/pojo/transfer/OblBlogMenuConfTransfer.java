package com.egg.manager.persistence.obl.blconf.pojo.transfer;

import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogMenuConfEntity;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogMenuConfDto;
import com.egg.manager.persistence.obl.blconf.pojo.mapstruct.imap.OblBlogMenuConfMapstruct;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogMenuConfVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 博客菜单定义表-Transfer
 * @date 2020-11-30
 */
@Component
@Named("oblBlogMenuConfTransfer")
public class OblBlogMenuConfTransfer extends BaseMysqlTransfer {
    static OblBlogMenuConfMapstruct oblBlogMenuConfMapstruct = OblBlogMenuConfMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static OblBlogMenuConfEntity transferVoToEntity(OblBlogMenuConfVo vo) {
        if (vo == null) {
            return null;
        }
        OblBlogMenuConfEntity entity = oblBlogMenuConfMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static OblBlogMenuConfVo transferEntityToVo(OblBlogMenuConfEntity entity) {
        if (entity == null) {
            return null;
        }
        OblBlogMenuConfVo vo = oblBlogMenuConfMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static OblBlogMenuConfVo transferDtoToVo(OblBlogMenuConfDto dto) {
        if (dto == null) {
            return null;
        }
        OblBlogMenuConfVo vo = oblBlogMenuConfMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<OblBlogMenuConfVo> transferEntityToVoList(List<OblBlogMenuConfEntity> oblBlogMenuConfEntities) {
        if (oblBlogMenuConfEntities == null) {
            return null;
        } else {
            List<OblBlogMenuConfVo> list = new ArrayList<>();
            for (OblBlogMenuConfEntity oblBlogMenuConfEntity : oblBlogMenuConfEntities) {
                list.add(transferEntityToVo(oblBlogMenuConfEntity));
            }
            return list;
        }
    }

    public static List<OblBlogMenuConfVo> transferDtoToVoList(List<OblBlogMenuConfDto> oblBlogMenuConfDtos) {
        if (oblBlogMenuConfDtos == null) {
            return null;
        } else {
            List<OblBlogMenuConfVo> list = new ArrayList<>();
            for (OblBlogMenuConfDto oblBlogMenuConfDto : oblBlogMenuConfDtos) {
                list.add(transferDtoToVo(oblBlogMenuConfDto));
            }
            return list;
        }
    }

}