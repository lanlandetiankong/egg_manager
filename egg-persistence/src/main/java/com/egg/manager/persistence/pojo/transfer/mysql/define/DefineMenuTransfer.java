package com.egg.manager.persistence.pojo.transfer.mysql.define;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineMenuDto;
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
    public static DefineMenu transferVoToEntity(DefineMenuVo defineMenuVo) {
        if (defineMenuVo == null) {
            return null;
        }
        DefineMenu defineMenu = new DefineMenu();
        defineMenu.setFid(defineMenuVo.getFid());
        defineMenu.setParentId(defineMenuVo.getParentId());
        defineMenu.setMenuName(defineMenuVo.getMenuName());
        defineMenu.setUrlJumpType(defineMenuVo.getUrlJumpType());
        defineMenu.setIconName(defineMenuVo.getIconName());
        defineMenu.setRouterUrl(defineMenuVo.getRouterUrl());
        defineMenu.setHrefUrl(defineMenuVo.getHrefUrl());
        defineMenu.setLabel(defineMenuVo.getLabel());
        defineMenu.setLevel(defineMenuVo.getLevel());
        defineMenu.setOrderNum(defineMenuVo.getOrderNum());
        defineMenu.setExcelModelConf(defineMenuVo.getExcelModelConf());
        defineMenu.setRemark(defineMenuVo.getRemark());
        defineMenu.setState(defineMenuVo.getState());
        defineMenu.setCreateTime(defineMenuVo.getCreateTime());
        defineMenu.setUpdateTime(defineMenuVo.getUpdateTime());
        defineMenu.setCreateUserId(defineMenuVo.getCreateUserId());
        defineMenu.setLastModifyerId(defineMenuVo.getLastModifyerId());

        return defineMenu;
    }

    public static DefineMenuVo transferEntityToVo(DefineMenu defineMenu) {
        if (defineMenu == null) {
            return null;
        }
        DefineMenuVo defineMenuVo = new DefineMenuVo();
        defineMenuVo.setFid(defineMenu.getFid());
        defineMenuVo.setParentId(defineMenu.getParentId());
        defineMenuVo.setMenuName(defineMenu.getMenuName());
        defineMenuVo.setIconName(defineMenu.getIconName());
        defineMenuVo.setRouterUrl(defineMenu.getRouterUrl());
        defineMenuVo.setHrefUrl(defineMenu.getHrefUrl());
        defineMenuVo.setUrlJumpType(defineMenu.getUrlJumpType());
        if (defineMenu.getUrlJumpType() != null) {
            DefineMenuUrlJumpTypeEnum typeEnum = DefineMenuUrlJumpTypeEnum.doGetEnumByValue(defineMenu.getUrlJumpType());
            if (typeEnum != null) {
                defineMenuVo.setUrlJumpTypeStr(typeEnum.getLabel());
            } else {
                defineMenuVo.setUrlJumpTypeStr("");
            }
        }
        defineMenuVo.setLabel(defineMenu.getLabel());
        defineMenuVo.setLevel(defineMenu.getLevel());
        defineMenuVo.setOrderNum(defineMenu.getOrderNum());
        String excelModelConf = defineMenu.getExcelModelConf();
        defineMenuVo.setExcelModelConf(excelModelConf);
        defineMenuVo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        defineMenuVo.setRemark(defineMenu.getRemark());
        defineMenuVo.setState(defineMenu.getState());
        defineMenuVo.setCreateTime(defineMenu.getCreateTime());
        defineMenuVo.setUpdateTime(defineMenu.getUpdateTime());
        defineMenuVo.setCreateUserId(defineMenu.getCreateUserId());
        defineMenuVo.setLastModifyerId(defineMenu.getLastModifyerId());
        return defineMenuVo;
    }


    public static DefineMenuVo transferDtoToVo(DefineMenuDto defineMenuDto) {
        if (defineMenuDto == null) {
            return null;
        }
        DefineMenuVo defineMenuVo = new DefineMenuVo();
        defineMenuVo.setFid(defineMenuDto.getFid());
        defineMenuVo.setParentId(defineMenuDto.getParentId());
        defineMenuVo.setParentMenu(DefineMenuTransfer.transferDtoToVo(defineMenuDto.getParentMenuDto()));
        defineMenuVo.setMenuName(defineMenuDto.getMenuName());
        defineMenuVo.setIconName(defineMenuDto.getIconName());
        defineMenuVo.setRouterUrl(defineMenuDto.getRouterUrl());
        defineMenuVo.setHrefUrl(defineMenuDto.getHrefUrl());
        defineMenuVo.setUrlJumpType(defineMenuDto.getUrlJumpType());
        if (defineMenuDto.getUrlJumpType() != null) {
            DefineMenuUrlJumpTypeEnum typeEnum = DefineMenuUrlJumpTypeEnum.doGetEnumByValue(defineMenuDto.getUrlJumpType());
            if (typeEnum != null) {
                defineMenuVo.setUrlJumpTypeStr(typeEnum.getLabel());
            } else {
                defineMenuVo.setUrlJumpTypeStr("");
            }
        }
        defineMenuVo.setLabel(defineMenuDto.getLabel());
        defineMenuVo.setLevel(defineMenuDto.getLevel());
        defineMenuVo.setOrderNum(defineMenuDto.getOrderNum());
        String excelModelConf = defineMenuDto.getExcelModelConf();
        defineMenuVo.setExcelModelConf(excelModelConf);
        defineMenuVo.dealAddAntdFileUploadBean(AntdFileUploadBean.dealJsonStrToBean(excelModelConf));
        defineMenuVo.setRemark(defineMenuDto.getRemark());
        defineMenuVo.setState(defineMenuDto.getState());
        defineMenuVo.setCreateTime(defineMenuDto.getCreateTime());
        defineMenuVo.setUpdateTime(defineMenuDto.getUpdateTime());
        defineMenuVo.setCreateUserId(defineMenuDto.getCreateUserId());
        defineMenuVo.setLastModifyerId(defineMenuDto.getLastModifyerId());
        defineMenuVo.setCreateUser(UserAccountTransfer.transferEntityToVo(defineMenuDto.getCreateUser()));
        defineMenuVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(defineMenuDto.getLastModifyer()));
        return defineMenuVo;
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
