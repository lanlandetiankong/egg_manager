package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.define.DefineJobFuncModuleConstant;
import com.egg.manager.api.services.basic.define.DefineJobService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineJobMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineJobTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineJobVo;
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
@Api(value = "API-职务定义接口 ")
@RestController
@RequestMapping("/define/define_job")
public class DefineJobController extends BaseController {

    @Autowired
    private DefineJobMapper defineJobMapper;
    @Reference
    private DefineJobService defineJobService;


    @PcWebQueryLog(action = "分页查询->职务定义",fullPath = "/define/define_job/queryPage")
    @ApiOperation(value = "分页查询->职务定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public MyCommonResult<DefineJobVo> queryPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = MyCommonResult.gainQueryResult(DefineJobVo.class,DefineJobFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineJob> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineJob.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineJobService.dealQueryPageByEntitys(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineJobFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @PcWebQueryLog(action = "分页查询(dto)->职务定义",fullPath = "/define/define_job/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->职务定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefineJobVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                             @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = MyCommonResult.gainQueryResult(DefineJobVo.class,DefineJobFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineJobDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineJobDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineJobService.dealQueryPageByDtos(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineJobFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->职务定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(action = "根据id查询->职务定义",fullPath = "/define/define_job/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefineJobVo> queryOneById(HttpServletRequest request, String defineJobId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = MyCommonResult.gainQueryResult(DefineJobVo.class,DefineJobFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            DefineJob defineJob = defineJobMapper.selectById(defineJobId);
            result.setBean(DefineJobTransfer.transferEntityToVo(defineJob));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineJobFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }


    @ApiOperation(value = "新增->职务定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "新增->职务定义",fullPath = "/define/define_job/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefineJobVo defineJobVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineJobFuncModuleConstant.Success.CREATE_OPER);
        Integer addCount = 0;
        try {
            Assert.notNull(defineJobVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineJobService.dealCreate(loginUser, defineJobVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineJobFuncModuleConstant.Failure.CREATE_OPER);
        }
        return result;
    }


    @ApiOperation(value = "更新->职务定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "更新->职务定义",fullPath = "/define/define_job/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefineJobVo defineJobVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineJobFuncModuleConstant.Success.UPDATE_OPER);
        Integer changeCount = 0;
        try {
            Assert.notNull(defineJobVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineJobService.dealUpdate(loginUser, defineJobVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefineJobFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量伪删除->职务定义",fullPath = "/define/define_job/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->职务定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineJobFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            //批量伪删除
            delCount = defineJobService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefineJobFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }


    @PcWebOperationLog(action = "伪删除->职务定义",fullPath = "/define/define_job/deleteById")
    @ApiOperation(value = "伪删除->职务定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "指定删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineJobFuncModuleConstant.Success.DELETE_BY_ID);
        Integer delCount = 0;
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            delCount = defineJobService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefineJobFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }

}
