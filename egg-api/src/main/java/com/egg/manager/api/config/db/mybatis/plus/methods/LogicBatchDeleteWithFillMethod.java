package com.egg.manager.api.config.db.mybatis.plus.methods;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 根据ids更新状态为
 * @author zhoucj
 * @date 2020/10/27
 */
@Slf4j
public class LogicBatchDeleteWithFillMethod extends AbstractMethod {

    /**
     * mapper 对应的方法名
     */
    private static final String MAPPER_METHOD = "batchDeleteByIdsWithFill";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BATCH_BY_IDS;
        if (tableInfo.isWithLogicDelete()) {
            //过滤掉： 没有新增_更新/更新 策略的字段,并忽略掉 @TableLogic 的字段
            List<TableFieldInfo> fieldInfos = tableInfo.getFieldList().stream()
                    .filter(i -> i.getFieldFill() == FieldFill.UPDATE || i.getFieldFill() == FieldFill.INSERT_UPDATE && (i.getField().isAnnotationPresent(TableLogic.class) == false))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(fieldInfos)) {
                String sqlSet = "SET " + fieldInfos.stream().map(i -> getNotNullSetSql(i)).collect(Collectors.joining(EMPTY))
                        + tableInfo.getLogicDeleteSql(false, false);
                //逻辑值 && 版本号
                String additional = tableInfo.getLogicDeleteSql(true, true)+optlockVersion(tableInfo);
                sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
                        tableInfo.getKeyColumn(),
                        SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
                        additional);
            } else {
                //逻辑值 && 版本号
                String additional = tableInfo.getLogicDeleteSql(true, true)+optlockVersion(tableInfo);
                sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
                        tableInfo.getKeyColumn(),
                        SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
                        additional);
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return this.addUpdateMappedStatement(mapperClass, modelClass, MAPPER_METHOD, sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }
    }


    public String getNotNullSetSql(TableFieldInfo fieldInfo) {
        if(fieldInfo == null){
            return "" ;
        }
        String partenSql = "<if test=\"%s!= null\"> \n %s \n </if>";
        String propertyName = ENTITY_DOT+fieldInfo.getProperty();
        return String.format(partenSql,propertyName,fieldInfo.getSqlSet(ENTITY_DOT)) ;
    }

}