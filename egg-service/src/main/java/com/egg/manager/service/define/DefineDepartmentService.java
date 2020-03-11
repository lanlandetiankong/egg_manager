package com.egg.manager.service.define;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.web.tree.CommonTreeSelect;
import com.egg.manager.entity.define.DefineDepartment;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.define.DefineDepartmentVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineDepartmentService extends IService<DefineDepartment> {

    /**
     * 分页查询 部门
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineDepartmentPages(MyCommonResult<DefineDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                      List<AntdvSortBean> sortBeans);


    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构
     * 用于 a-tree-select
     * @param rootId
     * @param allDepartments
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(String rootId,List<DefineDepartment> allDepartments);

    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构(包含最顶层)
     * 用于 a-tree-select
     * @param rootId
     * @param allDefineDepartments
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(String rootId, List<DefineDepartment> allDefineDepartments);

    /**
     * 部门定义-新增
     * @param defineDepartmentVo
     * @throws Exception
     */
    Integer dealAddDefineDepartment(DefineDepartmentVo defineDepartmentVo,UserAccount loginUser) throws Exception ;

    /**
     * 部门定义-更新
     * @param defineDepartmentVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefineDepartment(DefineDepartmentVo defineDepartmentVo,UserAccount loginUser,boolean updateAll) throws Exception ;

    /**
     * 部门定义-批量删除
     * @param delIds 要删除的部门id 集合
     * @throws Exception
     */
    Integer dealDelDefineDepartmentByArr(String[] delIds,UserAccount loginUser) throws Exception;

    /**
     * 部门定义-删除
     * @param delId 要删除的部门id
     * @throws Exception
     */
    Integer dealDelDefineDepartment(String delId,UserAccount loginUser) throws Exception;
}
