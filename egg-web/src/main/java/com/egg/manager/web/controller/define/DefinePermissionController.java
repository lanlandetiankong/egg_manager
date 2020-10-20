package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.define.DefinePermissionFuncModuleConstant;
import com.egg.manager.api.services.basic.define.DefinePermissionService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefinePermissionTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;
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
@Api(value = "API-权限定义接口")
@RestController
@RequestMapping("/define/definePermission")
public class DefinePermissionController extends BaseController {

    @Autowired
    private DefinePermissionMapper definePermissionMapper;

    @Reference
    private DefinePermissionService definePermissionService;


    @PcWebQueryLog(fullPath = "/define/definePermission/queryPage")
    @ApiOperation(value = "分页查询->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public MyCommonResult<DefinePermissionVo> queryPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class, DefinePermissionFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("ensure", SwitchStateEnum.Open.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefinePermission> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefinePermission.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = definePermissionService.dealQueryPageByEntitys(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }

    @PcWebQueryLog(fullPath = "/define/definePermission/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefinePermissionVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class,DefinePermissionFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefinePermissionDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefinePermissionDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = definePermissionService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/definePermission/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefinePermissionVo> queryOneById(HttpServletRequest request, String definePermissionId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class,DefinePermissionFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            DefinePermission definePermission = definePermissionMapper.selectById(definePermissionId);
            result.setBean(DefinePermissionTransfer.transferEntityToVo(definePermission));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }


    @ApiOperation(value = "新增->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/definePermission/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefinePermissionVo definePermissionVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.CREATE_OPER);
        Integer addCount = 0;
        try {
            Assert.notNull(definePermissionVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            addCount = definePermissionService.dealCreate(loginUser, definePermissionVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.CREATE_OPER);
        }
        return result;
    }


    @ApiOperation(value = "更新->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/definePermission/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefinePermissionVo definePermissionVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.UPDATE_OPER);
        Integer changeCount = 0;
        try {
            Assert.notNull(definePermissionVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = definePermissionService.dealUpdate(loginUser, definePermissionVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/define/definePermission/batchEnsureByIds")
    @ApiOperation(value = "更新/批量启用->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要启用的权限定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchEnsureByIds")
    public MyCommonResult batchEnsureByIds(HttpServletRequest request, String[] ensureIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.BATCH_ENSURE);
        Integer delCount = 0;
        try {
            Assert.notEmpty(ensureIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = definePermissionService.dealBatchEnsure(loginUser, ensureIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.BATCH_ENSURE);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/define/definePermission/deleteById")
    @ApiOperation(value = "伪删除->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = definePermissionService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
            if (new Integer(0).equals(delCount)) {
                //如果删除的是 [已启用的]，则抛出异常
                throw new BusinessException("删除权限定义:" + actionFailMsg + PublicResultEnum.SwitchOpenChangeLimit.getLabel());
            }
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }
    @PcWebOperationLog(fullPath = "/define/definePermission/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->权限定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = definePermissionService.dealBatchDelete(loginUser, delIds);
            if (delIds.length > delCount) {
                result.setMsg("由于部分权限已经确认启用后无法删除！预计删除" + delIds.length + "条数据，实际删除" + delCount + "条数据。");
            }
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }
}
