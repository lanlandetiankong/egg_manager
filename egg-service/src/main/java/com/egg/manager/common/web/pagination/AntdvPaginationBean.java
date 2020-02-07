package com.egg.manager.common.web.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.RowBounds;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 14:51
 * \* Description:
 * \p
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AntdvPaginationBean {
    private Integer current ;
    private Integer pageSize ;
    private int total ;



}
