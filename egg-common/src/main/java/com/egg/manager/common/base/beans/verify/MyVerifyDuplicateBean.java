package com.egg.manager.common.base.beans.verify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 验证 重复
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyVerifyDuplicateBean<T> implements Serializable {
    /**
     * 是否成功
     */
    private boolean successFlag = true;
    /**
     * 错误信息
     */
    private String errorMsg ;
    /**
     * 字段名-集合
     */
    private List<String> fidldNameList = new ArrayList<>();
    /**
     * 数据库字段名-集合
     */
    private List<String> columnList = new ArrayList<>();

    public MyVerifyDuplicateBean dealAddFieldName(String str){
        fidldNameList.add(str) ;
        return this ;
    }
    public MyVerifyDuplicateBean dealAddColumn(String str){
        columnList.add(str) ;
        return this ;
    }

}
