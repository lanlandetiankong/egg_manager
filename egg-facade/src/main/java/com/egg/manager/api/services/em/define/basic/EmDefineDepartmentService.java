package com.egg.manager.api.services.em.define.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineDepartmentEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineDepartmentVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineDepartmentService extends MyBaseMysqlService<EmDefineDepartmentEntity, EmDefineDepartmentMapper, EmDefineDepartmentVo> {

    /**
     * 分页查询 部门 dto列表
     * (查询的 Dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineDepartmentDto> queryPageBean);


    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构
     * 用于 a-tree-select
     * @param loginUserInfo  当前登录用户
     * @param rootId
     * @param allDepartments
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(CurrentLoginEmUserInfo loginUserInfo, String rootId, List<EmDefineDepartmentEntity> allDepartments);

    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构(包含最顶层)
     * 用于 a-tree-select
     * @param loginUserInfo               当前登录用户
     * @param rootId
     * @param allDefineDepartmentEntities
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(CurrentLoginEmUserInfo loginUserInfo, String rootId, List<EmDefineDepartmentEntity> allDefineDepartmentEntities);

    /**
     * 部门定义-新增
     * @param loginUserInfo        当前登录用户
     * @param emDefineDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineDepartmentVo emDefineDepartmentVo) throws Exception;

    /**
     * 部门定义-更新
     * @param loginUserInfo        当前登录用户
     * @param emDefineDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineDepartmentVo emDefineDepartmentVo) throws Exception;

    /**
     * 查询部门(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<EmDefineDepartmentEntity> getDepartmentFilterChildrens(String filterId, boolean onlyEnable);

    /**
     * 根据用户id查询 所属的部门详情
     * @param userAccountId
     * @return
     */
    EmDefineDepartmentDto selectOneDtoOfUserBelongDepartment(String userAccountId);

    /**
     * 根据用户id查询 所属的部门详情
     * @param userAccountId
     * @param departmentState
     * @return
     */
    EmDefineDepartmentEntity selectOneOfUserBelongDepartment(String userAccountId, Short departmentState);

}
