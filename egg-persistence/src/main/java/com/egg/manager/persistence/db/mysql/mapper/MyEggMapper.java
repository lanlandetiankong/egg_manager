package com.egg.manager.persistence.db.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: mybatisplus自定义增强扩展方法
 * @ClassName: MyEggMapper
 * @Author: zhoucj
 * @Date: 2020/10/26 17:10
 */
public interface  MyEggMapper<T> extends BaseMapper<T> {
    /**
     * 根据id伪删除
     * @return
     */
    int fakeDeleteById(@Param("fid") Serializable fid);

    /**
     * 批量逻辑删除并填充字段
     * @param entity 实体类
     * @return
     */
    int batchDeleteByIdsWithFill(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList,@Param(Constants.ENTITY)T entity);
}
