package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartmentEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefineDepartmentVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineDepartmentService extends IService<DefineDepartmentEntity>, MyBaseMysqlService<DefineDepartmentEntity, DefineDepartmentMapper, DefineDepartmentVo> {

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
    MyCommonResult<DefineDepartmentVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<DefineDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineDepartmentDto> paginationBean,
                                                           List<AntdvSortBean> sortBeans);


    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构
     * 用于 a-tree-select
     * @param loginUser      当前登录用户
     * @param rootId
     * @param allDepartments
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(UserAccountEntity loginUser, Long rootId, List<DefineDepartmentEntity> allDepartments);

    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构(包含最顶层)
     * 用于 a-tree-select
     * @param loginUser            当前登录用户
     * @param rootId
     * @param allDefineDepartmentEntities
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(UserAccountEntity loginUser, Long rootId, List<DefineDepartmentEntity> allDefineDepartmentEntities);

    /**
     * 部门定义-新增
     * @param loginUser          当前登录用户
     * @param defineDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccountEntity loginUser, DefineDepartmentVo defineDepartmentVo) throws Exception;

    /**
     * 部门定义-更新
     * @param loginUser          当前登录用户
     * @param defineDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccountEntity loginUser, DefineDepartmentVo defineDepartmentVo) throws Exception;

}