package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineDepartmentVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineDepartmentService extends IService<DefineDepartment>, MyBaseMysqlService<DefineDepartment, DefineDepartmentMapper, DefineDepartmentVo> {

    /**
     * 分页查询 部门 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineDepartmentVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineDepartmentDto> paginationBean,
                                                           List<AntdvSortBean> sortBeans);


    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构
     * 用于 a-tree-select
     * @param loginUser      当前登录用户
     * @param rootId
     * @param allDepartments
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(UserAccount loginUser, String rootId, List<DefineDepartment> allDepartments);

    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构(包含最顶层)
     * 用于 a-tree-select
     * @param loginUser            当前登录用户
     * @param rootId
     * @param allDefineDepartments
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(UserAccount loginUser, String rootId, List<DefineDepartment> allDefineDepartments);

    /**
     * 部门定义-新增
     * @param loginUser          当前登录用户
     * @param defineDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, DefineDepartmentVo defineDepartmentVo) throws Exception;

    /**
     * 部门定义-更新
     * @param loginUser          当前登录用户
     * @param defineDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, DefineDepartmentVo defineDepartmentVo) throws Exception;

    /**
     * 部门定义-删除
     * @param loginUser 当前登录用户
     * @param delId     要删除的部门id
     * @return
     * @throws Exception
     */
    Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception;
}
