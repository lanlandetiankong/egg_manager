package com.egg.manager.controller.define;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.constant.define.DefineMenuConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.common.web.tree.CommonMenuTree;
import com.egg.manager.common.web.tree.CommonTreeSelect;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.module.DefineMenu;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.module.DefineMenuMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.module.DefineMenuService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.module.DefineMenuVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "API ==>>  DefineMenuController ",description = "菜单定义接口")
@RestController
@RequestMapping("/define/define_menu")
public class DefineMenuController extends BaseController{
    @Autowired
    private DefineMenuService defineMenuService ;
    @Autowired
    private CommonFuncService commonFuncService ;

    @Autowired
    private DefineMenuMapper defineMenuMapper ;
    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @OperLog(modelName="DefineMenuController",action="查询路由菜单TreeSelect",description = "查询路由菜单TreeSelect")
    @ApiOperation(value = "查询路由菜单TreeSelect", notes = "查询路由菜单TreeSelect", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping("/getAllMenuTreeSelect")
    public MyCommonResult<DefineMenu> doGetAllMenuTreeSelect() {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<DefineMenu> allMenus  = defineMenuService.selectList(null);
        List<CommonTreeSelect> treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID,allMenus);
        result.setResultList(treeList);
        return result ;
    }

    //TODO 还没添加筛选
    @ApiOperation(value = "查询用户可以访问的路由菜单", notes = "查询用户可以访问的路由菜单", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="查询用户可以访问的路由菜单",description = "查询用户可以访问的路由菜单")
    @PostMapping("/user/getGrantedMenuTree")
    public MyCommonResult<DefineMenu> doGetAllMenu() {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<DefineMenu> allMenus  = defineMenuService.selectList(null);
        List<CommonMenuTree> treeList = defineMenuService.getMenuTreeChildNodes(DefineMenuConstant.ROOT_ID,allMenus);
        result.setResultList(treeList);
        return result ;
    }









    @OperLog(modelName="DefineMenuController",action="查询菜单定义信息列表",description = "查询菜单定义信息列表")
    @ApiOperation(value = "查询菜单定义信息列表", notes = "查询菜单定义信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineMenus")
    public MyCommonResult<DefineMenuVo> doGetAllDefineMenus(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj, String sortObj) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineMenuService.dealGetDefineMenuPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询菜单定义信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询菜单定义信息", notes = "根据菜单定义id查询菜单定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="查询菜单定义信息",description = "根据菜单定义id查询菜单定义信息")
    @PostMapping(value = "/getDefineMenuById")
    public MyCommonResult<DefineMenuVo> doGetDefineMenuById(HttpServletRequest request, HttpServletResponse response,String defineMenuId) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            DefineMenu defineMenu = defineMenuMapper.selectById(defineMenuId);
            result.setBean(DefineMenuVo.transferEntityToVo(defineMenu));
            dealCommonSuccessCatch(result,"查询菜单定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增菜单定义", notes = "表单方式新增菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="新增菜单定义",description = "表单方式新增菜单定义")
    @PostMapping(value = "/doAddDefineMenu")
    public MyCommonResult<DefineMenuVo> doAddDefineMenu(HttpServletRequest request, HttpServletResponse response, DefineMenuVo defineMenuVo){
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineMenuVo == null) {
                throw new Exception("未接收到有效的菜单定义！");
            }   else {
                addCount = defineMenuService.dealAddDefineMenu(defineMenuVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增菜单定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新菜单定义", notes = "表单方式更新菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="更新菜单定义",description = "表单方式更新菜单定义")
    @PostMapping(value = "/doUpdateDefineMenu")
    public MyCommonResult doUpdateDefineMenu(HttpServletRequest request, HttpServletResponse response, DefineMenuVo defineMenuVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineMenuVo == null) {
                throw new Exception("未接收到有效的菜单定义！");
            }   else {
                changeCount = defineMenuService.dealUpdateDefineMenu(defineMenuVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新菜单定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除菜单定义", notes = "根据菜单定义id批量删除菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="批量删除菜单定义",description = "根据菜单定义id批量删除菜单定义")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的菜单定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineMenuByIds")
    public MyCommonResult doBatchDeleteDefineMenuById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = defineMenuService.dealDelDefineMenuByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除菜单定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineMenuController",action="删除菜单定义",description = "根据菜单id删除菜单定义")
    @ApiOperation(value = "删除菜单定义", notes = "根据菜单id删除菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的菜单定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineMenuById")
    public MyCommonResult doDelOneDefineMenuById(HttpServletRequest request, HttpServletResponse response, String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(org.apache.commons.lang3.StringUtils.isNotBlank(delId)){
                Integer delCount = defineMenuService.dealDelDefineMenu(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除菜单定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



}
