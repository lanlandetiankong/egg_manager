package com.egg.manager.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementService;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
import com.egg.manager.persistence.exchange.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.exchange.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.exchange.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.Announcement;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTag;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;
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
@Api(value = "API-发布公告接口")
@RestController
@RequestMapping("/announcement")
public class AnnouncementController extends BaseController {


    @Autowired
    private AnnouncementMapper announcementMapper;
    @Reference
    private AnnouncementService announcementService;
    @Reference
    private AnnouncementTagService announcementTagService;


    @PcWebQueryLog(fullPath = "/announcement/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->公告", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<AnnouncementVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                       Boolean onlySelf, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementVo> result = MyCommonResult.gainQueryResult(AnnouncementVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            if (Boolean.TRUE.equals(onlySelf)) {
                //只查询自己发布的公告
                queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("create_user_id", loginUser.getFid()));
            }
            //取得 分页配置
            AntdvPaginationBean<AnnouncementDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, AnnouncementDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = announcementService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "筛选查询->公告", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/announcement/queryFilteredPage")
    @PostMapping(value = "/queryFilteredPage")
    public MyCommonResult<AnnouncementVo> queryFilteredPage(HttpServletRequest request, Integer limitSize,
                                                            Boolean onlySelf, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementVo> result = MyCommonResult.gainQueryResult(AnnouncementVo.class);
        try {
            //这些查询条件暂时用不到
            String queryObj = null, paginationObj = null, sortObj = null;
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            if (Boolean.TRUE.equals(onlySelf)) {
                //只查询自己发布的公告
                queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("create_user_id", loginUser.getFid()));
            }
            //取得 分页配置
            AntdvPaginationBean paginationBean = AntdvPaginationBean.gainLimitPaginationBean(limitSize);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            //按创建时间 倒序
            sortBeans.add(AntdvSortBean.gainCreateTimeDescBean());
            result = announcementService.dealQueryPageByEntitys(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebQueryLog(fullPath = "/announcement/queryOneById")
    @ApiOperation(value = "根据id查询->公告", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<AnnouncementVo> queryOneById(HttpServletRequest request, String announcementId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementVo> result = MyCommonResult.gainQueryResult(AnnouncementVo.class);
        try {
            Assert.notBlank(announcementId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Announcement announcement = announcementMapper.selectById(announcementId);
            //取得 公告标签 map
            Map<Long, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllToMap();
            result.setBean(AnnouncementTransfer.transferEntityToVo(announcement, announcementTagMap));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/announcement/createByForm")
    @ApiOperation(value = "新增->公告", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, AnnouncementVo announcementVo,
                                       @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(announcementVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = announcementService.dealCreate(loginUser, announcementVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "发布->公告草稿", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcement/createFromDraft")
    @PostMapping(value = "/createFromDraft")
    public MyCommonResult createFromDraft(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(announcementDraftVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = announcementService.dealCreateFromDraft(loginUser, announcementDraftVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/announcement/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->公告", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = announcementService.dealBatchLogicDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "伪删除->公告", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcement/deleteById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = announcementService.dealLogicDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
