package com.egg.manager.persistence.commons.base.query.pagination.antdv;

import com.egg.manager.persistence.commons.base.query.pagination.BasePagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AntdvPage<T> extends BasePagination {
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
    /**
     * 内容
     */
    private List<T> content;

    public AntdvPage() {
    }

    public AntdvPage(Integer current, Integer pageSize) {
        this(current, pageSize, 0L,null);
    }

    public AntdvPage(Integer current, Integer pageSize, Long total,List<T> list) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static AntdvPage gainPageWithSize(Integer pageSize) {
        return new AntdvPage(1, pageSize, 0L,null);
    }

    /**
     * 默认分页
     * @return
     */
    public static <T> AntdvPage<T> gainDefault(Class<T> clazz) {
        return new AntdvPage<T>(1, 10, 0L,null);
    }
}
