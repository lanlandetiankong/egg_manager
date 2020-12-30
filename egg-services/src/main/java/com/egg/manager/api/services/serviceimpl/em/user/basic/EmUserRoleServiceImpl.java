package com.egg.manager.api.services.serviceimpl.em.user.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.facade.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.facade.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.user.basic.EmUserRoleService;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.enums.db.RedisShiroCacheEnum;
import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserRoleEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserRoleMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserRoleVo;
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
@Service(interfaceClass = EmUserRoleService.class)
public class EmUserRoleServiceImpl extends MyBaseMysqlServiceImpl<EmUserRoleMapper, EmUserRoleEntity, EmUserRoleVo> implements EmUserRoleService {

    @Reference
    private RedisHelper redisHelper;
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmUserRoleMapper emUserRoleMapper;


    @Override
    public List<EmUserRoleEntity> dealGetAllByAccount(EmUserAccountEntity emUserAccountEntity) {
        if (super.checkUserAccountIsBlank(emUserAccountEntity) == true) {
            return null;
        }
        List<EmUserRoleEntity> emUserRoleEntityList = dealGetAllByAccountFromRedis(emUserAccountEntity);
        if (CollectionUtil.isEmpty(emUserRoleEntityList)) {
            emUserRoleEntityList = dealGetAllByAccountFromDb(emUserAccountEntity);
        }
        return emUserRoleEntityList;
    }


    public List<EmUserRoleEntity> dealGetAllByAccountFromDb(EmUserAccountEntity emUserAccountEntity) {
        if (super.checkUserAccountIsBlank(emUserAccountEntity) == true) {
            return null;
        }
        QueryWrapper<EmUserRoleEntity> userRoleEm = new QueryWrapper<EmUserRoleEntity>();
        userRoleEm.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", emUserAccountEntity.getFid());
        userRoleEm.orderBy(true, false, FieldConst.COL_UPDATE_TIME);
        List<EmUserRoleEntity> emUserRoleEntityList = emUserRoleMapper.selectList(userRoleEm);
        return emUserRoleEntityList;
    }


    public List<EmUserRoleEntity> dealGetAllByAccountFromRedis(EmUserAccountEntity emUserAccountEntity) {
        if (super.checkUserAccountIsBlank(emUserAccountEntity) == true) {
            return null;
        }
        Object userRoleListObj = redisHelper.hashGet(RedisShiroCacheEnum.userRoles.getKey(), emUserAccountEntity.getFid());
        String userRoleListJson = JSONObject.toJSONString(userRoleListObj);
        List<EmUserRoleEntity> emUserRoleEntityList = JSON.parseObject(userRoleListJson, new TypeReference<ArrayList<EmUserRoleEntity>>() {
        });
        return emUserRoleEntityList;
    }


}
