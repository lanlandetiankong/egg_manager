package com.egg.manager.mapper.organization;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.organization.DefineTenant;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 租户表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineTenantMapper extends BaseMapper<DefineTenant> {
    //批量 伪删除
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;


}
