package com.egg.manager.facade.persistence.em.define.pojo.transfer;

import com.egg.manager.facade.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineMenuDto;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap.EmDefineMenuMapstruct;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineMenuVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("defineMenuTransfer")
public class EmDefineMenuTransfer extends BaseMysqlTransfer {
    static EmDefineMenuMapstruct emDefineMenuMapstruct = EmDefineMenuMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmDefineMenuEntity transferVoToEntity(EmDefineMenuVo vo) {
        if (vo == null) {
            return null;
        }
        EmDefineMenuEntity entity = emDefineMenuMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmDefineMenuVo transferEntityToVo(EmDefineMenuEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefineMenuVo vo = emDefineMenuMapstruct.transferEntityToVo(entity);
        String excelModelConf = entity.getExcelModelConf();
        vo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmDefineMenuVo transferDtoToVo(EmDefineMenuDto dto) {
        if (dto == null) {
            return null;
        }
        EmDefineMenuVo vo = emDefineMenuMapstruct.transferDtoToVo(dto);
        String excelModelConf = dto.getExcelModelConf();
        vo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        return vo;
    }

    public static List<EmDefineMenuVo> transferEntityToVoList(List<EmDefineMenuEntity> defineMenuEntities) {
        if (defineMenuEntities == null) {
            return null;
        } else {
            List<EmDefineMenuVo> list = new ArrayList<>();
            for (EmDefineMenuEntity emDefineMenuEntity : defineMenuEntities) {
                list.add(transferEntityToVo(emDefineMenuEntity));
            }
            return list;
        }
    }


    public static List<EmDefineMenuVo> transferDtoToVoList(List<EmDefineMenuDto> emDefineMenuDtos) {
        if (emDefineMenuDtos == null) {
            return null;
        } else {
            List<EmDefineMenuVo> list = new ArrayList<>();
            for (EmDefineMenuDto emDefineMenuDto : emDefineMenuDtos) {
                list.add(transferDtoToVo(emDefineMenuDto));
            }
            return list;
        }
    }

}
