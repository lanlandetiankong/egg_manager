package com.egg.manager.persistence.commons.base.beans.verify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description 验证 重复
 * @date 2020/10/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyVerifyDuplicateBean<T> implements Serializable {
    /**
     * 是否成功
     */
    private boolean successFlag = true;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 字段名-集合
     */
    private List<String> fidldNameList = new ArrayList<>();
    /**
     * 数据库字段名-集合
     */
    private List<String> columnList = new ArrayList<>();

    public MyVerifyDuplicateBean dealAddFieldName(String str) {
        fidldNameList.add(str);
        return this;
    }

    public MyVerifyDuplicateBean dealAddColumn(String str) {
        columnList.add(str);
        return this;
    }

}
