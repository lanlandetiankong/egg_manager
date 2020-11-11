package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.UserRoleService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.redis.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserRoleEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserRoleMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserRoleDto;
import com.egg.manager.persistence.em.user.pojo.transfer.UserRoleTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserRoleService.class)
public class UserRoleServiceImpl extends MyBaseMysqlServiceImpl<UserRoleMapper, UserRoleEntity, UserRoleVo> implements UserRoleService {

    @Reference
    private RedisHelper redisHelper;
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public List<UserRoleEntity> dealGetAllByAccount(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        List<UserRoleEntity> userRoleEntityList = dealGetAllByAccountFromRedis(userAccountEntity);
        if (userRoleEntityList == null || userRoleEntityList.isEmpty()) {
            userRoleEntityList = dealGetAllByAccountFromDb(userAccountEntity);
        }
        return userRoleEntityList;
    }


    @Override
    public List<UserRoleEntity> dealGetAllByAccountFromDb(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        QueryWrapper<UserRoleEntity> userRoleEm = new QueryWrapper<UserRoleEntity>();
        userRoleEm.eq("state", BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccountEntity.getFid());
        userRoleEm.orderBy(true, false, "update_time");
        List<UserRoleEntity> userRoleEntityList = userRoleMapper.selectList(userRoleEm);
        return userRoleEntityList;
    }


    @Override
    public List<UserRoleEntity> dealGetAllByAccountFromRedis(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        Object userRoleListObj = redisHelper.hashGet(RedisShiroCacheEnum.userRoles.getKey(), userAccountEntity.getFid());
        String userRoleListJson = JSONObject.toJSONString(userRoleListObj);
        List<UserRoleEntity> userRoleEntityList = JSON.parseObject(userRoleListJson, new TypeReference<ArrayList<UserRoleEntity>>() {
        });
        return userRoleEntityList;
    }


    @Override
    public MyCommonResult<UserRoleVo> dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserRoleEntity> paginationBean,
                                                             List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserRoleEntity> userRoleEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userRoleMapper.selectCount(userRoleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userRoleMapper.selectPage(page, userRoleEntityWrapper);
        List<UserRoleEntity> userRoleEntities = iPage.getRecords();
        result.setResultList(UserRoleTransfer.transferEntityToVoList(userRoleEntities));
        return result;
    }


    @Override
    public MyCommonResult<UserRoleVo> dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserRoleDto> paginationBean,
                                                          List<AntdvSortBean> sortBeans) {
        Page<UserRoleDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserRoleDto> userRoleDtoList = userRoleMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserRoleTransfer.transferDtoToVoList(userRoleDtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, UserRoleVo userRoleVo) throws Exception {
        UserRoleEntity userRoleEntity = UserRoleTransfer.transferVoToEntity(userRoleVo);
        userRoleEntity = super.doBeforeCreate(loginUserInfo, userRoleEntity, true);
        Integer addCount = userRoleMapper.insert(userRoleEntity);
        return addCount;
    }

    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, UserRoleVo userRoleVo) throws Exception {
        Integer changeCount = 0;
        UserRoleEntity userRoleEntity = UserRoleTransfer.transferVoToEntity(userRoleVo);
        userRoleEntity = super.doBeforeUpdate(loginUserInfo, userRoleEntity);
        changeCount = userRoleMapper.updateById(userRoleEntity);
        return changeCount;
    }


}
