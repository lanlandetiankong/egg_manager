package com.egg.manager.facade.api.config.db.mybatis.plus.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.egg.manager.facade.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @Description:
 * @ClassName: LogicDeleteByIdWithModifyFill
 * @Author: zhoucj
 * @Date: 2020/10/28 10:17
 */
public class LogicDeleteByIdWithModifyFill extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BY_ID;
        if (tableInfo.isWithLogicDelete()) {
            String sqlSet = "SET " + tableInfo.getLogicDeleteSql(false, false) + getLoginUserSetSql(tableInfo, true);
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
                    tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                    tableInfo.getLogicDeleteSql(true, true));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BY_ID;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    tableInfo.getKeyProperty());
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }

    }

    @Override
    public String getMethod(SqlMethod sqlMethod) {
        // 自定义 mapper 方法名
        return "deleteByIdWithModifyFill";
    }


    public String getLoginUserSetSql(TableInfo tableInfo, boolean notFisrt) {
        if (tableInfo == null) {
            return "";
        }
        String dot = notFisrt ? "," : "";
        String partenSql = "<if test=\"%s != null\"> " + dot + " %s=#{%s},%s=%s</if>";
        //eg: loginUser.fid
        String loginUserIdKey = EggMpSqlConst.PARAMOF_LOGIN_USER + DOT + EggMpSqlConst.COLUMN_FID;
        return String.format(partenSql, EggMpSqlConst.PARAMOF_LOGIN_USER, EggMpSqlConst.COLUMN_LAST_MODIFYER_ID, loginUserIdKey,
                EggMpSqlConst.COLUMN_DELETE_TIME, EggMpSqlConst.MYSQL_DATE_FUNC_NOW);
    }
}
