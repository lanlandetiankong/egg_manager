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


    /**
     * 取得当前用户关联的 UserTenant
     *
     * @return
     */
    @Override
    public List<UserTenant> dealGetAllUserTenantByAccount(UserAccount userAccount) {
        if (checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        List<UserTenant> userTenant = dealGetAllUserTenantByAccountFromRedis(userAccount);
        if (userTenant == null || userTenant.isEmpty()) {
            userTenant = dealGetAllUserTenantByAccountFromDb(userAccount);
        }
        return userTenant;
    }

    /**
     * 从数据库是中取得当前用户关联的 UserTenant
     *
     * @return
     */
    @Override
    public List<UserTenant> dealGetAllUserTenantByAccountFromDb(UserAccount userAccount) {
        if (checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        QueryWrapper<UserTenant> userTenantEm = new QueryWrapper<UserTenant>();
        userTenantEm.eq("state", BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccount.getFid());
        userTenantEm.orderBy(true,false,"update_time");
        List<UserTenant> userTenant = userTenantMapper.selectList(userTenantEm);
        return userTenant;
    }

    /**
     * 从Redis中取得当前用户关联的 UserTenant
     *
     * @return
     */
    @Override
    public List<UserTenant> dealGetAllUserTenantByAccountFromRedis(UserAccount userAccount) {
        if (checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        Object userTenantListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserTenantKey(), userAccount.getFid());
        String userTenantListJson = JSONObject.toJSONString(userTenantListObj);
        List<UserTenant> userTenant = JSON.parseObject(userTenantListJson, new TypeReference<ArrayList<UserTenant>>() {
        });
        return userTenant;
    }


    private boolean checkUserAccountIsBlank(UserAccount userAccount) {
        if (userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return true;
        }
        return false;
    }


    /**
     * 分页查询 用户与租户关联 列表
     *
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserTenantVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
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


    /**
     * 分页查询 用户与租户关联  Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserTenantVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                  List<AntdvSortBean> sortBeans) {
        Page<UserTenantDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserTenantDto> userTenantDtoList = userTenantMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserTenantTransfer.transferDtoToVoList(userTenantDtoList));
        return result;
    }


    /**
     * 用户与租户关联 -新增
     *
     * @param userTenantVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealCreate(UserAccount loginUser, UserTenantVo userTenantVo) throws Exception {
        Date now = new Date();
        UserTenant userTenant = UserTenantTransfer.transferVoToEntity(userTenantVo);
        userTenant = super.doBeforeCreate(loginUser, userTenant, true);
        Integer addCount = userTenantMapper.insert(userTenant);
        return addCount;
    }


    /**
     * 用户与租户关联 -更新
     *
     * @param userTenantVo
     * @param updateAll    是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
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


    /**
     * 用户与租户关联 -删除
     *
     * @param delIds 要删除的用户与租户关联 id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
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

    /**
     * 用户与租户关联 -删除
     *
     * @param delId 要删除的用户与租户关联 id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        UserTenant userTenant = super.doBeforeDeleteOneById(loginUser, UserTenant.class, delId);
        ;
        Integer delCount = userTenantMapper.updateById(userTenant);
        return delCount;
    }


}
