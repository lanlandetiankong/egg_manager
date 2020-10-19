package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.define.DefineDepartmentFuncModuleConstant;
import com.egg.manager.api.services.basic.define.DefineDepartmentService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineDepartmentTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineDepartmentVo;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-部门定义接口")
@RestController
@RequestMapping("/define/define_department")
public class DefineDepartmentController extends BaseController {

    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;
    @Reference
    private DefineDepartmentService defineDepartmentService;


    @PcWebQueryLog(action = "分页查询(dto)->部门定义",fullPath = "/define/define_department/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->部门定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefineDepartmentVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj
            , @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineDepartmentVo> result = MyCommonResult.gainQueryResult(DefineDepartmentVo.class,DefineDepartmentFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineDepartmentDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineDepartmentDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineDepartmentService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->部门定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(action = "根据id查询->部门定义",fullPath = "/define/define_department/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefineDepartmentVo> queryOneById(HttpServletRequest request, String defineDepartmentId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineDepartmentVo> result = MyCommonResult.gainQueryResult(DefineDepartmentVo.class,DefineDepartmentFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            Assert.notBlank(defineDepartmentId,BaseRstMsgConstant.ErrorMsg.unknowId());

            DefineDepartment defineDepartment = defineDepartmentService.getById(defineDepartmentId);
            result.setBean(DefineDepartmentTransfer.transferEntityToVo(defineDepartment));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }

    @PcWebQueryLog(action = "查询下拉树->部门定义",fullPath = "/define/define_department/queryTreeSelect")
    @ApiOperation(value = "查询下拉树->部门定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryTreeSelect")
    public MyCommonResult<CommonTreeSelect> queryTreeSelect(@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<CommonTreeSelect> result = MyCommonResult.gainQueryResult(CommonTreeSelect.class,DefineDepartmentFuncModuleConstant.Success.QUERY_TREE_SELECT);
        try{
            //筛选与排序
            QueryWrapper<DefineDepartment> queryWrapper = new QueryWrapper<DefineDepartment>();
            queryWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
            queryWrapper.orderBy(true,true,"level");
            queryWrapper.orderBy(true,true,"order_num");
            queryWrapper.orderBy(true,true,"create_time");
            List<DefineDepartment> allDepartments = defineDepartmentMapper.selectList(queryWrapper);
            List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(loginUser, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartments);
            result.setResultList(treeList);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.QUERY_TREE_SELECT);
        }
        return result;
    }

    @PcWebQueryLog(action = "筛选查询下拉树->部门定义", description = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", fullPath = "/define/define_department/queryFilteredTreeSelect")
    @ApiOperation(value = "筛选查询下拉树->部门定义", notes = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryFilteredTreeSelect")
    public MyCommonResult<CommonTreeSelect> queryFilteredTreeSelect(String filterId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<CommonTreeSelect> result = MyCommonResult.gainQueryResult(CommonTreeSelect.class,DefineDepartmentFuncModuleConstant.Success.QUERY_TREE_SELECT);
        try{
            List<DefineDepartment> allDepartment = defineDepartmentMapper.getDepartmentFilterChildrens(filterId, true);
            List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(loginUser, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartment);
            result.setResultList(treeList);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.QUERY_TREE_SELECT);
        }
        return result;
    }

    @ApiOperation(value = "新增->部门定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "新增->部门定义",fullPath = "/define/define_department/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefineDepartmentVo defineDepartmentVo,
                                                                    @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineDepartmentFuncModuleConstant.Success.CREATE_OPER);
        Integer addCount = 0;
        try {
            Assert.notNull(defineDepartmentVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            addCount = defineDepartmentService.dealCreate(loginUser, defineDepartmentVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.CREATE_OPER);
        }
        return result;
    }


    @ApiOperation(value = "更新->部门定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "更新->部门定义",fullPath = "/define/define_department/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefineDepartmentVo defineDepartmentVo,
                                                   @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineDepartmentFuncModuleConstant.Success.UPDATE_OPER);
        Integer changeCount = 0;
        try {
            Assert.notNull(defineDepartmentVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineDepartmentService.dealUpdate(loginUser, defineDepartmentVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量伪删除->部门定义",fullPath = "/define/define_department/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->部门定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineDepartmentFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = defineDepartmentService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }


    @PcWebOperationLog(action = "伪删除->部门定义",fullPath = "/define/define_department/deleteById")
    @ApiOperation(value = "伪删除->部门定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "指定删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineDepartmentFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Integer delCount = defineDepartmentService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineDepartmentFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }

}
