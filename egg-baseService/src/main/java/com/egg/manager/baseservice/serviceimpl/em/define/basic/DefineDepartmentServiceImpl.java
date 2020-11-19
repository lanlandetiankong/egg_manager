package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.DefineDepartmentService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelectTranslate;
import com.egg.manager.persistence.commons.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartmentEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineDepartmentTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineDepartmentVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineDepartmentService.class)
public class DefineDepartmentServiceImpl extends MyBaseMysqlServiceImpl<DefineDepartmentMapper, DefineDepartmentEntity, DefineDepartmentVo>
        implements DefineDepartmentService {
    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryField> queryFieldList, AntdvPaginationBean<DefineDepartmentDto> paginationBean,
                                         AntdvSortMap sortMap) {
        Page<DefineDepartmentDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineDepartmentDto> defineDepartmentDtoList = defineDepartmentMapper.selectQueryPage(mpPagination, queryFieldList, sortMap);
        result.settingPage(paginationBean, mpPagination.getTotal());
        result.putResultList(DefineDepartmentTransfer.transferDtoToVoList(defineDepartmentDtoList));
        return result;
    }


    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodes(CurrentLoginUserInfo loginUserInfo, String rootId, List<DefineDepartmentEntity> allDepartments) {
        if (allDepartments == null || allDepartments.size() == 0) {
            return null;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>();
        CommonTreeSelect tree = null;
        for (DefineDepartmentEntity defineDepartmentEntity : allDepartments) {
            if (StringUtils.isNotBlank(defineDepartmentEntity.getParentId())) {
                if (rootId != null) {
                    if (rootId.equals(defineDepartmentEntity.getParentId())) {
                        tree = new CommonTreeSelect();
                        childList.add(CommonTreeSelectTranslate.setDefineDepartmentParamToTreeSelect(defineDepartmentEntity, tree));
                    }
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        for (CommonTreeSelect treeItem : childList) {
            treeItem.setChildren(this.getTreeSelectChildNodes(loginUserInfo, treeItem.getKey(), allDepartments));
        }
        return childList;
    }

    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(CurrentLoginUserInfo loginUserInfo, String rootId, List<DefineDepartmentEntity> allDefineDepartmentEntities) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(loginUserInfo, rootId, allDefineDepartmentEntities);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).title("部门首层项").value(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>();
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineDepartmentVo defineDepartmentVo) throws Exception {
        DefineDepartmentEntity defineDepartmentEntity = DefineDepartmentTransfer.transferVoToEntity(defineDepartmentVo);
        defineDepartmentEntity = super.doBeforeCreate(loginUserInfo, defineDepartmentEntity);
        String parentId = defineDepartmentEntity.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
            DefineDepartmentEntity parentDepartment = defineDepartmentMapper.selectById(parentId);
            Integer parentLevel = null;
            if (parentDepartment != null) {
                parentLevel = parentDepartment.getLevel();
            }
            if (parentLevel != null) {
                defineDepartmentEntity.setLevel(parentLevel + 1);
            } else {
                defineDepartmentEntity.setLevel(parentLevel);
            }
        } else {
            defineDepartmentEntity.setParentId(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            defineDepartmentEntity.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        return defineDepartmentMapper.insert(defineDepartmentEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineDepartmentVo defineDepartmentVo) throws Exception {
        Integer changeCount = 0;
        DefineDepartmentEntity defineDepartmentEntity = DefineDepartmentTransfer.transferVoToEntity(defineDepartmentVo);
        defineDepartmentEntity = super.doBeforeUpdate(loginUserInfo, defineDepartmentEntity);
        String parentId = defineDepartmentEntity.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
            DefineDepartmentEntity parentDepartment = defineDepartmentMapper.selectById(parentId);
            Integer parentLevel = null;
            if (parentDepartment != null) {
                parentLevel = parentDepartment.getLevel();
            }
            if (parentLevel != null) {
                defineDepartmentEntity.setLevel(parentLevel + 1);
            } else {
                defineDepartmentEntity.setLevel(parentLevel);
            }
        } else {
            defineDepartmentEntity.setParentId(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            defineDepartmentEntity.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        changeCount = defineDepartmentMapper.updateById(defineDepartmentEntity);
        return changeCount;
    }

}
