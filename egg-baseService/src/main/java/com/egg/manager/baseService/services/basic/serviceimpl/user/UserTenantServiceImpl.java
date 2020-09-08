package com.egg.manager.baseService.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.user.UserTenantService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.db.mysql.mapper.user.UserTenantMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserTenantTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserTenantVo;
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
 * \* Description:
 * \
 */
@Service(interfaceClass = UserTenantService.class)
public class UserTenantServiceImpl extends ServiceImpl<UserTenantMapper,UserTenant> implements UserTenantService {

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private UserTenantMapper userTenantMapper ;
    @Autowired
    private DefineTenantMapper defineTenantMapper ;
    @Reference
    private CommonFuncService commonFuncService ;

    @Reference
    private RedisHelper redisHelper ;



    /**
     * 取得当前用户关联的 UserTenant
     * @return
     */
    @Override
    public List<UserTenant> dealGetAllUserTenantByAccount(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        List<UserTenant> userTenant = dealGetAllUserTenantByAccountFromRedis(userAccount);
        if(userTenant == null || userTenant.isEmpty()) {
            userTenant = dealGetAllUserTenantByAccountFromDb(userAccount);
        }
        return userTenant;
    }
    /**
     * 从数据库是中取得当前用户关联的 UserTenant
     * @return
     */
    @Override
    public List<UserTenant> dealGetAllUserTenantByAccountFromDb(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        EntityWrapper<UserTenant> userTenantEm = new EntityWrapper<UserTenant>() ;
        userTenantEm.where("state={0}", BaseStateEnum.ENABLED.getValue())
                .and("user_account_id={0}",userAccount.getFid());
        userTenantEm.orderBy("update_time",false);
        List<UserTenant> userTenant = selectList(userTenantEm);
        return userTenant;
    }

    /**
     * 从Redis中取得当前用户关联的 UserTenant
     * @return
     */
    @Override
    public List<UserTenant> dealGetAllUserTenantByAccountFromRedis(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        Object  userTenantListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserTenantKey(),userAccount.getFid()) ;
        String userTenantListJson = JSONObject.toJSONString(userTenantListObj);
        List<UserTenant> userTenant  = JSON.parseObject(userTenantListJson, new TypeReference<ArrayList<UserTenant>>(){}) ;
        return userTenant;
    }






    private boolean checkUserAccountIsBlank(UserAccount userAccount) {
        if(userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return true ;
        }
        return false;
    }







    /**
     * 分页查询 用户与租户关联 列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserTenantVo> dealGetUserTenantPages(MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                               List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<UserTenant> userTenantEntityWrapper = new EntityWrapper<UserTenant>();
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到userTenantEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(userTenantEntityWrapper,queryFormFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                userTenantEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = userTenantMapper.selectCount(userTenantEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<UserTenant> userTenants = userTenantMapper.selectPage(rowBounds,userTenantEntityWrapper) ;
        result.setResultList(UserTenantTransfer.transferEntityToVoList(userTenants));
        return result;
    }


    /**
     * 分页查询 用户与租户关联  Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserTenantVo> dealGetUserTenantDtoPages(MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                  List<AntdvSortBean> sortBeans){
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<UserTenantDto> userTenantDtoList = userTenantMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(UserTenantTransfer.transferDtoToVoList(userTenantDtoList));
        return result;
    }


    /**
     * 用户与租户关联 -新增
     * @param userTenantVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddUserTenant(UserTenantVo userTenantVo, UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        UserTenant userTenant = UserTenantTransfer.transferVoToEntity(userTenantVo);
        userTenant.setFid(MyUUIDUtil.renderSimpleUUID());
        userTenant.setState(BaseStateEnum.ENABLED.getValue());
        userTenant.setCreateTime(now);
        userTenant.setUpdateTime(now);
        if(loginUser != null){
            userTenant.setCreateUserId(loginUser.getFid());
            userTenant.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = userTenantMapper.insert(userTenant) ;
        return addCount ;
    }


    /**
     * 用户与租户关联 -更新
     * @param userTenantVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateUserTenant(UserTenantVo userTenantVo, UserAccount loginUser, boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        userTenantVo.setUpdateTime(now);
        UserTenant userTenant = UserTenantTransfer.transferVoToEntity(userTenantVo);
        if(loginUser != null){
            userTenant.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = userTenantMapper.updateAllColumnById(userTenant) ;
        }   else {
            changeCount = userTenantMapper.updateById(userTenant) ;
        }
        return changeCount ;
    }



    /**
     * 用户与租户关联 -删除
     * @param delIds 要删除的用户与租户关联 id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserTenantByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = userTenantMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 用户与租户关联 -删除
     * @param delId 要删除的用户与租户关联 id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserTenant(String delId,UserAccount loginUser) throws Exception{
        UserTenant userTenant = UserTenant.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            userTenant.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = userTenantMapper.updateById(userTenant);
        return delCount ;
    }


}
