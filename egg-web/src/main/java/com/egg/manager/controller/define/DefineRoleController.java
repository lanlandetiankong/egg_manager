package com.egg.manager.controller.define;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.mapper.define.DefineRoleMapper;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.define.DefineRoleService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.define.DefineRoleVo;
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

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/define/define_role")
public class DefineRoleController extends BaseController {


    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private DefineRoleMapper defineRoleMapper ;
    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private DefineRoleService defineRoleService;

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @ApiOperation(value = "查询角色定义信息列表", notes = "查询角色定义信息列表", response = String.class)
    @PostMapping(value = "/getAllDefineRoles")
    public MyCommonResult<DefineRoleVo> doGetAllDefineRoles(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            defineRoleService.dealGetDefineRolePages(result,queryFieldBeanList,paginationBean) ;
            dealCommonSuccessCatch(result,"查询角色定义信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询角色定义信息", notes = "根据角色定义id查询角色定义信息", response = String.class)
    @PostMapping(value = "/getDefineRoleById")
    public MyCommonResult<DefineRoleVo> doGetDefineRoleById(HttpServletRequest request, HttpServletResponse response,String defineRoleId) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        try{
            DefineRole defineRole = defineRoleMapper.selectById(defineRoleId);
            result.setBean(DefineRoleVo.transferEntityToVo(defineRole));
            dealCommonSuccessCatch(result,"查询角色定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增角色定义", notes = "表单方式新增角色定义", response = String.class)
    @PostMapping(value = "/doAddDefineRole")
    public MyCommonResult<DefineRoleVo> doAddDefineRole(HttpServletRequest request, HttpServletResponse response, DefineRoleVo defineRoleVo){
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        Integer addCount = 0 ;
        try{
            if(defineRoleVo == null) {
                throw new Exception("未接收到有效的角色定义！");
            }   else {
                addCount = defineRoleService.dealAddDefineRole(defineRoleVo) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增角色定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新角色定义", notes = "表单方式更新角色定义", response = String.class)
    @PostMapping(value = "/doUpdateDefineRole")
    public MyCommonResult doUpdateDefineRole(HttpServletRequest request, HttpServletResponse response, DefineRoleVo defineRoleVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(defineRoleVo == null) {
                throw new Exception("未接收到有效的角色定义！");
            }   else {
                changeCount = defineRoleService.dealUpdateDefineRole(defineRoleVo,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新角色定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除角色定义", notes = "根据用户id批量删除角色定义", response = String.class)
    @PostMapping(value = "/batchDelDefineRoleByIds")
    public MyCommonResult doBatchDeleteDefineRoleById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = defineRoleService.dealDelDefineRoleByArr(delIds);
                dealCommonSuccessCatch(result,"批量删除角色定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除角色定义", notes = "根据角色id删除角色定义", response = String.class)
    @PostMapping(value = "/delOneDefineRoleByIds")
    public MyCommonResult doDelOneDefineRoleByIds(HttpServletRequest request, HttpServletResponse response, String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = defineRoleService.dealDelDefineRole(delId);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除角色定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }
}
