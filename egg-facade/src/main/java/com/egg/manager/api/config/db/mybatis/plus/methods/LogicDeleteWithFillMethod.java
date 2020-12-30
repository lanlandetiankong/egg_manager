package com.egg.manager.api.config.db.mybatis.plus.methods;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/27
 */
@Slf4j
public class LogicDeleteWithFillMethod extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BY_ID;
        //entity是否指定逻辑值，没指定将直接删除数据
        if (tableInfo.isWithLogicDelete()) {
            //过滤掉： 没有新增_更新/更新 策略的字段,并忽略掉 @TableLogic 的字段
            List<TableFieldInfo> fieldInfos = tableInfo.getFieldList().stream()
                    .filter(i -> i.getFieldFill() == FieldFill.UPDATE || i.getFieldFill() == FieldFill.INSERT_UPDATE && (i.getField().isAnnotationPresent(TableLogic.class) == false))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(fieldInfos)) {
                //更新的字段：有新增/更新策略注解的+逻辑值
                String sqlSet = "SET " + fieldInfos.stream().map(i -> i.getSqlSet(ENTITY_DOT)).collect(Collectors.joining(EMPTY))
                        + tableInfo.getLogicDeleteSql(false, false);
                sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
                        tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                        tableInfo.getLogicDeleteSql(true, true));
            } else {
                //更新的字段:逻辑值
                sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
                        tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                        tableInfo.getLogicDeleteSql(true, true));
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BY_ID;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    tableInfo.getKeyProperty());
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }
    }

    @Override
    public String getMethod(SqlMethod sqlMethod) {
        // 自定义 mapper 方法名
        return "deleteByIdsWithFill";
    }

}