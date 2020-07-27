package com.egg.manager.common.base.pagination.mongo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/27
 * \* Time: 23:26
 * \* Description:
 * \
 */
public class AntdvMongoPage {

    private final static int DEFAULT_PAGE = 0 ;
    private final static int DEFAULT_SIZE = 10 ;
    private final static String PARAMETER_paginationObj = "paginationObj" ;

    public static PageRequest getPageFromRequest(HttpServletRequest request){
        String paginationJson = request.getParameter(PARAMETER_paginationObj) ;
        AntdvPaginationBean paginationBean = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            paginationBean = JSONObject.parseObject(paginationJson, AntdvPaginationBean.class);
        } else {
            paginationBean = AntdvPaginationBean.gainDefaultPaginationBean();
        }
        dealInitPageBean(paginationBean);
        return new PageRequest(paginationBean.getCurrent(),paginationBean.getPageSize());
    }


    private static AntdvPaginationBean dealInitPageBean(AntdvPaginationBean paginationBean){
        if(paginationBean == null){
            return new AntdvPaginationBean(DEFAULT_PAGE,DEFAULT_SIZE);
        }
        if(paginationBean.getCurrent() == null || paginationBean.getCurrent() < 0){
            paginationBean.setCurrent(DEFAULT_PAGE);
        }
        if(paginationBean.getPageSize() == null || paginationBean.getPageSize() <= 0){
            paginationBean.setPageSize(DEFAULT_SIZE);
        }
        return paginationBean ;
    }


}
