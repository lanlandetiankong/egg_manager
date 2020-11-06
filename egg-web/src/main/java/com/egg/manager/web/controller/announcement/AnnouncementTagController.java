package com.egg.manager.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
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
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTag;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementTagMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementTagDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTagTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementTagVo;
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
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-公告标签接口")
@RestController
@RequestMapping("/announcementTag")
public class AnnouncementTagController extends BaseController {

    @Autowired
    private AnnouncementTagMapper announcementTagMapper;

    @Reference
    private AnnouncementTagService announcementTagService;


    @PcWebQueryLog(fullPath = "/announcementTag/gainEnumSelect")
    @ApiOperation(value = "分页查询->公告标签", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/gainEnumSelect")
    public MyCommonResult<AnnouncementTagVo> gainEnumSelect(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                            @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = MyCommonResult.gainQueryResult(AnnouncementTagVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>();
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = announcementTagService.dealQueryPageByEntitys(loginUser, result, queryFieldBeanList, null, sortBeans);
            result = announcementTagService.dealResultListToEnums(result);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "分页查询(dto)->公告标签", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/announcementTag/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<AnnouncementTagVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = MyCommonResult.gainQueryResult(AnnouncementTagVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<AnnouncementTagDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, AnnouncementTagDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            announcementTagService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "根据id查询->公告标签", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/announcementTag/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<AnnouncementTagVo> queryOneById(HttpServletRequest request, String announcementTagId,
                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = MyCommonResult.gainQueryResult(AnnouncementTagVo.class);
        try {
            Assert.notBlank(announcementTagId, BaseRstMsgConstant.ErrorMsg.unknowId());
            AnnouncementTag announcementTag = announcementTagMapper.selectById(announcementTagId);
            result.setBean(AnnouncementTagTransfer.transferEntityToVo(announcementTag));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->公告标签", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementTag/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, AnnouncementTagVo announcementTagVo,
                                       @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(announcementTagVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = announcementTagService.dealCreate(loginUser, announcementTagVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->公告标签", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementTag/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, AnnouncementTagVo announcementTagVo,
                                       @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(announcementTagVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            changeCount = announcementTagService.dealUpdate(loginUser, announcementTagVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/announcementTag/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->公告标签", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds,
                                           @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = announcementTagService.dealBatchLogicDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/announcementTag/deleteById")
    @ApiOperation(value = "伪删除->公告标签", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId,
                                     @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = announcementTagService.dealLogicDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

}
