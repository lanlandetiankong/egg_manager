package com.egg.manager.web.controller.announcement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.api.services.basic.announcement.AnnouncementService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementMapper;
import com.egg.manager.persistence.pojo.mysql.transfer.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementVo;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "API -  AnnouncementController ",description = "发布公告接口")
@RestController
@RequestMapping("/announcement")
public class AnnouncementController extends BaseController {


    @Autowired
    private AnnouncementMapper announcementMapper ;
    @Reference
    private AnnouncementService announcementService ;
    @Reference
    private AnnouncementTagService announcementTagService ;

    @OperLog(action="新增公告",description = "表单方式新增公告",fullPath = "/announcement/addAnnouncement")
    @ApiOperation(value = "新增公告", notes = "表单方式新增公告", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/addAnnouncement")
    public MyCommonResult<AnnouncementVo> doAddAnnouncement(HttpServletRequest request, AnnouncementVo announcementVo,
                                                            @CurrentLoginUser UserAccount loginUser){
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        Integer addCount = 0 ;
        try{
            if(announcementVo == null) {
                throw new Exception("未接收到有效的公告！");
            }   else {
                addCount = announcementService.dealAddAnnouncement(announcementVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增公告:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "公告草稿发布", notes = "表单方式发布公告草稿", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="公告草稿发布",description = "表单方式发布公告草稿",fullPath = "/announcement/addAnnouncementFromDraft")
    @PostMapping(value = "/addAnnouncementFromDraft")
    public MyCommonResult<AnnouncementVo> doAddAnnouncementFromDraft(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                                                     @CurrentLoginUser UserAccount loginUser){
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        Integer addCount = 0 ;
        try{
            if(announcementDraftVo == null) {
                throw new Exception("未接收到有效的公告草稿！");
            }   else {
                addCount = announcementService.dealAddAnnouncementFromDraft(announcementDraftVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"公告草稿发布:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @OperLog(action="查询公告信息-Dto列表",description = "查询公告信息-Dto列表",fullPath = "/announcement/getAllAnnouncementDtos")
    @ApiOperation(value = "查询公告信息-Dto列表", notes = "查询公告信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllAnnouncementDtos")
    public MyCommonResult<AnnouncementVo> doGetAllAnnouncementDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                   Boolean onlySelf, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            if(Boolean.TRUE.equals(onlySelf)){  //只查询自己发布的公告
                queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("create_user_id",loginUser.getFid() )) ;
            }
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            result = announcementService.dealGetAnnouncementDtoPages(result,queryFieldBeanList,paginationBean,sortBeans); ;
            dealCommonSuccessCatch(result,"查询公告信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "查询公告信息部分列表", notes = "查询公告信息部分列表", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="查询公告信息部分列表",description = "查询公告信息部分列表",fullPath = "/announcement/getSomeAnnouncements")
    @PostMapping(value = "/getSomeAnnouncements")
    public MyCommonResult<AnnouncementVo> doGetSomeAnnouncements(HttpServletRequest request, Integer limitSize,
                                                                 Boolean onlySelf, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        try{
            //这些查询条件暂时用不到
            String queryObj = null,paginationObj = null,sortObj = null ;
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            if(Boolean.TRUE.equals(onlySelf)){  //只查询自己发布的公告
                queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("create_user_id",loginUser.getFid() )) ;
            }
            //取得 分页配置
            AntdvPaginationBean paginationBean = AntdvPaginationBean.gainLimitPaginationBean(limitSize);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            sortBeans.add(AntdvSortBean.gainCreateTimeDescBean());  //按创建时间 倒序
            result = announcementService.dealGetAnnouncementPages(result,queryFieldBeanList,paginationBean,sortBeans); ;
            dealCommonSuccessCatch(result,"查询公告信息部分列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



    @OperLog(action="查询公告信息",description = "根据id查询公告信息",fullPath = "/announcement/getAnnouncementById")
    @ApiOperation(value = "查询公告信息", notes = "根据id查询公告信息", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/getAnnouncementById")
    public MyCommonResult<AnnouncementVo> doGetAnnouncementById(HttpServletRequest request, String announcementId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        try{
            Announcement announcement = announcementMapper.selectById(announcementId);
            //取得 公告标签 map
            Map<String,AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();
            result.setBean(AnnouncementTransfer.transferEntityToVo(announcement,announcementTagMap));
            dealCommonSuccessCatch(result,"查询公告信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @OperLog(action="批量删除公告",description = "根据公告id批量删除公告",fullPath = "/announcement/batchDelAnnouncementByIds")
    @ApiOperation(value = "批量删除公告", notes = "根据公告id批量删除公告", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的公告id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelAnnouncementByIds")
    public MyCommonResult doBatchDeleteAnnouncementById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = announcementService.dealDelAnnouncementByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除公告:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除公告", notes = "根据公告id删除公告", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="删除公告",description = "根据公告id删除公告",fullPath = "/announcement/delOneAnnouncementByIds")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的公告id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneAnnouncementByIds")
    public MyCommonResult doDelOneAnnouncementByIds(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = announcementService.dealDelAnnouncement(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除公告:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



}
