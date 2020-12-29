package com.egg.manager.facade.persistence.commons.base.beans.tree.common;

import com.egg.manager.facade.persistence.commons.base.beans.tree.MyBaseTree;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineDepartmentEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20 17:16
 */
public class CommonTreeSelectTranslate extends MyBaseTree {


    private static final long serialVersionUID = -6531490965620260001L;

    /**
     * 部门 树选择 ->entity 转 CommonTreeSelect
     * @param emDefineDepartmentEntity
     * @param tree
     * @return
     */
    public static CommonTreeSelect setDefineDepartmentParamToTreeSelect(EmDefineDepartmentEntity emDefineDepartmentEntity, CommonTreeSelect tree) {
        tree.setKey(emDefineDepartmentEntity.getFid());
        tree.setValue(emDefineDepartmentEntity.getFid());
        tree.setTitle(emDefineDepartmentEntity.getName());
        tree.setPid(emDefineDepartmentEntity.getPid());
        return tree;
    }


    /**
     * 菜单 树选择 ->entity 转 CommonTreeSelect
     * @param menu
     * @param tree
     * @return
     */
    public static CommonTreeSelect setDefineMenuParamToTreeSelect(EmDefineMenuEntity menu, CommonTreeSelect tree) {
        tree.setKey(menu.getFid());
        tree.setValue(menu.getFid());
        tree.setTitle(menu.getMenuName());
        tree.setPid(menu.getPid());
        return tree;
    }
}
