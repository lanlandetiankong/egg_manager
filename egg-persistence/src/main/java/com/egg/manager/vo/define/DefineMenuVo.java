package com.egg.manager.vo.define;

import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.entity.define.DefineMenu;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineMenuVo {
    private String fid ;
    private String parentId ;
    private String menuName ;
    private String iconName ;
    private String routerUrl ;
    private String hrefUrl ;
    private Integer urlJumpType;
    private String urlJumpTypeStr;
    private String label ;
    private Integer level ;
    private Integer orderNum ;

    private Integer state ;
    private String remark ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;




    public static DefineMenu transferVoToEntity(DefineMenuVo defineMenuVo) {
        if(defineMenuVo == null){
            return null ;
        }
        DefineMenu defineMenu = new DefineMenu() ;
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

        defineMenu.setState(defineMenuVo.getState());
        defineMenu.setCreateTime(defineMenuVo.getCreateTime());
        defineMenu.setUpdateTime(defineMenuVo.getUpdateTime());
        defineMenu.setCreateUser(defineMenuVo.getCreateUser());
        defineMenu.setLastModifyer(defineMenuVo.getLastModifyer());
        defineMenu.setRemark(defineMenuVo.getRemark());
        return defineMenu ;
    }

    public static DefineMenuVo transferEntityToVo(DefineMenu defineMenu) {
        if(defineMenu == null){
            return null ;
        }
        DefineMenuVo defineMenuVo = new DefineMenuVo() ;
        defineMenuVo.setFid(defineMenu.getFid());
        defineMenuVo.setParentId(defineMenu.getParentId());
        defineMenuVo.setMenuName(defineMenu.getMenuName());
        defineMenuVo.setIconName(defineMenu.getIconName());
        defineMenuVo.setRouterUrl(defineMenu.getRouterUrl());
        defineMenuVo.setHrefUrl(defineMenu.getHrefUrl());
        defineMenuVo.setUrlJumpType(defineMenu.getUrlJumpType());
        if(defineMenu.getUrlJumpType() != null){
            DefineMenuUrlJumpTypeEnum typeEnum = DefineMenuUrlJumpTypeEnum.doGetEnumByValue(defineMenu.getUrlJumpType());
            if(typeEnum != null){
                defineMenuVo.setUrlJumpTypeStr(typeEnum.getLabel());
            }   else {
                defineMenuVo.setUrlJumpTypeStr("");
            }
        }
        defineMenuVo.setLabel(defineMenu.getLabel());
        defineMenuVo.setLevel(defineMenu.getLevel());
        defineMenuVo.setOrderNum(defineMenu.getOrderNum());

        defineMenuVo.setState(defineMenu.getState());
        defineMenuVo.setCreateTime(defineMenu.getCreateTime());
        defineMenuVo.setUpdateTime(defineMenu.getUpdateTime());
        defineMenuVo.setCreateUser(defineMenu.getCreateUser());
        defineMenuVo.setLastModifyer(defineMenu.getLastModifyer());
        defineMenuVo.setRemark(defineMenu.getRemark());
        return defineMenuVo ;
    }

    public static List<DefineMenuVo> transferEntityToVoList(List<DefineMenu> defineMenus){
        if(defineMenus == null){
            return null ;
        }   else {
            List<DefineMenuVo> list = new ArrayList<>() ;
            for (DefineMenu defineMenu : defineMenus){
                list.add(transferEntityToVo(defineMenu));
            }
            return list ;
        }
    }
}
