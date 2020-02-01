package com.egg.manager.serviceimpl.role;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.role.RolePermission;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.role.RolePermissionMapper;
import com.egg.manager.service.role.RolePermissionService;
import com.egg.manager.service.user.UserAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper,RolePermission>  implements RolePermissionService {


    @Override
    public List<DefinePermission> dealGetAllPermissionByRoles(List<DefineRole> defineRoles) {
        if(defineRoles == null || defineRoles.isEmpty()) {
            return null ;
        }
        //所有的角色id
        Set<String> defineRoleIds = new HashSet<String>();
        for (DefineRole role : defineRoles){
            if(role != null && StringUtils.isNotBlank(role.getFid())){
                defineRoleIds.add(role.getFid());
            }
        }
        //取得所有的 RolePermission
        EntityWrapper<RolePermission> rolePermissionEw = new EntityWrapper<RolePermission>() ;
        rolePermissionEw.where("state={0}", BaseStateEnum.ENABLED.getValue())
                .in(true,"define_role_id",defineRoleIds);
        selectList(rolePermissionEw) ;
        return null;
    }
}
