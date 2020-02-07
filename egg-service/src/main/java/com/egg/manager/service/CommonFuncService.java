package com.egg.manager.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import scala.Int;

import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 12:54
 * \* Description:
 * \
 */
public interface CommonFuncService {

    Integer defaultVersion = 0 ;

    void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, List<QueryFormFieldBean> queryFieldBeanList);

    /**
     * 取得分页 配置 -> mybatis-plus
     * @param paginationBean
     * @return
     */
    RowBounds parsePaginationToRowBounds(AntdvPaginationBean paginationBean);
}
