package com.egg.manager.persistence.commons.base.pagination.antdv;

import com.egg.manager.persistence.commons.base.pagination.MyBasePagination;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Data
@Builder
public class AntdvPaginationBean<T> extends MyBasePagination {
    /**
     * 当前页数
     */
    private Integer current;
    /**
     * 单页数据量
     */
    private Integer pageSize;
    /**
     * 总数据量
     */
    private Long total;

    public AntdvPaginationBean() {
    }

    public AntdvPaginationBean(Integer current, Integer pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

    public AntdvPaginationBean(Integer current, Integer pageSize, Long total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static AntdvPaginationBean gainLimitPaginationBean(Integer pageSize) {
        return new AntdvPaginationBean(1, pageSize, 0L);
    }

    /**
     * 默认分页
     * @return
     */
    public static <T> AntdvPaginationBean<T> gainDefaultPaginationBean(Class<T> clazz) {
        return new AntdvPaginationBean<T>(1, 10, 0L);
    }
}
