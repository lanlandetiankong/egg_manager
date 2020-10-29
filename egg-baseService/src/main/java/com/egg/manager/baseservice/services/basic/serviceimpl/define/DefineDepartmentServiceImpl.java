package com.egg.manager.baseservice.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.define.DefineDepartmentService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelectTranslate;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineDepartmentTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineDepartmentVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<CommonTreeSelect> getTreeSelectChildNodes(UserAccount loginUser, String rootId, List<DefineDepartment> allDepartments) {
        if (allDepartments == null || allDepartments.size() == 0) {
            return null;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>();
        CommonTreeSelect tree = null;
        for (DefineDepartment defineDepartment : allDepartments) {
            if (StringUtils.isNotBlank(defineDepartment.getParentId())) {
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
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(UserAccount loginUser, String rootId, List<DefineDepartment> allDefineDepartments) {
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
        String parentId = defineDepartment.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
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
        String parentId = defineDepartment.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
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
