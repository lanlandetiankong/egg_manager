package com.egg.manager.common.base.enums.query.mongo;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2020/8/8
 * \* Time: 19:06
 * \* Description:
 * \
 */
public enum MyMongoCommonSortFieldEnum {
    CreateTime_Asc("create",true),
    CreateTime_Desc("createTime",true),
    LastModifiedDate_Asc("lastModifiedDate",true),
    LastModifiedDate_Desc("lastModifiedDate",false),
    ;


    MyMongoCommonSortFieldEnum(String fieldName, boolean ascFlag) {
        this.fieldName = fieldName;
        this.ascFlag = ascFlag;
    }

    private String fieldName;
    private Boolean ascFlag;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getAscFlag() {
        return ascFlag;
    }

    public void setAscFlag(Boolean ascFlag) {
        this.ascFlag = ascFlag;
    }
}
