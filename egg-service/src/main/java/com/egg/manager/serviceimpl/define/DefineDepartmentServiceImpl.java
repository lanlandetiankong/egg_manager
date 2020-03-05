package com.egg.manager.serviceimpl.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.common.web.tree.CommonTreeSelect;
import com.egg.manager.common.web.tree.CommonTreeSelectTranslate;
import com.egg.manager.entity.define.DefineDepartment;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.define.DefineDepartmentMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.define.DefineDepartmentService;
import com.egg.manager.vo.define.DefineDepartmentVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/03/05
 * \* Time:20:09
 * \* Description:
 * \
 */
@Service
public class DefineDepartmentServiceImpl extends ServiceImpl<DefineDepartmentMapper,DefineDepartment> implements DefineDepartmentService {
    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;



    /**
     * 分页查询 部门
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineDepartmentPages(MyCommonResult<DefineDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                             List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<DefineDepartment> defineDepartmentEntityWrapper = new EntityWrapper<DefineDepartment>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 defineDepartmentEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(defineDepartmentEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                defineDepartmentEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = defineDepartmentMapper.selectCount(defineDepartmentEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineDepartment> defineDepartments = defineDepartmentMapper.selectPage(rowBounds,defineDepartmentEntityWrapper) ;
        result.setResultList(DefineDepartmentVo.transferEntityToVoList(defineDepartments));
    }


    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构
     * 用于 a-tree-select
     * @param rootId
     * @param allDepartments
     * @return
     */
    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodes(String rootId,List<DefineDepartment> allDepartments) {
        if(allDepartments == null || allDepartments.size() == 0){
            return null ;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>() ;
        CommonTreeSelect tree = null ;
        for (DefineDepartment defineDepartment : allDepartments) {
            if(StringUtils.isNotBlank(defineDepartment.getParentId())){
                if(rootId != null){
                    if(rootId.equals(defineDepartment.getParentId())){
                        tree = new CommonTreeSelect() ;
                        childList.add(CommonTreeSelectTranslate.setDefineDepartmentParamToTreeSelect(defineDepartment,tree)) ;
                    }
                }
            }
        }
        if(childList.size() == 0) {
            return null ;
        }
        for (CommonTreeSelect treeItem : childList) {
            treeItem.setChildren(this.getTreeSelectChildNodes(treeItem.getKey(),allDepartments));
        }
        return childList ;
    }
    /**
     * [部门展示]的子节点 构建的 TreeSelect 结构(包含最顶层)
     * 用于 a-tree-select
     * @param rootId
     * @param allDefineDepartments
     * @return
     */
    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(String rootId,List<DefineDepartment> allDefineDepartments) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(rootId,allDefineDepartments);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).title("部门首层项").value(DefineDepartmentConstant.ROOT_DEPARTMENT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>() ;
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot ;
    }




    /**
     * 部门定义-新增
     * @param defineDepartmentVo
     * @throws Exception
     */
    @Override
    public Integer dealAddDefineDepartment(DefineDepartmentVo defineDepartmentVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineDepartment defineDepartment = DefineDepartmentVo.transferVoToEntity(defineDepartmentVo);
        String parentId = defineDepartment.getParentId() ;
        if(StringUtils.isNotBlank(parentId)){
            DefineDepartment parentDepartment =defineDepartmentMapper.selectById(parentId);
            Integer parentLevel = null ;
            if(parentDepartment != null){
                parentLevel = parentDepartment.getLevel();
            }
            if(parentLevel != null){
                defineDepartment.setLevel(parentLevel+1);
            }   else {
                defineDepartment.setLevel(parentLevel);
            }
        }   else {
            defineDepartment.setParentId(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            defineDepartment.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        defineDepartment.setFid(MyUUIDUtil.renderSimpleUUID());
        defineDepartment.setState(BaseStateEnum.ENABLED.getValue());
        defineDepartment.setCreateTime(now);
        defineDepartment.setUpdateTime(now);
        if(loginUser != null){
            defineDepartment.setCreateUser(loginUser.getFid());
            defineDepartment.setLastModifyer(loginUser.getFid());
        }
        Integer addCount = defineDepartmentMapper.insert(defineDepartment) ;
        return addCount ;
    }


    /**
     * 部门定义-更新
     * @param defineDepartmentVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateDefineDepartment(DefineDepartmentVo defineDepartmentVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineDepartmentVo.setUpdateTime(now);
        DefineDepartment defineDepartment = DefineDepartmentVo.transferVoToEntity(defineDepartmentVo);
        String parentId = defineDepartment.getParentId() ;
        if(StringUtils.isNotBlank(parentId)){
            DefineDepartment parentDepartment =defineDepartmentMapper.selectById(parentId);
            Integer parentLevel = null ;
            if(parentDepartment != null){
                parentLevel = parentDepartment.getLevel();
            }
            if(parentLevel != null){
                defineDepartment.setLevel(parentLevel+1);
            }   else {
                defineDepartment.setLevel(parentLevel);
            }
        }   else {
            defineDepartment.setParentId(DefineDepartmentConstant.ROOT_DEPARTMENT_ID);
            defineDepartment.setLevel(DefineDepartmentConstant.ROOT_LEVEL);
        }
        if(loginUser != null){
            defineDepartment.setLastModifyer(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = defineDepartmentMapper.updateAllColumnById(defineDepartment) ;
        }   else {
            changeCount = defineDepartmentMapper.updateById(defineDepartment) ;
        }
        return changeCount ;
    }

    /**
     * 部门定义-删除
     * @param delIds 要删除的部门id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineDepartmentByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineDepartmentMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 部门定义-删除
     * @param delId 要删除的部门id
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineDepartment(String delId,UserAccount loginUser) throws Exception{
        DefineDepartment defineDepartment = DefineDepartment.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineDepartment.setLastModifyer(loginUser.getFid());
        }
        Integer delCount = defineDepartmentMapper.updateById(defineDepartment);
        return delCount ;
    }
}
