package com.egg.manager.api.trait.routine;

import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

/**
 * 如果有不可用于dubbo的，迁移到此处
 */
@Component
public class RoutineCommonFunc {
    /**
     * 取得分页 配置 -> mybatis-plus
     * @param paginationBean
     * @return
     */
    public RowBounds parsePaginationToRowBounds(AntdvPaginationBean paginationBean) {
        if(paginationBean != null){
            Integer current = paginationBean.getCurrent();
            Integer pageSize = paginationBean.getPageSize();
            current = current != null ? current : 1;
            pageSize = pageSize != null ? pageSize : 0;
            int offset = (current - 1) * pageSize ;
            return new RowBounds(offset,pageSize) ;
        }   else {
            return new RowBounds() ;
        }
    }
}
