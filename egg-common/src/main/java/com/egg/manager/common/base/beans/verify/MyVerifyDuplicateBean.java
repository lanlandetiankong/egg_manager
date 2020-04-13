package com.egg.manager.common.base.beans.verify;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/11
 * \* Time: 19:53
 * \* Description:
 * \
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyVerifyDuplicateBean<T> {
    private boolean successFlag = true;
    private String errorMsg ;
    private List<String> fidldNameList = new ArrayList<>();
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
