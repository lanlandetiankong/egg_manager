package com.egg.manager.controller.organization;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.organization.DefineTenant;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.organization.DefineTenantMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.organization.DefineTenantService;
import com.egg.manager.redis.service.RedisHelper;
import com.egg.manager.vo.organization.DefineTenantVo;
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
@Api(value = "API ==>>  DefineTenantController ",description = "租户定义接口")
@RestController
@RequestMapping("/organization/define_tenant")
public class DefineTenantController extends BaseController{

    @Autowired
    private DefineTenantMapper defineTenantMapper ;
    @Autowired
    private DefineTenantService defineTenantService;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private RedisHelper redisHelper ;


    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @OperLog(modelName="DefineTenantController",action="查询租户定义信息-Dto列表",description = "查询租户定义信息-Dto列表")
    @ApiOperation(value = "查询租户定义信息-Dto列表", notes = "查询租户定义信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineTenantDtos")
    public MyCommonResult<DefineTenantVo> doGetAllDefineTenantDtos(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj, String sortObj) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineTenantService.dealGetDefineTenantDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询租户定义信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询租户定义信息", notes = "根据租户定义id查询租户定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineTenantController",action="查询租户定义信息",description = "根据租户定义id查询租户定义信息")
    @PostMapping(value = "/getDefineTenantById")
    public MyCommonResult<DefineTenantVo> doGetDefineTenantById(HttpServletRequest request, HttpServletResponse response,String defineTenantId) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            DefineTenant defineTenant = defineTenantMapper.selectById(defineTenantId);
            result.setBean(DefineTenantVo.transferEntityToVo(defineTenant));
            dealCommonSuccessCatch(result,"查询租户定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineTenantController",action="查询租户定义信息-Enum列表",description = "查询租户定义信息-Enum列表")
    @ApiOperation(value = "查询租户定义信息-Enum列表", notes = "查询租户定义信息-Enum列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineTenantEnums")
    public MyCommonResult<DefineTenantVo> doGetAllDefineTenantEnums(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj, String sortObj) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>() ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineTenantService.dealGetDefineTenantDtoPages(result,queryFieldBeanList,null,sortBeans); ;
            defineTenantService.dealResultListSetToEntitySelect(result) ;
            dealCommonSuccessCatch(result,"查询公告标签信息Select列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }




    @ApiOperation(value = "新增租户定义", notes = "表单方式新增租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineTenantController",action="新增租户定义",description = "表单方式新增租户定义")
    @PostMapping(value = "/doAddDefineTenant")
    public MyCommonResult<DefineTenantVo> doAddDefineTenant(HttpServletRequest request, HttpServletResponse response, DefineTenantVo defineTenantVo){
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineTenantVo == null) {
                throw new Exception("未接收到有效的租户定义！");
            }   else {
                addCount = defineTenantService.dealAddDefineTenant(defineTenantVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增租户定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新租户定义", notes = "表单方式更新租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineTenantController",action="更新租户定义",description = "表单方式更新租户定义")
    @PostMapping(value = "/doUpdateDefineTenant")
    public MyCommonResult doUpdateDefineTenant(HttpServletRequest request, HttpServletResponse response, DefineTenantVo defineTenantVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineTenantVo == null) {
                throw new Exception("未接收到有效的租户定义！");
            }   else {
                changeCount = defineTenantService.dealUpdateDefineTenant(defineTenantVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新租户定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineTenantController",action="批量删除租户定义",description = "根据租户定义id批量删除租户定义")
    @ApiOperation(value = "批量删除租户定义", notes = "根据租户定义id批量删除租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的租户定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineTenantByIds")
    public MyCommonResult doBatchDeleteDefineTenantByIds(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = defineTenantService.dealDelDefineTenantByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除租户定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineTenantController",action="删除租户定义",description = "根据租户id删除租户定义")
    @ApiOperation(value = "删除租户定义", notes = "根据租户id删除租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的租户定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineTenantById")
    public MyCommonResult doDelOneDefineTenantById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = defineTenantService.dealDelDefineTenant(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除租户定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

}
