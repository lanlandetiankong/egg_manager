package com.egg.manager.common.web.tree;

import com.egg.manager.entity.define.DefineDepartment;
import lombok.*;

import java.util.List;


public class CommonTreeSelectTranslate {


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
        return tree;
    }
}
