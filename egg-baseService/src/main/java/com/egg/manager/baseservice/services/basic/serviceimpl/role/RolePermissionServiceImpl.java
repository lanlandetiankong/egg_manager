package com.egg.manager.baseservice.services.basic.serviceimpl.role;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.services.basic.role.RolePermissionService;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.LongUtils;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermission;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRole;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermission;
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
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = RolePermissionService.class)
public class RolePermissionServiceImpl extends MyBaseMysqlServiceImpl<RolePermissionMapper, RolePermission, RolePermissionVo> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public List<DefinePermission> dealQueryPageByEntitys(List<DefineRole> defineRoles) {
        if (defineRoles == null || defineRoles.isEmpty()) {
            return null;
        }
        //所有的角色id
        Set<Long> defineRoleIds = new HashSet<Long>();
        for (DefineRole role : defineRoles) {
            if (role != null && LongUtils.isNotBlank(role.getFid())) {
                defineRoleIds.add(role.getFid());
            }
        }
        //取得所有的 RolePermission
        QueryWrapper<RolePermission> rolePermissionEw = new QueryWrapper<RolePermission>();
        rolePermissionEw.eq("state", BaseStateEnum.ENABLED.getValue())
                .in(true, "define_role_id", defineRoleIds);
        rolePermissionMapper.selectList(rolePermissionEw);
        return null;
    }
}
