package com.egg.manager.vo.module;

import com.egg.manager.entity.module.DefineMenu;
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
    private String defineModuleId ;
    private String parentId ;
    private String menuName ;
    private String type;
    private String iconName ;
    private String routerUrl ;
    private String label ;
    private Integer level ;
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
        defineMenu.setDefineModuleId(defineMenuVo.getDefineModuleId());
        defineMenu.setParentId(defineMenuVo.getParentId());
        defineMenu.setMenuName(defineMenuVo.getMenuName());
        defineMenu.setType(defineMenuVo.getType());
        defineMenu.setIconName(defineMenuVo.getIconName());
        defineMenu.setRouterUrl(defineMenuVo.getRouterUrl());
        defineMenu.setLabel(defineMenuVo.getLabel());

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
        defineMenuVo.setDefineModuleId(defineMenu.getDefineModuleId());
        defineMenuVo.setParentId(defineMenu.getParentId());
        defineMenuVo.setMenuName(defineMenu.getMenuName());
        defineMenuVo.setType(defineMenu.getType());
        defineMenuVo.setIconName(defineMenu.getIconName());
        defineMenuVo.setRouterUrl(defineMenu.getRouterUrl());
        defineMenuVo.setLabel(defineMenu.getLabel());

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
