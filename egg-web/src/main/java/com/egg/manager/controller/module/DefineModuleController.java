package com.egg.manager.controller.module;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.module.DefineModule;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.module.DefineModuleMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.module.DefineModuleService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.vo.module.DefineModuleVo;
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

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Api(value = "API ==>>  DefineModuleController ",description = "模块定义接口")
@RestController
@RequestMapping("/module/define_module")
public class DefineModuleController extends BaseController{

    @Autowired
    private DefineModuleMapper defineModuleMapper ;
    @Autowired
    private DefineModuleService defineModuleService;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private RedisHelper redisHelper ;


    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @OperLog(modelName="DefineModuleController",action="查询模块定义信息列表",description = "查询模块定义信息列表")
    @ApiOperation(value = "查询模块定义信息列表", notes = "查询模块定义信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineModules")
    public MyCommonResult<DefineModuleVo> doGetAllDefineModules(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj, String sortObj) {
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineModuleService.dealGetDefineModulePages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询模块定义信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询模块定义信息", notes = "根据模块定义id查询模块定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineModuleController",action="查询模块定义信息",description = "根据模块定义id查询模块定义信息")
    @PostMapping(value = "/getDefineModuleById")
    public MyCommonResult<DefineModuleVo> doGetDefineModuleById(HttpServletRequest request, HttpServletResponse response,String defineModuleId) {
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            DefineModule defineModule = defineModuleMapper.selectById(defineModuleId);
            result.setBean(DefineModuleVo.transferEntityToVo(defineModule));
            dealCommonSuccessCatch(result,"查询模块定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增模块定义", notes = "表单方式新增模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineModuleController",action="新增模块定义",description = "表单方式新增模块定义")
    @PostMapping(value = "/doAddDefineModule")
    public MyCommonResult<DefineModuleVo> doAddDefineModule(HttpServletRequest request, HttpServletResponse response, DefineModuleVo defineModuleVo){
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineModuleVo == null) {
                throw new Exception("未接收到有效的模块定义！");
            }   else {
                addCount = defineModuleService.dealAddDefineModule(defineModuleVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增模块定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新模块定义", notes = "表单方式更新模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineModuleController",action="更新模块定义",description = "表单方式更新模块定义")
    @PostMapping(value = "/doUpdateDefineModule")
    public MyCommonResult doUpdateDefineModule(HttpServletRequest request, HttpServletResponse response, DefineModuleVo defineModuleVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineModuleVo == null) {
                throw new Exception("未接收到有效的模块定义！");
            }   else {
                changeCount = defineModuleService.dealUpdateDefineModule(defineModuleVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新模块定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineModuleController",action="批量删除模块定义",description = "根据菜单定义id批量删除模块定义")
    @ApiOperation(value = "批量删除模块定义", notes = "根据菜单定义id批量删除模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的模块定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineModuleByIds")
    public MyCommonResult doBatchDeleteDefineModuleById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = defineModuleService.dealDelDefineModuleByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除模块定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineModuleController",action="删除模块定义",description = "根据模块id删除模块定义")
    @ApiOperation(value = "删除模块定义", notes = "根据模块id删除模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的模块定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineModuleById")
    public MyCommonResult doDelOneDefineModuleById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = defineModuleService.dealDelDefineModule(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除模块定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

}
