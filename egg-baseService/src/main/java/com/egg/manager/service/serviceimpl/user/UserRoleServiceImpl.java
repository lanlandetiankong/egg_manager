package com.egg.manager.service.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.service.redis.service.RedisHelper;
import com.egg.manager.api.service.service.CommonFuncService;
import com.egg.manager.api.service.service.user.UserRoleService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.pojo.dto.user.UserRoleDto;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.mapper.user.UserRoleMapper;
import com.egg.manager.persistence.pojo.transfer.user.UserRoleTransfer;
import com.egg.manager.persistence.pojo.vo.user.UserRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = UserRoleService.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService {
    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;
    @Reference
    private RedisHelper redisHelper ;
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private UserRoleMapper userRoleMapper ;
    @Reference
    private CommonFuncService commonFuncService ;




    /**
     * 取得当前用户关联的 UserRole
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccount(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        List<UserRole> userRoleList = dealGetAllUserRoleByAccountFromRedis(userAccount);
        if(userRoleList == null || userRoleList.isEmpty()) {
            userRoleList = dealGetAllUserRoleByAccountFromDb(userAccount);
        }
        return userRoleList;
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
        List<UserRole> userRoleList = selectList(userRoleEm);
        return userRoleList;
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
        Object  userRoleListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserRolesKey(),userAccount.getFid()) ;
        String userRoleListJson = JSONObject.toJSONString(userRoleListObj);
        List<UserRole> userRoleList  = JSON.parseObject(userRoleListJson, new TypeReference<ArrayList<UserRole>>(){}) ;
        return userRoleList;
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
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
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
        result.setResultList(UserRoleTransfer.transferEntityToVoList(userRoles));
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
        result.setResultList(UserRoleTransfer.transferDtoToVoList(userRoleDtoList));
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
        UserRole userRole = UserRoleTransfer.transferVoToEntity(userRoleVo);
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
        UserRole userRole = UserRoleTransfer.transferVoToEntity(userRoleVo);
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
