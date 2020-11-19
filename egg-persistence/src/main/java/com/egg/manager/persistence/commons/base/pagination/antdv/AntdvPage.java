package com.egg.manager.persistence.commons.base.pagination.antdv;

import com.egg.manager.persistence.commons.base.pagination.IBasePagination;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Data
@Builder
public class AntdvPage<T> extends IBasePagination {
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

    public AntdvPage() {
    }

    public AntdvPage(Integer current, Integer pageSize) {
        this(current,pageSize,0L);
    }

    public AntdvPage(Integer current, Integer pageSize, Long total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static AntdvPage gainLimitPaginationBean(Integer pageSize) {
        return new AntdvPage(1, pageSize, 0L);
    }

    /**
     * 默认分页
     * @return
     */
    public static <T> AntdvPage<T> gainDefaultPaginationBean(Class<T> clazz) {
        return new AntdvPage<T>(1, 10, 0L);
    }
}
