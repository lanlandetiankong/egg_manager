package com.egg.manager.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcModule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.controllers.announcement.AnnouncementDraftFuncModuleConstant;
import com.egg.manager.api.services.basic.announcement.AnnouncementDraftService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
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
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API -  AnnouncementDraftController ", description = "公告草稿接口")
@RestController
@RequestMapping("/announcement_draft")
public class AnnouncementDraftController extends BaseController {

    @Autowired
    private AnnouncementDraftMapper announcementDraftMapper;
    @Reference
    private AnnouncementDraftService announcementDraftService;
    @Reference
    private AnnouncementTagService announcementTagService;

    @PcWebQueryLog(action = "查询公告信息草稿-Dto列表", description = "查询公告信息草稿-Dto列表", fullPath = "/announcement_draft/getAllAnnouncementDraftDtos")
    @ApiOperation(value = "查询公告信息草稿-Dto列表", notes = "查询公告信息草稿-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllAnnouncementDraftDtos")
    public MyCommonResult<AnnouncementDraftVo> doGetAllAnnouncementDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                        Boolean onlySelf, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = MyCommonResult.gainQueryResult(AnnouncementDraftVo.class, AnnouncementDraftFuncModuleConstant.Success.queryPage);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            queryFieldBeanList.add(QueryFormFieldBean.dealGetNotEqualsBean("is_published", BaseStateEnum.ENABLED.getValue()));
            if (Boolean.TRUE.equals(onlySelf)) {  //只查询自己发布的公告
                queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("create_user_id", loginUser.getFid()));
            }
            //取得 分页配置
            AntdvPaginationBean<AnnouncementDraftDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, AnnouncementDraftDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = announcementDraftService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,AnnouncementDraftFuncModuleConstant.Failure.queryPage);
        }
        return result;
    }


    @ApiOperation(value = "查询公告草稿信息", notes = "根据id查询公告草稿信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询公告草稿信息", description = "根据id查询公告草稿信息", fullPath = "/announcement_draft/getAnnouncementDraftById")
    @PostMapping(value = "/getAnnouncementDraftById")
    public MyCommonResult<AnnouncementDraftVo> doGetAnnouncementDraftById(HttpServletRequest request, String draftId,
                                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = MyCommonResult.gainQueryResult(AnnouncementDraftVo.class,AnnouncementDraftFuncModuleConstant.Success.queryOneById);
        try {
            Assert.notBlank(draftId, BaseRstMsgConstant.ErrorMsg.unknowId());
            AnnouncementDraft announcementDraft = announcementDraftMapper.selectById(draftId);

            //取得 公告标签 map
            Map<String, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllToMap();
            result.setBean(AnnouncementDraftTransfer.transferEntityToVo(announcementDraft, announcementTagMap));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementDraftFuncModuleConstant.Failure.queryOneById);
        }
        return result;
    }

    @ApiOperation(value = "新增公告草稿", notes = "表单方式新增公告草稿", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增公告草稿", description = "表单方式新增公告草稿", fullPath = "/announcement_draft/addAnnouncementDraft")
    @PostMapping(value = "/addAnnouncementDraft")
    public MyCommonResult<AnnouncementDraftVo> doAddAnnouncementDraft(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                                                      @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = MyCommonResult.gainQueryResult(AnnouncementDraftVo.class,AnnouncementDraftFuncModuleConstant.Success.create);
        Integer addCount = 0;
        try {
            Assert.notNull(announcementDraftVo,BaseRstMsgConstant.ErrorMsg.emptyForm());

            announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
            addCount = announcementDraftService.dealCreate(loginUser, announcementDraftVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementDraftFuncModuleConstant.Failure.create);
        }
        return result;
    }

    @ApiOperation(value = "更新公告草稿", notes = "表单方式更新公告草稿", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新公告草稿", description = "表单方式更新公告草稿", fullPath = "/announcement_draft/updateAnnouncementDraft")
    @PostMapping(value = "/updateAnnouncementDraft")
    public MyCommonResult<AnnouncementDraftVo> doUpdateAnnouncementDraft(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                                                         @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = MyCommonResult.gainQueryResult(AnnouncementDraftVo.class,AnnouncementDraftFuncModuleConstant.Success.update);
        Integer updateCount = 0;
        try {
            Assert.notNull(announcementDraftVo,BaseRstMsgConstant.ErrorMsg.emptyForm());

            announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
            updateCount = announcementDraftService.dealUpdate(loginUser, announcementDraftVo);
            result.setCount(updateCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementDraftFuncModuleConstant.Failure.update);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除公告草稿", description = "根据公告id批量删除公告草稿", fullPath = "/announcement_draft/batchDelAnnouncementDraftByIds")
    @ApiOperation(value = "批量删除公告草稿", notes = "根据公告id批量删除公告草稿", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的公告草稿id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelAnnouncementDraftByIds")
    public MyCommonResult doBatchDeleteAnnouncementById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementDraftFuncModuleConstant.Success.batchDeleteByIds);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = announcementDraftService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,AnnouncementDraftFuncModuleConstant.Failure.batchDeleteByIds);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除公告草稿", description = "根据公告id删除公告草稿", fullPath = "/announcement_draft/delOneAnnouncementDraftByIds")
    @ApiOperation(value = "删除公告草稿", notes = "根据公告id删除公告草稿", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的公告草稿id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneAnnouncementDraftByIds")
    public MyCommonResult doDelOneAnnouncementDraftByIds(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementDraftFuncModuleConstant.Success.deleteById);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = announcementDraftService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,AnnouncementDraftFuncModuleConstant.Failure.deleteById);
        }
        return result;
    }


    @ApiOperation(value = "公告草稿批量转发布", notes = "根据公告草稿id批量发布", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "公告草稿批量转发布", description = "根据公告草稿id批量发布", fullPath = "/announcement_draft/batchPublishAnnouncementDraftByIds")
    @PostMapping(value = "/batchPublishAnnouncementDraftByIds")
    public MyCommonResult doBatchPublishAnnouncementById(HttpServletRequest request, String[] draftIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementDraftFuncModuleConstant.Success.batchPublish);
        Integer publishCount = 0;
        try {
            Assert.notEmpty(draftIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            publishCount = announcementDraftService.dealBatchPublishByDraft(loginUser, draftIds);
            result.setCount(publishCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,AnnouncementDraftFuncModuleConstant.Failure.batchPublish);
        }
        return result;
    }

    @ApiOperation(value = "公告草稿转发布", notes = "根据公告草稿id批量转发布", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "公告草稿转发布", description = "根据公告草稿id批量转发布", fullPath = "/announcement_draft/publishOneAnnouncementDraftById")
    @PostMapping(value = "/publishOneAnnouncementDraftById")
    public MyCommonResult doPublishOneAnnouncementDraftById(HttpServletRequest request, String draftId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(AnnouncementDraftFuncModuleConstant.Success.publish);
        try {
            Assert.notBlank(draftId,BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer publishCount = announcementDraftService.dealPublishByDraft(loginUser, draftId, true);
            result.setCount(publishCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,AnnouncementDraftFuncModuleConstant.Failure.publish);
        }
        return result;
    }
}
