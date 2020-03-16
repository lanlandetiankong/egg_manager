package com.egg.manager.serviceimpl.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.dto.define.DefineDepartmentDto;
import com.egg.manager.dto.user.UserRoleDto;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.user.UserRoleMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserRoleService;
import com.egg.manager.vo.define.DefineDepartmentVo;
import com.egg.manager.vo.user.UserRoleVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    /**
     * TODO
     * @return
     */
    @Override
    public List<UserRole> selectByAccountId() {
        EntityWrapper<UserRole> ew = new EntityWrapper<>() ;
        ew.where("");

        return null;
    }



    /**
     * 取得当前用户关联的 UserRole
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccount(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        List<UserRole> userDefineRoles = dealGetAllUserRoleByAccountFromRedis(userAccount);
        if(userDefineRoles == null || userDefineRoles.isEmpty()) {
            userDefineRoles = dealGetAllUserRoleByAccountFromDb(userAccount);
        }
        return userDefineRoles;
    }
    /**
     * 从数据库是中取得当前用户关联的 UserRole
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccountFromDb(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        EntityWrapper<UserRole> userRoleEm = new EntityWrapper<UserRole>() ;
        userRoleEm.where("state={0}", BaseStateEnum.ENABLED.getValue())
                .and("user_account_id={0}",userAccount.getFid());
        userRoleEm.orderBy("update_time",false);
        List<UserRole> userDefineRoles = selectList(userRoleEm);
        return userDefineRoles;
    }

    /**
     * 从Redis中取得当前用户关联的 UserRole
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccountFromRedis(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        Object  userRoleListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserRoleKey(),userAccount.getFid()) ;
        String userRoleListJson = JSONObject.toJSONString(userRoleListObj);
        List<UserRole> userDefineRoles  = JSON.parseObject(userRoleListJson, new TypeReference<ArrayList<UserRole>>(){}) ;
        return userDefineRoles;
    }






    private boolean checkUserAccountIsBlank(UserAccount userAccount) {
        if(userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return true ;
        }
        return false;
    }







    /**
     * 分页查询 用户角色列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserRolePages(MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                     List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<UserRole> userRoleEntityWrapper = new EntityWrapper<UserRole>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到userRoleEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(userRoleEntityWrapper,queryFormFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                userRoleEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = userRoleMapper.selectCount(userRoleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<UserRole> userRoles = userRoleMapper.selectPage(rowBounds,userRoleEntityWrapper) ;
        result.setResultList(UserRoleVo.transferEntityToVoList(userRoles));
    }


    /**
     * 分页查询 用户角色 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserRoleDtoPages(MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                     List<AntdvSortBean> sortBeans){
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<UserRoleDto> userRoleDtoList = userRoleMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(UserRoleVo.transferDtoToVoList(userRoleDtoList));
    }


    /**
     * 用户角色-新增
     * @param userRoleVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddUserRole(UserRoleVo userRoleVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        UserRole userRole = UserRoleVo.transferVoToEntity(userRoleVo);
        userRole.setFid(MyUUIDUtil.renderSimpleUUID());
        userRole.setState(BaseStateEnum.ENABLED.getValue());
        userRole.setCreateTime(now);
        userRole.setUpdateTime(now);
        if(loginUser != null){
            userRole.setCreateUserId(loginUser.getFid());
            userRole.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = userRoleMapper.insert(userRole) ;
        return addCount ;
    }


    /**
     * 用户角色-更新
     * @param userRoleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateUserRole(UserRoleVo userRoleVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        userRoleVo.setUpdateTime(now);
        UserRole userRole = UserRoleVo.transferVoToEntity(userRoleVo);
        if(loginUser != null){
            userRole.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = userRoleMapper.updateAllColumnById(userRole) ;
        }   else {
            changeCount = userRoleMapper.updateById(userRole) ;
        }
        return changeCount ;
    }



    /**
     * 用户角色-删除
     * @param delIds 要删除的用户角色id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserRoleByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = userRoleMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 用户角色-删除
     * @param delId 要删除的用户角色id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserRole(String delId,UserAccount loginUser) throws Exception{
        UserRole userRole = UserRole.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            userRole.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = userRoleMapper.updateById(userRole);
        return delCount ;
    }
}
