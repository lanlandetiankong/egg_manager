package com.egg.manager.api.config.db.mybatis.plus.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.egg.manager.persistence.constant.pojo.mysql.EggMpSqlConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @description: 根据ids更新状态为 逻辑删除，并存储相应的更新信息
 * @author zhoucj
 * @date 2020/10/27
 */
@Slf4j
public class LogicBatchDeleteWithModifyFillMethod extends AbstractMethod {

    /**
     * mapper 对应的方法名
     */
    private static final String MAPPER_METHOD = "batchDeleteByIdsWithModifyFill";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BATCH_BY_IDS;
        if (tableInfo.isWithLogicDelete()) {
            String sqlSet = "SET " + tableInfo.getLogicDeleteSql(false, false) +getLoginUserSetSql(tableInfo,true) ;
            //逻辑值 && 版本号
            String additional = tableInfo.getLogicDeleteSql(true, true)+optlockVersion(tableInfo);
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
                    tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
                    additional);
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return this.addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }
    }



    public String getLoginUserSetSql(TableInfo tableInfo,boolean notFisrt) {
        if(tableInfo == null){
            return "" ;
        }
        String dot = notFisrt ? "," : "";
        String partenSql = "<if test=\"%s != null\"> "+dot+" %s=#{%s},%s=%s</if>";
        //eg: loginUser.fid
        String loginUserIdKey = EggMpSqlConst.LOGIN_USER+DOT+EggMpSqlConst.COLUMN_FID;
        return String.format(partenSql, EggMpSqlConst.LOGIN_USER,EggMpSqlConst.COLUMN_LAST_MODIFYER_ID,loginUserIdKey,
                        EggMpSqlConst.COLUMN_UPDATE_TIME,EggMpSqlConst.MYSQL_DATE_FUNC_NOW) ;
    }

}