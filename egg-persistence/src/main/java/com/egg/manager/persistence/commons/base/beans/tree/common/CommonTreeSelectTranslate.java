package com.egg.manager.persistence.commons.base.beans.tree.common;

import com.egg.manager.persistence.commons.base.beans.tree.MyBaseTree;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartmentEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20 17:16
 */
public class CommonTreeSelectTranslate extends MyBaseTree {


    private static final long serialVersionUID = -6531490965620260001L;

    /**
     * 部门 树选择 ->entity 转 CommonTreeSelect
     * @param defineDepartmentEntity
     * @param tree
     * @return
     */
    public static CommonTreeSelect setDefineDepartmentParamToTreeSelect(DefineDepartmentEntity defineDepartmentEntity, CommonTreeSelect tree) {
        tree.setKey(defineDepartmentEntity.getFid());
        tree.setValue(defineDepartmentEntity.getFid());
        tree.setTitle(defineDepartmentEntity.getName());
        tree.setParentId(defineDepartmentEntity.getParentId());
        return tree;
    }


    /**
     * 菜单 树选择 ->entity 转 CommonTreeSelect
     * @param menu
     * @param tree
     * @return
     */
    public static CommonTreeSelect setDefineMenuParamToTreeSelect(DefineMenuEntity menu, CommonTreeSelect tree) {
        tree.setKey(menu.getFid());
        tree.setValue(menu.getFid());
        tree.setTitle(menu.getMenuName());
        tree.setParentId(menu.getParentId());
        return tree;
    }
}
