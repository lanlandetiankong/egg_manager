package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.api.services.em.user.basic.DefineTenantService;
import com.egg.manager.persistence.em.user.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.user.pojo.dto.DefineTenantDto;
import com.egg.manager.persistence.em.user.pojo.transfer.DefineTenantTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.DefineTenantVo;
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
import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * @author: \* Date: 2019/9/14
 * \* Time: 23:41
 * \
 */
@Slf4j
@Api(value = "API-租户定义接口")
@RestController
@RequestMapping("/organization/defineTenant")
public class DefineTenantController extends BaseController {

    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Reference
    private DefineTenantService defineTenantService;


    @PcWebQueryLog(fullPath = "/organization/defineTenant/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefineTenantVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult<DefineTenantVo> result = MyCommonResult.gainQueryResult(DefineTenantVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineTenantDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineTenantDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineTenantService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/organization/defineTenant/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefineTenantVo> queryOneById(HttpServletRequest request, String defineTenantId, @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult<DefineTenantVo> result = MyCommonResult.gainQueryResult(DefineTenantVo.class);
        try {
            Assert.notBlank(defineTenantId, BaseRstMsgConstant.ErrorMsg.unknowId());
            DefineTenantEntity defineTenantEntity = defineTenantMapper.selectById(defineTenantId);
            result.setBean(DefineTenantTransfer.transferEntityToVo(defineTenantEntity));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebQueryLog(fullPath = "/organization/defineTenant/gainEnumSelect")
    @ApiOperation(value = "查询枚举下拉->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/gainEnumSelect")
    public MyCommonResult<DefineTenantVo> doGetAllDefineTenantEnums(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult<DefineTenantVo> result = MyCommonResult.gainQueryResult(DefineTenantVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>();
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineTenantService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, null, sortBeans);
            result = defineTenantService.dealResultListToEnums(loginUser, result);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/organization/defineTenant/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(defineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineTenantService.dealCreate(loginUser, defineTenantVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/organization/defineTenant/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(defineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineTenantService.dealUpdate(loginUser, defineTenantVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/organization/defineTenant/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser(required = false) UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = defineTenantService.dealBatchLogicDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/organization/defineTenant/deleteById")
    @ApiOperation(value = "伪删除->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
            Integer delCount = defineTenantService.dealLogicDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/organization/defineTenant/setupTenantManager")
    @ApiOperation(value = "更新->租户定义/设置管理员", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantId", value = "要配置的租户定义id", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "userAccountIdArr", value = "要设置为管理员的用户id-数组", required = true, dataTypeClass = String.class, allowMultiple = true),
    })
    @PostMapping(value = "/setupTenantManager")
    public MyCommonResult doSetupTenantManager(HttpServletRequest request, Long tenantId, Long[] userAccountIdArr,
                                               @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notNull(tenantId, "未知租户id:" + actionFailMsg);
            DefineTenantEntity defineTenantEntity = defineTenantMapper.selectById(tenantId);
            Assert.notNull(defineTenantEntity, "租户不存在:" + actionFailMsg);
            int count = defineTenantService.dealTenantSetupManager(loginUser, tenantId, userAccountIdArr);
            result.setCount(count);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

}