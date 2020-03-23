package com.egg.manager.persistence.mapper.role;

import com.egg.manager.persistence.entity.role.RolePermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.persistence.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    /**
     * 批量新增 角色-权限 关联
     * @param permissionList
     * @return
     */
    int customBatchInsert(List<RolePermission> permissionList);

    /**
     * 根据角色id 修改指定权限 的可用状态
     * @param roleId
     * @param permissionIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") String roleId,@Param("permissionIdList")List<String> permissionIdList,@Param("stateVal")Integer stateVal
                                    ,@Param("loginUser")UserAccount loginUser);
}
