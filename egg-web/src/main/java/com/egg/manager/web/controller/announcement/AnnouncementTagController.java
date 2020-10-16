package com.egg.manager.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.announcement.AnnouncementTagFuncModuleConstant;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mysql.transfer.announcement.AnnouncementTagTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementTagVo;
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
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API -  公告标签接口")
@RestController
@RequestMapping("/announcement_tag")
public class AnnouncementTagController extends BaseController {

    @Autowired
    private AnnouncementTagMapper announcementTagMapper;

    @Reference
    private AnnouncementTagService announcementTagService;


    @PcWebQueryLog(action = "查询公告标签信息Select列表",fullPath = "/announcement_tag/gainEnumSelect")
    @ApiOperation(value = "查询公告标签信息Select列表",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/gainEnumSelect")
    public MyCommonResult<AnnouncementTagVo> gainEnumSelect(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = MyCommonResult.gainQueryResult(AnnouncementTagVo.class,AnnouncementTagFuncModuleConstant.Success.QUERY_ENUM_LIST);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>();
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = announcementTagService.dealQueryPageByEntitys(loginUser, result, queryFieldBeanList, null, sortBeans);
            result = announcementTagService.dealResultListToEnums(result);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementTagFuncModuleConstant.Failure.QUERY_ENUM_LIST);
        }
        return result;
    }

    @ApiOperation(value = "查询公告标签信息-Dto列表",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(action = "查询公告标签信息-Dto列表",fullPath = "/announcement_tag/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<AnnouncementTagVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                         @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = MyCommonResult.gainQueryResult(AnnouncementTagVo.class,AnnouncementTagFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<AnnouncementTagDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,AnnouncementTagDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            announcementTagService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementTagFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }

    @ApiOperation(value = "查询公告标签信息",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(action = "查询公告标签信息",fullPath = "/announcement_tag/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<AnnouncementTagVo> queryOneById(HttpServletRequest request, String announcementTagId,
                                                                      @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = MyCommonResult.gainQueryResult(AnnouncementTagVo.class,AnnouncementTagFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            Assert.notBlank(announcementTagId, BaseRstMsgConstant.ErrorMsg.unknowId());
            AnnouncementTag announcementTag = announcementTagMapper.selectById(announcementTagId);
            result.setBean(AnnouncementTagTransfer.transferEntityToVo(announcementTag));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementTagFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }


    @ApiOperation(value = "新增公告标签",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "新增公告标签",fullPath = "/announcement_tag/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, AnnouncementTagVo announcementTagVo,
                                                                  @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementTagFuncModuleConstant.Success.CREATE_OPER);
        Integer addCount = 0;
        try {
            Assert.notNull(announcementTagVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = announcementTagService.dealCreate(loginUser, announcementTagVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementTagFuncModuleConstant.Failure.CREATE_OPER);
        }
        return result;
    }


    @ApiOperation(value = "更新公告标签",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "更新公告标签",fullPath = "/announcement_tag/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, AnnouncementTagVo announcementTagVo,
                                                  @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementTagFuncModuleConstant.Success.UPDATE_OPER);
        Integer changeCount = 0;
        try {
            Assert.notNull(announcementTagVo,BaseRstMsgConstant.ErrorMsg.emptyForm());

            changeCount = announcementTagService.dealUpdate(loginUser, announcementTagVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementTagFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除公告标签",fullPath = "/announcement_tag/batchDeleteByIds")
    @ApiOperation(value = "批量删除公告标签",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的公告标签id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds,
                                                           @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementTagFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = announcementTagService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementTagFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除公告标签",fullPath = "/announcement_tag/deleteById")
    @ApiOperation(value = "删除公告标签",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的公告标签id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId,
                                                      @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementTagFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = announcementTagService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementTagFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }

}
