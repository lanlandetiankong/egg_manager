package com.egg.manager.mapper.define;

import com.egg.manager.entity.define.DefinePermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefinePermissionMapper extends BaseMapper<DefinePermission> {
    //批量 伪删除
    int batchFakeDelByIds(List<String> delIds) ;
}
