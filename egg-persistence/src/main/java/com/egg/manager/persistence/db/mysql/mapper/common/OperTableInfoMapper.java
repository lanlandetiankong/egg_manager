package com.egg.manager.persistence.db.mysql.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @ClassName: OperTableInfoMapper
 * @Author: zhoucj
 * @Date: 2020/10/14 17:12
 */
public interface OperTableInfoMapper {


    List<String> getAllTableName(@Param("databaseName") String databaseName);
}
