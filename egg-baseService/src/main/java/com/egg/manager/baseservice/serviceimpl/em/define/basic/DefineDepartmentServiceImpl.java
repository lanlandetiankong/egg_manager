package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.define.basic.DefineDepartmentService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.util.LongUtils;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelectTranslate;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartment;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineDepartmentTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineDepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineDepartmentService.class)
public class DefineDepartmentServiceImpl extends MyBaseMysqlServiceImpl<DefineDepartmentMapper, DefineDepartment, DefineDepartmentVo>
        implements DefineDepartmentService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;
    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;


    @Override
    public MyCommonResult<DefineDepartmentVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineDepartmentDto> paginationBean,
                                                                  List<AntdvSortBean> sortBeans) {
        Page<DefineDepartmentDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineDepartmentDto> defineDepartmentDtoList = defineDepartmentMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineDepartmentTransfer.transferDtoToVoList(defineDepartmentDtoList));
        return result;
    }


    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodes(UserAccount loginUser, Long rootId, List<DefineDepartment> allDepartments) {
        if (allDepartments == null || allDepartments.size() == 0) {
            return null;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>();
        CommonTreeSelect tree = null;
        for (DefineDepartment defineDepartment : allDepartments) {
            if (LongUtils.isNotBlank(defineDepartment.getParentId())) {
                if (rootId != null) {
                    if (rootId.equals(defineDepartment.getParentId())) {
                        tree = new CommonTreeSelect();
                        childList.add(CommonTreeSelectTranslate.setDefineDepartmentParamToTreeSelect(defineDepartment, tree));
                    }
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        for (CommonTreeSelect treeItem : childList) {
            treeItem.setChildren(this.getTreeSelectChildNodes(loginUser, treeItem.getKey(), allDepartments));
        }
        return childList;
    }

    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(UserAccount loginUser, Long rootId, List<DefineDepartment> allDefineDepartments) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(loginUser, rootId, allDefineDepartments);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).title("部门首层项").value(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>();
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, DefineDepartmentVo defineDepartmentVo) throws Exception {
        DefineDepartment defineDepartment = DefineDepartmentTransfer.transferVoToEntity(defineDepartmentVo);
        defineDepartment = super.doBeforeCreate(loginUser, defineDepartment, true);
        Long parentId = defineDepartment.getParentId();
        if (LongUtils.isNotBlank(parentId)) {
            DefineDepartment parentDepartment = defineDepartmentMapper.selectById(parentId);
            Integer parentLevel = null;
            if (parentDepartment != null) {
                parentLevel = parentDepartment.getLevel();
            }
            if (parentLevel != null) {
                defineDepartment.setLevel(parentLevel + 1);
            } else {
                defineDepartment.setLevel(parentLevel);
            }
        } else {
            defineDepartment.setParentId(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            defineDepartment.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        return defineDepartmentMapper.insert(defineDepartment);
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, DefineDepartmentVo defineDepartmentVo) throws Exception {
        Integer changeCount = 0;
        DefineDepartment defineDepartment = DefineDepartmentTransfer.transferVoToEntity(defineDepartmentVo);
        defineDepartment = super.doBeforeUpdate(loginUser, defineDepartment);
        Long parentId = defineDepartment.getParentId();
        if (LongUtils.isNotBlank(parentId)) {
            DefineDepartment parentDepartment = defineDepartmentMapper.selectById(parentId);
            Integer parentLevel = null;
            if (parentDepartment != null) {
                parentLevel = parentDepartment.getLevel();
            }
            if (parentLevel != null) {
                defineDepartment.setLevel(parentLevel + 1);
            } else {
                defineDepartment.setLevel(parentLevel);
            }
        } else {
            defineDepartment.setParentId(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            defineDepartment.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        changeCount = defineDepartmentMapper.updateById(defineDepartment);
        return changeCount;
    }

}
