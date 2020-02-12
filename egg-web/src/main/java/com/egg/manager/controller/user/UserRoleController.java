package com.egg.manager.controller.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.mapper.user.UserRoleMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.service.user.UserRoleService;
import com.egg.manager.vo.user.UserRoleVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
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
@RequestMapping("/user/user_role")
public class UserRoleController  extends BaseController{

    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private UserRoleMapper userRoleMapper ;
    @Autowired
    private UserRoleService userRoleService ;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());






    @ApiOperation(value = "查询用户角色列表", notes = "查询用户角色列表", response = String.class)
    @PostMapping(value = "/getAllUserRoles")
    public MyCommonResult<UserRoleVo> doGetAllUserRoles(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj) {
        MyCommonResult<UserRoleVo> result = new MyCommonResult<UserRoleVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            userRoleService.dealGetUserRolePages(result,queryFormFieldBeanList,paginationBean);
            dealCommonSuccessCatch(result,"查询用户角色信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户角色信息", notes = "根据用户角色id查询用户角色信息", response = String.class)
    @PostMapping(value = "/getUserRoleById")
    public MyCommonResult<UserRoleVo> doGetUserRoleById(HttpServletRequest request, HttpServletResponse response,String roleId) {
        MyCommonResult<UserRoleVo> result = new MyCommonResult<UserRoleVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            UserRole vo = userRoleMapper.selectById(roleId);
            result.setBean(UserRoleVo.transferEntityToVo(vo));
            dealCommonSuccessCatch(result,"查询用户角色信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增用户角色", notes = "表单方式新增用户角色", response = String.class)
    @PostMapping(value = "/doAddUserRole")
    public MyCommonResult doAddUserRole(HttpServletRequest request, HttpServletResponse response, UserRoleVo userRoleVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(userRoleVo == null) {
                throw new Exception("未接收到有效的用户角色信息！");
            }   else {
                addCount = userRoleService.dealAddUserRole(userRoleVo,loginUser);
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增用户角色:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    

    @ApiOperation(value = "批量删除用户角色", notes = "根据用户角色id批量删除用户角色", response = String.class)
    @PostMapping(value = "/batchDelUserRoleByIds")
    public MyCommonResult doBatchDeleteUserRoleById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = userRoleService.dealDelUserRoleByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除用户角色:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除用户角色", notes = "根据用户角色id删除用户角色", response = String.class)
    @PostMapping(value = "/delOneUserRoleByIds")
    public MyCommonResult doDelOneUserRoleById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                delCount = userRoleService.dealDelUserRole(delId,loginUser);
                dealCommonSuccessCatch(result,"删除用户角色:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }













}
