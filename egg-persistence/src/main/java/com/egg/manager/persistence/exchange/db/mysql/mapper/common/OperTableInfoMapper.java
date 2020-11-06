package com.egg.manager.persistence.exchange.db.mysql.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface OperTableInfoMapper {

    /**
     * 取得所有 表名
     * @param databaseName
     * @return
     */
    List<String> getAllTableName(@Param("databaseName") String databaseName);
}
