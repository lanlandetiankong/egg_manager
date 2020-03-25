package com.egg.manager.web.controller.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.common.base.constant.define.DefineMenuConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.persistence.tree.CommonMenuTree;
import com.egg.manager.persistence.tree.CommonTreeSelect;
import com.egg.manager.service.redis.service.user.UserAccountRedisService;
import com.egg.manager.web.controller.BaseController;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.define.DefineMenuMapper;
import com.egg.manager.service.redis.service.RedisHelper;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.module.DefineMenuService;
import com.egg.manager.service.service.user.UserAccountService;
import com.egg.manager.persistence.vo.define.DefineMenuVo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    private UserAccountRedisService userAccountRedisService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @OperLog(modelName="DefineMenuController",action="查询所有路由菜单TreeSelect",description = "查询所有路由菜单TreeSelect")
    @ApiOperation(value = "查询所有路由菜单TreeSelect", notes = "查询所有路由菜单TreeSelect", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping("/getAllMenuTreeSelect")
    public MyCommonResult<DefineMenu> doGetAllMenuTreeSelect(Boolean withRoot) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        //查询 所有[可用状态]的 [菜单定义]
        List<DefineMenu> allMenus  = defineMenuService.getAllEnableDefineMenus(new EntityWrapper<DefineMenu>());
        List<CommonTreeSelect> treeList = null ;
        if(Boolean.TRUE.equals(withRoot)){
            treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID,allMenus);
        }   else {
            treeList = defineMenuService.getTreeSelectChildNodes(DefineMenuConstant.ROOT_ID,allMenus);
        }
        result.setResultList(treeList);
        return result ;
    }

    @OperLog(modelName="DefineMenuController",action="查询被过滤的路由菜单TreeSelect",description = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)")
    @ApiOperation(value = "查询被过滤的路由菜单TreeSelect", notes = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping("/getMenuTreeSelectFilterChildrens")
    public MyCommonResult<DefineMenu> doGetMenuTreeSelectFilterChildrens(String filterId) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<DefineMenu> allMenus  =  defineMenuMapper.getMenusFilterChildrens(filterId,true);
        List<CommonTreeSelect> treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID,allMenus);
        result.setResultList(treeList);
        return result ;
    }

    @ApiOperation(value = "查询所有可以访问的路由菜单", notes = "查询所有可以访问的路由菜单", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="查询所有可以访问的路由菜单",description = "查询所有可以访问的路由菜单")
    @PostMapping("/user/getAllMenuTree")
    public MyCommonResult<DefineMenu> doGetAllMenuTree() {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        //筛选与排序
        EntityWrapper<DefineMenu> defineMenuEntityWrapper = new EntityWrapper<DefineMenu>();
        defineMenuEntityWrapper.eq("state",BaseStateEnum.ENABLED.getValue());
        defineMenuEntityWrapper.orderBy("level",true);
        defineMenuEntityWrapper.orderBy("order_num",true);
        defineMenuEntityWrapper.orderBy("create_time",false);
        List<DefineMenu> allMenus  = defineMenuService.selectList(defineMenuEntityWrapper);
        List<CommonMenuTree> treeList = defineMenuService.getMenuTreeChildNodes(DefineMenuConstant.ROOT_ID,allMenus);
        result.setResultList(treeList);
        return result ;
    }


    @ApiOperation(value = "查询用户可以访问的路由菜单", notes = "查询用户可以访问的路由菜单", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="查询用户可以访问的路由菜单",description = "查询用户可以访问的路由菜单")
    @PostMapping("/user/getGrantedMenuTree")
    public MyCommonResult<DefineMenu> doGetGrantedMenuTree(@RequestHeader("authorization") String authorization, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<CommonMenuTree> treeList = userAccountRedisService.dealGetCurrentUserFrontMenuTrees(authorization,loginUser.getFid(),false);
        result.setResultList(treeList);
        return result ;
    }







    @OperLog(modelName="DefineMenuController",action="查询菜单定义信息-Dto列表",description = "查询菜单定义信息-Dto列表")
    @ApiOperation(value = "查询菜单定义信息-Dto列表", notes = "查询菜单定义信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineMenuDtos")
    public MyCommonResult<DefineMenuVo> doGetAllDefineMenuDtos(HttpServletRequest request,
                                                               String queryObj, String paginationObj, String sortObj,
                                                               @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = this.parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineMenuService.dealGetDefineMenuDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询菜单定义信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询菜单定义信息", notes = "根据菜单定义id查询菜单定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineMenuController",action="查询菜单定义信息",description = "根据菜单定义id查询菜单定义信息")
    @PostMapping(value = "/getDefineMenuById")
    public MyCommonResult<DefineMenuVo> doGetDefineMenuById(HttpServletRequest request,String defineMenuId) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        try{
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
    public MyCommonResult<DefineMenuVo> doAddDefineMenu(HttpServletRequest request,DefineMenuVo defineMenuVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        Integer addCount = 0 ;
        try{
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
    public MyCommonResult doUpdateDefineMenu(HttpServletRequest request,DefineMenuVo defineMenuVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
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
    public MyCommonResult doBatchDeleteDefineMenuById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
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
    public MyCommonResult doDelOneDefineMenuById(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
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
