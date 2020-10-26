package com.egg.manager.persistence.db.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

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
    int fakeDeleteById(Serializable fid);


}
