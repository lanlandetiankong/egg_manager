package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.define.basic.DefineJobService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineJobMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineJobDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineJobTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineJobVo;
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
@Api(value = "API-职务定义接口 ")
@RestController
@RequestMapping("/define/defineJob")
public class DefineJobController extends BaseController {

    @Autowired
    private DefineJobMapper defineJobMapper;
    @Reference
    private DefineJobService defineJobService;


    @PcWebQueryLog(fullPath = "/define/defineJob/queryPage")
    @ApiOperation(value = "分页查询->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public WebResult queryPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                               @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineJobEntity> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineJobEntity.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineJobService.dealQueryPageByEntitys(loginUserInfo, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebQueryLog(fullPath = "/define/defineJob/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineJobDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineJobDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineJobService.dealQueryPageByDtos(loginUserInfo, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/defineJob/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineJobId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            DefineJobEntity defineJobEntity = defineJobMapper.selectById(defineJobId);
            result.putBean(DefineJobTransfer.transferEntityToVo(defineJobEntity));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineJob/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, DefineJobVo defineJobVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        try {
            Assert.notNull(defineJobVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineJobService.dealCreate(loginUserInfo, defineJobVo);
            result.putCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineJob/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, DefineJobVo defineJobVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        try {
            Assert.notNull(defineJobVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineJobService.dealUpdate(loginUserInfo, defineJobVo);
            result.putCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/define/defineJob/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            //批量伪删除
            delCount = defineJobService.dealBatchLogicDelete(loginUserInfo, delIds);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/define/defineJob/deleteById")
    @ApiOperation(value = "伪删除->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
            delCount = defineJobService.dealLogicDeleteById(loginUserInfo, delId);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

}
