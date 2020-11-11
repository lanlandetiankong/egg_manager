package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.DefineMenuMapstruct;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineMenuVo;
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
public class DefineMenuTransfer extends BaseMysqlTransfer {
    static DefineMenuMapstruct defineMenuMapstruct = DefineMenuMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static DefineMenuEntity transferVoToEntity(DefineMenuVo vo) {
        if (vo == null) {
            return null;
        }
        DefineMenuEntity entity = defineMenuMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineMenuVo transferEntityToVo(DefineMenuEntity entity) {
        if (entity == null) {
            return null;
        }
        DefineMenuVo vo = defineMenuMapstruct.transferEntityToVo(entity);
        String excelModelConf = entity.getExcelModelConf();
        vo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static DefineMenuVo transferDtoToVo(DefineMenuDto dto) {
        if (dto == null) {
            return null;
        }
        DefineMenuVo vo = defineMenuMapstruct.transferDtoToVo(dto);
        String excelModelConf = dto.getExcelModelConf();
        vo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        return vo;
    }

    public static List<DefineMenuVo> transferEntityToVoList(List<DefineMenuEntity> defineMenuEntities) {
        if (defineMenuEntities == null) {
            return null;
        } else {
            List<DefineMenuVo> list = new ArrayList<>();
            for (DefineMenuEntity defineMenuEntity : defineMenuEntities) {
                list.add(transferEntityToVo(defineMenuEntity));
            }
            return list;
        }
    }


    public static List<DefineMenuVo> transferDtoToVoList(List<DefineMenuDto> defineMenuDtos) {
        if (defineMenuDtos == null) {
            return null;
        } else {
            List<DefineMenuVo> list = new ArrayList<>();
            for (DefineMenuDto defineMenuDto : defineMenuDtos) {
                list.add(transferDtoToVo(defineMenuDto));
            }
            return list;
        }
    }

}