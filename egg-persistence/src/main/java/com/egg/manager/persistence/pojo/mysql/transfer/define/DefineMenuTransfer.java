package com.egg.manager.persistence.pojo.mysql.transfer.define;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineMenuDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.define.DefineMenuMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineMenuVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @version V1.0
 * @description:
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
    public static DefineMenu transferVoToEntity(DefineMenuVo vo) {
        if (vo == null) {
            return null;
        }
        DefineMenu entity = defineMenuMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineMenuVo transferEntityToVo(DefineMenu entity) {
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

    public static List<DefineMenuVo> transferEntityToVoList(List<DefineMenu> defineMenus) {
        if (defineMenus == null) {
            return null;
        } else {
            List<DefineMenuVo> list = new ArrayList<>();
            for (DefineMenu defineMenu : defineMenus) {
                list.add(transferEntityToVo(defineMenu));
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
