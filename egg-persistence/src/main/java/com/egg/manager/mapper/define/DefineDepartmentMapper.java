package com.egg.manager.mapper.define;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.define.DefineDepartment;
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

    /**
     * 查询部门(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<DefineDepartment> getDepartmentFilterChildrens(@Param("filterId")String filterId, @Param("onlyEnable")boolean onlyEnable);
}
