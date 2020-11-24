package com.egg.manager.persistence.commons.base.query.mongo;

import com.egg.manager.persistence.commons.base.query.BaseQueryBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MongoQryPage<T> extends BaseQueryBean {
    /**
     * 当前页
     */
    private Integer current;
    /**
     * 单页数量
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 内容
     */
    private List<T> content;

    public MongoQryPage() {
    }

    public MongoQryPage(Integer current, Integer pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

    public MongoQryPage(Integer current, Integer pageSize, int total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static MongoQryPage gainLimitPaginationBean(Integer pageSize) {
        return new MongoQryPage(1, pageSize, 0);
    }

    /**
     * 默认分页
     * @return
     */
    public static MongoQryPage gainDefault() {
        return new MongoQryPage(1, 10, 0);
    }


}
