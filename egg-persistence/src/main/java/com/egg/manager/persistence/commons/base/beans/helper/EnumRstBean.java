package com.egg.manager.persistence.commons.base.beans.helper;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 枚举结果bean
 * @ClassName: EnumRstBean
 * @Author: zhoucj
 * @Date: 2020/11/25 9:53
 */
@Data
@Builder
public class EnumRstBean<T,K> implements Serializable {
    /**
     * 集合
     */
    private List<T> list;
    /**
     * 枚举数据默认勾选
     */
    private List<K> checkeds ;


}