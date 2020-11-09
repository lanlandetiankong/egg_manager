package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.services.em.user.basic.RolePermissionService;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.util.LongUtils;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRoleEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RolePermissionMapper;
import com.egg.manager.persistence.em.user.pojo.vo.RolePermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = RolePermissionService.class)
public class RolePermissionServiceImpl extends MyBaseMysqlServiceImpl<RolePermissionMapper, RolePermissionEntity, RolePermissionVo> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public List<DefinePermissionEntity> dealQueryPageByEntitys(List<DefineRoleEntity> defineRoleEntities) {
        if (defineRoleEntities == null || defineRoleEntities.isEmpty()) {
            return null;
        }
        //所有的角色id
        Set<Long> defineRoleIds = new HashSet<Long>();
        for (DefineRoleEntity role : defineRoleEntities) {
            if (role != null && LongUtils.isNotBlank(role.getFid())) {
                defineRoleIds.add(role.getFid());
            }
        }
        //取得所有的 RolePermission
        QueryWrapper<RolePermissionEntity> rolePermissionEw = new QueryWrapper<RolePermissionEntity>();
        rolePermissionEw.eq("state", BaseStateEnum.ENABLED.getValue())
                .in(true, "define_role_id", defineRoleIds);
        rolePermissionMapper.selectList(rolePermissionEw);
        return null;
    }
}
