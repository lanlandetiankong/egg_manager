package com.egg.manager.mapper.define;

import com.egg.manager.entity.define.DefineMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineMenuMapper extends BaseMapper<DefineMenu> {

    //批量 伪删除
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;


    /**
     * 查询菜单(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<DefineMenu> getMenusFilterChildrens(@Param("filterId")String filterId,@Param("onlyEnable")boolean onlyEnable);


}
