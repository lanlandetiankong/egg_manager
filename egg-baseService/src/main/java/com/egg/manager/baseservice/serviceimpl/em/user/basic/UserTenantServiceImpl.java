package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.user.basic.UserTenantService;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.redis.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserTenantMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserTenantDto;
import com.egg.manager.persistence.em.user.pojo.transfer.UserTenantTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserTenantVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserTenantService.class)
public class UserTenantServiceImpl extends MyBaseMysqlServiceImpl<UserTenantMapper, UserTenantEntity, UserTenantVo> implements UserTenantService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserTenantMapper userTenantMapper;
    @Autowired
    private DefineTenantMapper defineTenantMapper;

    @Reference
    private RedisHelper redisHelper;


    @Override
    public List<UserTenantEntity> dealGetAllByAccount(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        List<UserTenantEntity> userTenantEntity = dealGetAllByAccountFromRedis(userAccountEntity);
        if (userTenantEntity == null || userTenantEntity.isEmpty()) {
            userTenantEntity = dealGetAllByAccountFromDb(userAccountEntity);
        }
        return userTenantEntity;
    }


    @Override
    public List<UserTenantEntity> dealGetAllByAccountFromDb(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        QueryWrapper<UserTenantEntity> userTenantEm = new QueryWrapper<UserTenantEntity>();
        userTenantEm.eq("state", BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccountEntity.getFid());
        userTenantEm.orderBy(true, false, "update_time");
        List<UserTenantEntity> userTenantEntity = userTenantMapper.selectList(userTenantEm);
        return userTenantEntity;
    }


    @Override
    public List<UserTenantEntity> dealGetAllByAccountFromRedis(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        Object userTenantListObj = redisHelper.hashGet(RedisShiroCacheEnum.userTenant.getKey(), userAccountEntity.getFid());
        String userTenantListJson = JSONObject.toJSONString(userTenantListObj);
        List<UserTenantEntity> userTenantEntity = JSON.parseObject(userTenantListJson, new TypeReference<ArrayList<UserTenantEntity>>() {
        });
        return userTenantEntity;
    }


    @Override
    public MyCommonResult<UserTenantVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserTenantEntity> paginationBean,
                                                               List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserTenantEntity> userTenantEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userTenantMapper.selectCount(userTenantEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userTenantMapper.selectPage(page, userTenantEntityWrapper);
        List<UserTenantEntity> userTenantEntities = iPage.getRecords();
        result.setResultList(UserTenantTransfer.transferEntityToVoList(userTenantEntities));
        return result;
    }


    @Override
    public MyCommonResult<UserTenantVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserTenantDto> paginationBean,
                                                            List<AntdvSortBean> sortBeans) {
        Page<UserTenantDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserTenantDto> userTenantDtoList = userTenantMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserTenantTransfer.transferDtoToVoList(userTenantDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccountEntity loginUser, UserTenantVo userTenantVo) throws Exception {
        Date now = new Date();
        UserTenantEntity userTenantEntity = UserTenantTransfer.transferVoToEntity(userTenantVo);
        userTenantEntity = super.doBeforeCreate(loginUser, userTenantEntity, true);
        Integer addCount = userTenantMapper.insert(userTenantEntity);
        return addCount;
    }


    @Override
    public Integer dealUpdate(UserAccountEntity loginUser, UserTenantVo userTenantVo) throws Exception {
        Integer changeCount = 0;
        UserTenantEntity userTenantEntity = UserTenantTransfer.transferVoToEntity(userTenantVo);
        userTenantEntity = super.doBeforeUpdate(loginUser, userTenantEntity);
        changeCount = userTenantMapper.updateById(userTenantEntity);
        return changeCount;
    }


}
