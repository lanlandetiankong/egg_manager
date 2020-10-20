package com.egg.manager.baseservice.services.basic.serviceimpl.role;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.services.basic.role.RolePermissionService;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.mapper.role.RolePermissionMapper;
import com.egg.manager.persistence.pojo.mysql.vo.role.RolePermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
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
        Set<String> defineRoleIds = new HashSet<String>();
        for (DefineRole role : defineRoles) {
            if (role != null && StringUtils.isNotBlank(role.getFid())) {
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
