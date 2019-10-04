package com.egg.manager.controller.define;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.constant.define.DefineMenuConstant;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.tree.CommonTree;
import com.egg.manager.entity.define.DefineMenu;
import com.egg.manager.mapper.define.DefineMenuMapper;
import com.egg.manager.service.define.DefineMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.runtime.directive.Define;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/define/define_menu")
public class DefineMenuController {
    @Autowired
    private DefineMenuService defineMenuService ;




    @PostMapping("/get/all_menu")
    public MyCommonResult<DefineMenu> doGetAllMenu() {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<DefineMenu> allMenus  = defineMenuService.selectList(null);

        List<CommonTree> treeList = getChildNodes(DefineMenuConstant.ROOT_ID,allMenus);

        result.setResultList(treeList);
        return result ;
    }

    private List<CommonTree> getChildNodes(String id, List<DefineMenu> allMenus) {
        if(allMenus == null || allMenus.size() == 0){
            return null ;
        }
        List<CommonTree> childList = new ArrayList<CommonTree>() ;
        CommonTree tree = null ;
        for (DefineMenu menu : allMenus) {
            if(StringUtils.isNotBlank(menu.getParentId())){
                if(id != null){
                    if(id.equals(menu.getParentId())){
                        tree = new CommonTree() ;
                        childList.add(setDefineMenuParamToTree(menu,tree)) ;
                    }
                }
            }
        }
        if(childList.size() == 0) {
            return null ;
        }
        for (CommonTree treeItem : childList) {
            treeItem.setChildren(getChildNodes(treeItem.getId(),allMenus));
        }
        return childList ;
    }

    private CommonTree setDefineMenuParamToTree(DefineMenu menu,CommonTree tree) {
        tree.setId(menu.getFid());
        tree.setPid(menu.getParentId());
        tree.setName(menu.getMenuName());
        tree.setIconName(menu.getIconName());
        tree.setModuleId(menu.getDefineModuleId());
        tree.setLabel(menu.getLabel());
        tree.setKey(menu.getFid());
        tree.setPath(menu.getRouterUrl());
        return tree;
    }







}
