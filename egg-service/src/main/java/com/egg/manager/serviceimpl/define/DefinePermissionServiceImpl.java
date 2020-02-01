package com.egg.manager.serviceimpl.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.mapper.define.DefinePermissionMapper;
import com.egg.manager.service.define.DefinePermissionService;
import com.egg.manager.service.user.UserAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class DefinePermissionServiceImpl extends ServiceImpl<DefinePermissionMapper,DefinePermission> implements DefinePermissionService {

    /**
     * 根据 DefineRoleBean 取得 所有权限
     * @param defineRoles
     * @return
     */
    @Override
    public List<DefinePermission> dealGetAllPermissionByRoles(List<DefineRole> defineRoles) {
        EntityWrapper<DefinePermission> definePermissionEntityWrapper = new EntityWrapper<DefinePermission>() ;
        definePermissionEntityWrapper.where("state={0}", BaseStateEnum.ENABLED.getValue()) ;
        DefineRole role = new DefineRole() ;
        return null;
    }
}
