package com.egg.manager.controller.define;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.define.DefinePermissionMapper;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.define.DefinePermissionService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.vo.define.DefinePermissionVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
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
@RestController
@RequestMapping("/define/define_permission")
public class DefinePermissionController  extends BaseController{

    @Autowired
    private DefinePermissionMapper definePermissionMapper ;
    @Autowired
    private DefinePermissionService definePermissionService;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private RedisHelper redisHelper ;


    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @ApiOperation(value = "查询权限定义信息列表", notes = "查询权限定义信息列表", response = String.class)
    @PostMapping(value = "/getAllDefinePermissions")
    public MyCommonResult<DefinePermissionVo> doGetAllDefinePermissions(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj,String sortObj) {
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            definePermissionService.dealGetDefinePermissionPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询权限定义信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询权限定义信息", notes = "根据权限定义id查询权限定义信息", response = String.class)
    @PostMapping(value = "/getDefinePermissionById")
    public MyCommonResult<DefinePermissionVo> doGetDefinePermissionById(HttpServletRequest request, HttpServletResponse response,String definePermissionId) {
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            DefinePermission definePermission = definePermissionMapper.selectById(definePermissionId);
            result.setBean(DefinePermissionVo.transferEntityToVo(definePermission));
            dealCommonSuccessCatch(result,"查询权限定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增权限定义", notes = "表单方式新增权限定义", response = String.class)
    @PostMapping(value = "/doAddDefinePermission")
    public MyCommonResult<DefinePermissionVo> doAddDefinePermission(HttpServletRequest request, HttpServletResponse response, DefinePermissionVo definePermissionVo){
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(definePermissionVo == null) {
                throw new Exception("未接收到有效的权限定义！");
            }   else {
                addCount = definePermissionService.dealAddDefinePermission(definePermissionVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增权限定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新权限定义", notes = "表单方式更新权限定义", response = String.class)
    @PostMapping(value = "/doUpdateDefinePermission")
    public MyCommonResult doUpdateDefinePermission(HttpServletRequest request, HttpServletResponse response, DefinePermissionVo definePermissionVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(definePermissionVo == null) {
                throw new Exception("未接收到有效的权限定义！");
            }   else {
                changeCount = definePermissionService.dealUpdateDefinePermission(definePermissionVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新权限定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除权限定义", notes = "根据用户id批量删除权限定义", response = String.class)
    @PostMapping(value = "/batchDelDefinePermissionByIds")
    public MyCommonResult doBatchDeleteDefinePermissionById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = definePermissionService.dealDelDefinePermissionByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除权限定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除权限定义", notes = "根据权限id删除权限定义", response = String.class)
    @PostMapping(value = "/delOneDefinePermissionByIds")
    public MyCommonResult doDelOneDefinePermissionByIds(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = definePermissionService.dealDelDefinePermission(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除权限定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

}
