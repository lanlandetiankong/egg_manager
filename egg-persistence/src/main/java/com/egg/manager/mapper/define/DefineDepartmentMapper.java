package com.egg.manager.mapper.define;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.define.DefineDepartment;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 定义的部门 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineDepartmentMapper extends BaseMapper<DefineDepartment> {

    //批量 伪删除
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;
}
