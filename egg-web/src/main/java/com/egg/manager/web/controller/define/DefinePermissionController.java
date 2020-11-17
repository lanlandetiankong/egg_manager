package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.define.basic.DefinePermissionService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.PublicResultEnum;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefinePermissionTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefinePermissionVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
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
 * @author zhoucj
 * @description
 * @date 2020/10/21
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
    @ApiOperation(value = "分页查询->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public WebResult queryPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("ensure", SwitchStateEnum.Open.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefinePermissionEntity> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefinePermissionEntity.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = definePermissionService.dealQueryPageByEntitys(loginUserInfo, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebQueryLog(fullPath = "/define/definePermission/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefinePermissionDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefinePermissionDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = definePermissionService.dealQueryPageByDtos(loginUserInfo, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/definePermission/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String definePermissionId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            DefinePermissionEntity definePermissionEntity = definePermissionMapper.selectById(definePermissionId);
            result.putBean(DefinePermissionTransfer.transferEntityToVo(definePermissionEntity));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/definePermission/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, DefinePermissionVo definePermissionVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        try {
            Assert.notNull(definePermissionVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            addCount = definePermissionService.dealCreate(loginUserInfo, definePermissionVo);
            result.putCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/definePermission/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, DefinePermissionVo definePermissionVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        try {
            Assert.notNull(definePermissionVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = definePermissionService.dealUpdate(loginUserInfo, definePermissionVo);
            result.putCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/define/definePermission/batchEnsureByIds")
    @ApiOperation(value = "更新/批量启用->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要启用的权限定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchEnsureByIds")
    public WebResult batchEnsureByIds(HttpServletRequest request, String[] ensureIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        try {
            Assert.notEmpty(ensureIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = definePermissionService.dealBatchEnsure(loginUserInfo, ensureIds);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/define/definePermission/deleteById")
    @ApiOperation(value = "伪删除->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = definePermissionService.dealLogicDeleteById(loginUserInfo, delId);
            result.putCount(delCount);
            if (new Integer(0).equals(delCount)) {
                //如果删除的是 [已启用的]，则抛出异常
                throw new BusinessException("删除权限定义:" + actionFailMsg + PublicResultEnum.SwitchOpenChangeLimit.getLabel());
            }
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/definePermission/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = definePermissionService.dealBatchLogicDelete(loginUserInfo, delIds);
            if (delIds.length > delCount) {
                result.putErrorMsg("由于部分权限已经确认启用后无法删除！预计删除" + delIds.length + "条数据，实际删除" + delCount + "条数据。");
            }
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
