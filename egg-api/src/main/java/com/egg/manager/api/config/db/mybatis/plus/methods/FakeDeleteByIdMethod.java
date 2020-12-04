package com.egg.manager.api.config.db.mybatis.plus.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.egg.manager.api.config.db.mybatis.plus.EggMpSqlMethod;
import com.egg.manager.persistence.commons.base.constant.db.mysql.MyBaseMysqlEntityFieldConstant;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author zhoucj
 * @description 自定义-mybatisplus增强方法->根据id伪删除
 * @date 2020/10/26
 */
public class FakeDeleteByIdMethod extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        EggMpSqlMethod sqlMethod = EggMpSqlMethod.FAKE_DELETE_BY_ID;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), MyBaseMysqlEntityFieldConstant.IS_DELETED.getColumnName(), SwitchStateEnum.Open.getValue(), MyBaseMysqlEntityFieldConstant.FID.getColumnName());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }

}