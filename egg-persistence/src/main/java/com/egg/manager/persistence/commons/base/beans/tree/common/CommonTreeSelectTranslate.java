package com.egg.manager.persistence.commons.base.beans.tree.common;

import com.egg.manager.persistence.commons.base.beans.tree.MyBaseTree;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartment;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20 17:16
 */
public class CommonTreeSelectTranslate extends MyBaseTree {


    /**
     * 部门 树选择 ->entity 转 CommonTreeSelect
     * @param defineDepartment
     * @param tree
     * @return
     */
    public static CommonTreeSelect setDefineDepartmentParamToTreeSelect(DefineDepartment defineDepartment, CommonTreeSelect tree) {
        tree.setKey(defineDepartment.getFid());
        tree.setValue(defineDepartment.getFid());
        tree.setTitle(defineDepartment.getName());
        tree.setParentId(defineDepartment.getParentId());
        return tree;
    }


    /**
     * 菜单 树选择 ->entity 转 CommonTreeSelect
     * @param menu
     * @param tree
     * @return
     */
    public static CommonTreeSelect setDefineMenuParamToTreeSelect(DefineMenu menu, CommonTreeSelect tree) {
        tree.setKey(menu.getFid());
        tree.setValue(menu.getFid());
        tree.setTitle(menu.getMenuName());
        tree.setParentId(menu.getParentId());
        return tree;
    }
}
