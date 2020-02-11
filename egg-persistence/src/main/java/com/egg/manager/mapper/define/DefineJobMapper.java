package com.egg.manager.mapper.define;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.define.DefineJob;

import java.util.List;

/**
 * <p>
 * 定义的职务 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineJobMapper extends BaseMapper<DefineJob> {


    /**
     * 批量 伪删除
     */
    int batchFakeDelByIds(List<String> delIds) ;
}
