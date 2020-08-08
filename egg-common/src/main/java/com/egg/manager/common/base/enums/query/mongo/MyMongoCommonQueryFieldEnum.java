package com.egg.manager.common.base.enums.query.mongo;

import com.egg.manager.common.base.enums.base.BaseStateEnum;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/8/8
 * \* Time: 19:06
 * \* Description:
 * \
 */
public enum MyMongoCommonQueryFieldEnum {
    Status_Eq_Enable("status", MyMongoQueryMatchingEnum.EqualsMatch, "", BaseStateEnum.ENABLED.getValue()),
    Status_Eq_Disabled("status", MyMongoQueryMatchingEnum.EqualsMatch, "", BaseStateEnum.DISABLED.getValue()),
    Status_NotEq_Disabled("status", MyMongoQueryMatchingEnum.NotEqualsMatch, "", BaseStateEnum.DISABLED.getValue()),
    Status_NotEq_Delete("status", MyMongoQueryMatchingEnum.NotEqualsMatch, "", BaseStateEnum.DELETE.getValue()),
    ;

    MyMongoCommonQueryFieldEnum(String fieldName, MyMongoQueryMatchingEnum queryMatchingEnum, String foreignName, Object value) {
        this.fieldName = fieldName;
        this.foreignName = foreignName;
        this.matching = queryMatchingEnum.getValue();
        this.sqlMatching = queryMatchingEnum.getSqlMatchingValue();
        this.value = value;
    }


    private String fieldName;
    private String matching;
    private String foreignName;
    private String sqlMatching;
    private Object value;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMatching() {
        return matching;
    }

    public void setMatching(String matching) {
        this.matching = matching;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getSqlMatching() {
        return sqlMatching;
    }

    public void setSqlMatching(String sqlMatching) {
        this.sqlMatching = sqlMatching;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
