package com.egg.manager.controller.user;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserTenant;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.mapper.user.UserTenantMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserTenantService;
import com.egg.manager.vo.user.UserTenantVo;
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


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Api(value = "API ==>>  UserTenantController ",description = "用户与租户关联表的接口")
@RestController
@RequestMapping("/user/user_tenant")
public class UserTenantController extends BaseController{

    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private UserTenantMapper userTenantMapper ;
    @Autowired
    private UserTenantService userTenantService ;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());






    @OperLog(modelName="UserTenantController",action="查询 [用户与租户关联] 列表",description = "查询 [用户与租户关联] 列表")
    @ApiOperation(value = "查询 [用户与租户关联] 列表", notes = "查询 [用户与租户关联] 列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllUserTenants")
    public MyCommonResult<UserTenantVo> doGetAllUserTenants(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj,String sortObj) {
        MyCommonResult<UserTenantVo> result = new MyCommonResult<UserTenantVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            userTenantService.dealGetUserTenantPages(result,queryFormFieldBeanList,paginationBean,sortBeans);
            dealCommonSuccessCatch(result,"查询 [用户与租户关联] 信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询 [用户与租户关联] 信息", notes = "根据 [用户与租户关联] id查询 [用户与租户关联] 信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserTenantController",action="查询 [用户与租户关联] 信息",description = "根据 [用户与租户关联] id查询 [用户与租户关联] 信息")
    @PostMapping(value = "/getUserTenantById")
    public MyCommonResult<UserTenantVo> doGetUserTenantById(HttpServletRequest request, HttpServletResponse response,String tenantId) {
        MyCommonResult<UserTenantVo> result = new MyCommonResult<UserTenantVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            UserTenant vo = userTenantMapper.selectById(tenantId);
            result.setBean(UserTenantVo.transferEntityToVo(vo));
            dealCommonSuccessCatch(result,"查询 [用户与租户关联] 信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增 [用户与租户关联] ", notes = "表单方式新增 [用户与租户关联] ", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserTenantController",action="新增 [用户与租户关联] ",description = "表单方式新增 [用户与租户关联] ")
    @PostMapping(value = "/doAddUserTenant")
    public MyCommonResult doAddUserTenant(HttpServletRequest request, HttpServletResponse response, UserTenantVo userTenantVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(userTenantVo == null) {
                throw new Exception("未接收到有效的 [用户与租户关联] 信息！");
            }   else {
                addCount = userTenantService.dealAddUserTenant(userTenantVo,loginUser);
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增 [用户与租户关联] :"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    

    @OperLog(modelName="UserTenantController",action="批量删除 [用户与租户关联] ",description = "根据 [用户与租户关联] id批量删除 [用户与租户关联] ")
    @ApiOperation(value = "批量删除 [用户与租户关联] ", notes = "根据 [用户与租户关联] id批量删除 [用户与租户关联] ", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的 [用户与租户关联] id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelUserTenantByIds")
    public MyCommonResult doBatchDeleteUserTenantById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = userTenantService.dealDelUserTenantByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除 [用户与租户关联] :"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="UserTenantController",action="删除 [用户与租户关联] ",description = "根据 [用户与租户关联] id删除 [用户与租户关联] ")
    @ApiOperation(value = "删除 [用户与租户关联] ", notes = "根据 [用户与租户关联] id删除 [用户与租户关联] ", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的 [用户与租户关联] id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneUserTenantByIds")
    public MyCommonResult doDelOneUserTenantById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                delCount = userTenantService.dealDelUserTenant(delId,loginUser);
                dealCommonSuccessCatch(result,"删除 [用户与租户关联] :"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }













}
