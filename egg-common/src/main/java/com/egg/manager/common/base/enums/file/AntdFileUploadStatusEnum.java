package com.egg.manager.common.base.enums.file;

import lombok.Data;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/4
 * \* Time: 14:45
 * \* Description:
 * \
 */
public enum AntdFileUploadStatusEnum {

    Uploading("uploading ","上传中..."),
    Done("done","已上传"),
    Error("error","上传失败"),
    Remove("removed","已移除")
    ;


    AntdFileUploadStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private String value ;
    private String label ;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
