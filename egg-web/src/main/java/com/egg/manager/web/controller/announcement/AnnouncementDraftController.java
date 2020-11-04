package com.egg.manager.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.common.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.api.services.basic.announcement.AnnouncementDraftService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
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
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementDraftMapper;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mysql.transfer.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementDraftVo;
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
import java.util.Map;


/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-公告草稿接口 ")
@RestController
@RequestMapping("/announcementDraft")
public class AnnouncementDraftController extends BaseController {

    @Autowired
    private AnnouncementDraftMapper announcementDraftMapper;
    @Reference
    private AnnouncementDraftService announcementDraftService;
    @Reference
    private AnnouncementTagService announcementTagService;

    @PcWebQueryLog(fullPath = "/announcementDraft/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<AnnouncementDraftVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                            Boolean onlySelf, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = MyCommonResult.gainQueryResult(AnnouncementDraftVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            queryFieldBeanList.add(QueryFormFieldBean.dealGetNotEqualsBean("is_published", BaseStateEnum.ENABLED.getValue()));
            if (Boolean.TRUE.equals(onlySelf)) {
                //只查询自己发布的公告
                queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("create_user_id", loginUser.getFid()));
            }
            //取得 分页配置
            AntdvPaginationBean<AnnouncementDraftDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, AnnouncementDraftDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = announcementDraftService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/announcementDraft/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<AnnouncementDraftVo> queryOneById(HttpServletRequest request, String draftId,
                                                            @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = MyCommonResult.gainQueryResult(AnnouncementDraftVo.class);
        try {
            Assert.notBlank(draftId, BaseRstMsgConstant.ErrorMsg.unknowId());
            AnnouncementDraft announcementDraft = announcementDraftMapper.selectById(draftId);

            //取得 公告标签 map
            Map<Long, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllToMap();
            result.setBean(AnnouncementDraftTransfer.transferEntityToVo(announcementDraft, announcementTagMap));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "新增->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementDraft/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                       @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(announcementDraftVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
            addCount = announcementDraftService.dealCreate(loginUser, announcementDraftVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "更新->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementDraft/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                       @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer updateCount = 0;
        try {
            Assert.notNull(announcementDraftVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
            updateCount = announcementDraftService.dealUpdate(loginUser, announcementDraftVo);
            result.setCount(updateCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/announcementDraft/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = announcementDraftService.dealBatchLogicDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/announcementDraft/deleteById")
    @ApiOperation(value = "伪删除->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = announcementDraftService.dealLogicDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "批量发布->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementDraft/batchPublishDraft")
    @PostMapping(value = "/batchPublishDraft")
    public MyCommonResult batchPublishDraft(HttpServletRequest request, Long[] draftIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer publishCount = 0;
        try {
            Assert.notEmpty(draftIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            publishCount = announcementDraftService.dealBatchPublishByDraft(loginUser, draftIds);
            result.setCount(publishCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "发布->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementDraft/publishDraft")
    @PostMapping(value = "/publishDraft")
    public MyCommonResult publishDraft(HttpServletRequest request, Long draftId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notNull(draftId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer publishCount = announcementDraftService.dealPublishByDraft(loginUser, draftId, true);
            result.setCount(publishCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
