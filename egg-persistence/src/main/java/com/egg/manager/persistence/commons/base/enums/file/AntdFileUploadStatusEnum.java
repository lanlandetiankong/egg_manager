package com.egg.manager.persistence.commons.base.enums.file;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public enum AntdFileUploadStatusEnum {

    Uploading("uploading ", "上传中..."),
    Done("done", "已上传"),
    Error("error", "上传失败"),
    Remove("removed", "已移除");


    AntdFileUploadStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 值
     */
    private String value;
    /**
     * 展示内容
     */
    private String label;


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
