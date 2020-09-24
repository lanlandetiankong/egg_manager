package com.egg.manager.baseService.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.user.UserTenantService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.db.mysql.mapper.user.UserTenantMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserTenantTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserTenantVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserTenantService.class)
public class UserTenantServiceImpl extends MyBaseMysqlServiceImpl<UserTenantMapper, UserTenant, UserTenantVo> implements UserTenantService {

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache;
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserTenantMapper userTenantMapper;
    @Autowired
    private DefineTenantMapper defineTenantMapper;

    @Reference
    private RedisHelper redisHelper;



    @Override
    public List<UserTenant> dealGetAllByAccount(UserAccount userAccount) {
        if (super.checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        List<UserTenant> userTenant = dealGetAllByAccountFromRedis(userAccount);
        if (userTenant == null || userTenant.isEmpty()) {
            userTenant = dealGetAllByAccountFromDb(userAccount);
        }
        return userTenant;
    }


    @Override
    public List<UserTenant> dealGetAllByAccountFromDb(UserAccount userAccount) {
        if (super.checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        QueryWrapper<UserTenant> userTenantEm = new QueryWrapper<UserTenant>();
        userTenantEm.eq("state", BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccount.getFid());
        userTenantEm.orderBy(true,false,"update_time");
        List<UserTenant> userTenant = userTenantMapper.selectList(userTenantEm);
        return userTenant;
    }


    @Override
    public List<UserTenant> dealGetAllByAccountFromRedis(UserAccount userAccount) {
        if (super.checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        Object userTenantListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserTenantKey(), userAccount.getFid());
        String userTenantListJson = JSONObject.toJSONString(userTenantListObj);
        List<UserTenant> userTenant = JSON.parseObject(userTenantListJson, new TypeReference<ArrayList<UserTenant>>() {
        });
        return userTenant;
    }


    @Override
    public MyCommonResult<UserTenantVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserTenant> paginationBean,
                                                               List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserTenant> userTenantEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userTenantMapper.selectCount(userTenantEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userTenantMapper.selectPage(page, userTenantEntityWrapper);
        List<UserTenant> userTenants = iPage.getRecords();
        result.setResultList(UserTenantTransfer.transferEntityToVoList(userTenants));
        return result;
    }


    @Override
    public MyCommonResult<UserTenantVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserTenantDto> paginationBean,
                                                                  List<AntdvSortBean> sortBeans) {
        Page<UserTenantDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserTenantDto> userTenantDtoList = userTenantMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserTenantTransfer.transferDtoToVoList(userTenantDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, UserTenantVo userTenantVo) throws Exception {
        Date now = new Date();
        UserTenant userTenant = UserTenantTransfer.transferVoToEntity(userTenantVo);
        userTenant = super.doBeforeCreate(loginUser, userTenant, true);
        Integer addCount = userTenantMapper.insert(userTenant);
        return addCount;
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, UserTenantVo userTenantVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        UserTenant userTenant = UserTenantTransfer.transferVoToEntity(userTenantVo);
        userTenant = super.doBeforeUpdate(loginUser, userTenant);
        if (updateAll) {  //是否更新所有字段
            changeCount = userTenantMapper.updateById(userTenant);
        } else {
            changeCount = userTenantMapper.updateById(userTenant);
        }
        return changeCount;
    }


    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = userTenantMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        UserTenant userTenant = super.doBeforeDeleteOneById(loginUser, UserTenant.class, delId);
        Integer delCount = userTenantMapper.updateById(userTenant);
        return delCount;
    }


}
