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
@EqualsAndHashCode(callSuper=true)
public class MongoQueryPageBean<T> extends BaseQueryBean {
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

    public MongoQueryPageBean() {
    }

    public MongoQueryPageBean(Integer current, Integer pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

    public MongoQueryPageBean(Integer current, Integer pageSize, int total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static MongoQueryPageBean gainLimitPaginationBean(Integer pageSize) {
        return new MongoQueryPageBean(1, pageSize, 0);
    }

    /**
     * 默认分页
     * @return
     */
    public static MongoQueryPageBean gainDefaultPaginationBean() {
        return new MongoQueryPageBean(1, 10, 0);
    }


}
