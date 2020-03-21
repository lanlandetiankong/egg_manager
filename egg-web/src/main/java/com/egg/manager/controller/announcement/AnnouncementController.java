package com.egg.manager.controller.announcement;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.announcement.Announcement;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.announcement.AnnouncementMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.announcement.AnnouncementService;
import com.egg.manager.service.announcement.AnnouncementTagService;
import com.egg.manager.redis.service.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.vo.announcement.AnnouncementVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Api(value = "API -  AnnouncementController ",description = "发布公告接口")
@RestController
@RequestMapping("/announcement")
public class AnnouncementController extends BaseController {


    @Autowired
    private AnnouncementMapper announcementMapper ;
    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private AnnouncementService announcementService ;
    @Autowired
    private AnnouncementTagService announcementTagService ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @OperLog(modelName="AnnouncementController",action="新增公告",description = "表单方式新增公告")
    @ApiOperation(value = "新增公告", notes = "表单方式新增公告", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/addAnnouncement")
    public MyCommonResult<AnnouncementVo> doAddAnnouncement(HttpServletRequest request, HttpServletResponse response,AnnouncementVo announcementVo){
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(announcementVo == null) {
                throw new Exception("未接收到有效的公告！");
            }   else {
                addCount = announcementService.dealAddAnnouncement(announcementVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增公告:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "公告草稿发布", notes = "表单方式发布公告草稿", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementController",action="公告草稿发布",description = "表单方式发布公告草稿")
    @PostMapping(value = "/addAnnouncementFromDraft")
    public MyCommonResult<AnnouncementVo> doAddAnnouncementFromDraft(HttpServletRequest request, HttpServletResponse response,AnnouncementDraftVo announcementDraftVo){
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(announcementDraftVo == null) {
                throw new Exception("未接收到有效的公告草稿！");
            }   else {
                addCount = announcementService.dealAddAnnouncementFromDraft(announcementDraftVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"公告草稿发布:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="AnnouncementController",action="查询公告信息-Dto列表",description = "查询公告信息-Dto列表")
    @ApiOperation(value = "查询公告信息-Dto列表", notes = "查询公告信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllAnnouncementDtos")
    public MyCommonResult<AnnouncementVo> doGetAllAnnouncementDtos(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj,String sortObj,Boolean onlySelf) {
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
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
            announcementService.dealGetAnnouncementDtoPages(result,queryFieldBeanList,paginationBean,sortBeans); ;
            dealCommonSuccessCatch(result,"查询公告信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "查询公告信息部分列表", notes = "查询公告信息部分列表", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementController",action="查询公告信息部分列表",description = "查询公告信息部分列表")
    @PostMapping(value = "/getSomeAnnouncements")
    public MyCommonResult<AnnouncementVo> doGetSomeAnnouncements(HttpServletRequest request, HttpServletResponse response,Integer limitSize, Boolean onlySelf) {
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        try{
            //这些查询条件暂时用不到
            String queryObj = null,paginationObj = null,sortObj = null ;
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
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
            announcementService.dealGetAnnouncementPages(result,queryFieldBeanList,paginationBean,sortBeans); ;
            dealCommonSuccessCatch(result,"查询公告信息部分列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @OperLog(modelName="AnnouncementController",action="查询公告信息",description = "根据id查询公告信息")
    @ApiOperation(value = "查询公告信息", notes = "根据id查询公告信息", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/getAnnouncementById")
    public MyCommonResult<AnnouncementVo> doGetAnnouncementById(HttpServletRequest request, HttpServletResponse response, String announcementId) {
        MyCommonResult<AnnouncementVo> result = new MyCommonResult<AnnouncementVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            Announcement announcement = announcementMapper.selectById(announcementId);
            //取得 公告标签 map
            Map<String,AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();
            result.setBean(AnnouncementVo.transferEntityToVo(announcement,announcementTagMap));
            dealCommonSuccessCatch(result,"查询公告信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="AnnouncementController",action="批量删除公告",description = "根据公告id批量删除公告")
    @ApiOperation(value = "批量删除公告", notes = "根据公告id批量删除公告", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的公告id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelAnnouncementByIds")
    public MyCommonResult doBatchDeleteAnnouncementById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = announcementService.dealDelAnnouncementByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除公告:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除公告", notes = "根据公告id删除公告", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementController",action="删除公告",description = "根据公告id删除公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的公告id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneAnnouncementByIds")
    public MyCommonResult doDelOneAnnouncementByIds(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = announcementService.dealDelAnnouncement(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除公告:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



}
