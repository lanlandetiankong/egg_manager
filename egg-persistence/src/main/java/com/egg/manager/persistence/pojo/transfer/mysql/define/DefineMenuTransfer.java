package com.egg.manager.persistence.pojo.transfer.mysql.define;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineMenuDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define.DefineMenuVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineMenuVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("DefineMenuTransfer")
public class DefineMenuTransfer extends MyBaseMysqlTransfer {
    static DefineMenuVoMapstruct defineMenuVoMapstruct = DefineMenuVoMapstruct.INSTANCE ;

    public static DefineMenu transferVoToEntity(DefineMenuVo vo) {
        if (vo == null) {
            return null;
        }
        DefineMenu entity = defineMenuVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineMenuVo transferEntityToVo(DefineMenu entity) {
        if (entity == null) {
            return null;
        }
        DefineMenuVo vo = defineMenuVoMapstruct.transferEntityToVo(entity);
        String excelModelConf = entity.getExcelModelConf();
        vo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        return vo;
    }


    public static DefineMenuVo transferDtoToVo(DefineMenuDto dto) {
        if (dto == null) {
            return null;
        }
        DefineMenuVo vo = defineMenuVoMapstruct.transferDtoToVo(dto);
        //TODO

        String excelModelConf = dto.getExcelModelConf();
        vo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        vo.setParentMenu(DefineMenuTransfer.transferDtoToVo(dto.getParentMenuDto()));
        vo.setCreateUser(UserAccountTransfer.transferEntityToVo(dto.getCreateUser()));
        vo.setLastModifyer(UserAccountTransfer.transferEntityToVo(dto.getLastModifyer()));
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


    @Named("handleDefineMenuUrlJumpTypeGetLabel")
    public String handleDefineMenuUrlJumpTypeGetLabel(Integer type){
        if (type != null) {
            DefineMenuUrlJumpTypeEnum typeEnum = DefineMenuUrlJumpTypeEnum.doGetEnumByValue(type);
            if (typeEnum != null) {
                return typeEnum.getLabel();
            }
        }
        return "" ;
    }
}
