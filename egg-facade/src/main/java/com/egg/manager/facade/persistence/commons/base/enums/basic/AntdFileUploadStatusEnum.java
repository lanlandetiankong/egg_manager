package com.egg.manager.facade.persistence.commons.base.enums.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum AntdFileUploadStatusEnum {

    Uploading("uploading ", "上传中..."),
    Done("done", "已上传"),
    Error("error", "上传失败"),
    Remove("removed", "已移除");


    /**
     * 值
     */
    private String value;
    /**
     * 展示内容
     */
    private String label;

}
