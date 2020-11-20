package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.UserRoleService;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.redis.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserRoleEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserRoleMapper;
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
        if (CollectionUtil.isEmpty(userRoleEntityList)) {
            userRoleEntityList = dealGetAllByAccountFromDb(userAccountEntity);
        }
        return userRoleEntityList;
    }


    public List<UserRoleEntity> dealGetAllByAccountFromDb(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        QueryWrapper<UserRoleEntity> userRoleEm = new QueryWrapper<UserRoleEntity>();
        userRoleEm.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccountEntity.getFid());
        userRoleEm.orderBy(true, false, FieldConst.COL_UPDATE_TIME);
        List<UserRoleEntity> userRoleEntityList = userRoleMapper.selectList(userRoleEm);
        return userRoleEntityList;
    }


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


}
