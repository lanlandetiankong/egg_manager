package com.egg.manager.persistence.commons.base.query.pagination.antdv;

import com.egg.manager.persistence.commons.base.query.BaseQueryBean;
import lombok.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueryField extends BaseQueryBean {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 匹配方式
     */
    private String matching;
    /**
     * 外键名
     */
    private String foreignName;
    /**
     * 数据库匹配方式
     */
    private String sqlMatching;
    /**
     * 匹配的值
     */
    private Object value;

}
