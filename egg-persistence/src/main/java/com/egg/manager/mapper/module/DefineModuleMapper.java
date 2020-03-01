package com.egg.manager.mapper.module;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.module.DefineModule;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineModuleMapper extends BaseMapper<DefineModule> {

    //批量 伪删除
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;
}
