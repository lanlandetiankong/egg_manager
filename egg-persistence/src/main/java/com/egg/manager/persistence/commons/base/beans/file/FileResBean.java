package com.egg.manager.persistence.commons.base.beans.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResBean implements Serializable {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件位置
     */
    private String fileLocation;
    /**
     * 文件名前缀
     */
    private String filePrefix;
    /**
     * 图片uri
     */
    private String fileUri;
    /**
     * 原文件名
     */
    private String fileOldName;


}
