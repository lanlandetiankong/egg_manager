package com.egg.manager.api.services.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.EmDefineDepartmentService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelectTranslate;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineDepartmentEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineDepartmentTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineDepartmentVo;
import com.egg.manager.persistence.em.user.domain.constant.DefineDepartmentConstant;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
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
@Service(interfaceClass = EmDefineDepartmentService.class)
public class EmDefineDepartmentServiceImpl extends MyBaseMysqlServiceImpl<EmDefineDepartmentMapper, EmDefineDepartmentEntity, EmDefineDepartmentVo>
        implements EmDefineDepartmentService {
    @Autowired
    private EmDefineDepartmentMapper emDefineDepartmentMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineDepartmentDto> queryPageBean) {
        Page<EmDefineDepartmentDto> mpPagination = queryPageBean.toMpPage();
        List<EmDefineDepartmentDto> emDefineDepartmentDtoList = emDefineDepartmentMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean, mpPagination);
        result.putGridList(EmDefineDepartmentTransfer.transferDtoToVoList(emDefineDepartmentDtoList));
        return result;
    }


    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodes(CurrentLoginEmUserInfo loginUserInfo, String rootId, List<EmDefineDepartmentEntity> allDepartments) {
        if (allDepartments == null || allDepartments.size() == 0) {
            return null;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>();
        CommonTreeSelect tree = null;
        for (EmDefineDepartmentEntity emDefineDepartmentEntity : allDepartments) {
            if (StringUtils.isNotBlank(emDefineDepartmentEntity.getPid())) {
                if (rootId != null) {
                    if (rootId.equals(emDefineDepartmentEntity.getPid())) {
                        tree = new CommonTreeSelect();
                        childList.add(CommonTreeSelectTranslate.setDefineDepartmentParamToTreeSelect(emDefineDepartmentEntity, tree));
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
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(CurrentLoginEmUserInfo loginUserInfo, String rootId, List<EmDefineDepartmentEntity> allDefineDepartmentEntities) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(loginUserInfo, rootId, allDefineDepartmentEntities);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).title("部门首层项").value(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>();
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineDepartmentVo emDefineDepartmentVo) throws Exception {
        EmDefineDepartmentEntity emDefineDepartmentEntity = EmDefineDepartmentTransfer.transferVoToEntity(emDefineDepartmentVo);
        emDefineDepartmentEntity = super.doBeforeCreate(loginUserInfo, emDefineDepartmentEntity);
        String pid = emDefineDepartmentEntity.getPid();
        if (StringUtils.isNotBlank(pid)) {
            EmDefineDepartmentEntity parentDepartment = emDefineDepartmentMapper.selectById(pid);
            Integer parentLevel = null;
            if (parentDepartment != null) {
                parentLevel = parentDepartment.getLevel();
            }
            if (parentLevel != null) {
                emDefineDepartmentEntity.setLevel(parentLevel + 1);
            } else {
                emDefineDepartmentEntity.setLevel(parentLevel);
            }
        } else {
            emDefineDepartmentEntity.setPid(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            emDefineDepartmentEntity.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        return emDefineDepartmentMapper.insert(emDefineDepartmentEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineDepartmentVo emDefineDepartmentVo) throws Exception {
        Integer changeCount = 0;
        EmDefineDepartmentEntity emDefineDepartmentEntity = EmDefineDepartmentTransfer.transferVoToEntity(emDefineDepartmentVo);
        emDefineDepartmentEntity = super.doBeforeUpdate(loginUserInfo, emDefineDepartmentEntity);
        String pid = emDefineDepartmentEntity.getPid();
        if (StringUtils.isNotBlank(pid)) {
            EmDefineDepartmentEntity parentDepartment = emDefineDepartmentMapper.selectById(pid);
            Integer parentLevel = null;
            if (parentDepartment != null) {
                parentLevel = parentDepartment.getLevel();
            }
            if (parentLevel != null) {
                emDefineDepartmentEntity.setLevel(parentLevel + 1);
            } else {
                emDefineDepartmentEntity.setLevel(parentLevel);
            }
        } else {
            emDefineDepartmentEntity.setPid(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            emDefineDepartmentEntity.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        changeCount = emDefineDepartmentMapper.updateById(emDefineDepartmentEntity);
        return changeCount;
    }

    @Override
    public List<EmDefineDepartmentEntity> getDepartmentFilterChildrens(String filterId, boolean onlyEnable) {
        return this.baseMapper.getDepartmentFilterChildrens(filterId, onlyEnable);
    }

    @Override
    public EmDefineDepartmentDto selectOneDtoOfUserBelongDepartment(String userAccountId) {
        return this.baseMapper.selectOneDtoOfUserBelongDepartment(userAccountId);
    }

    /**
     * 根据用户id查询 所属的部门详情
     * @param userAccountId
     * @param departmentState
     * @return
     */
    @Override
    public EmDefineDepartmentEntity selectOneOfUserBelongDepartment(String userAccountId, Short departmentState) {
        return this.baseMapper.selectOneOfUserBelongDepartment(userAccountId, departmentState);
    }
}
