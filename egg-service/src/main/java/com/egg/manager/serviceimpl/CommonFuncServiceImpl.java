package com.egg.manager.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 12:55
 * \* Description:
 * \
 */
@Service
public class CommonFuncServiceImpl implements CommonFuncService {

    @Override
    public void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, List<QueryFormFieldBean> queryFieldBeanList){
        if(queryFieldBeanList != null && queryFieldBeanList.isEmpty() == false){
            for(QueryFormFieldBean queryFormFieldBean : queryFieldBeanList){
                Object fieldValue = queryFormFieldBean.getValue();
                if(fieldValue == null){
                    continue;
                }   else {
                    if("equals".equals(queryFormFieldBean.getMatching())){
                        entityWrapper.eq(queryFormFieldBean.getFieldName(),fieldValue) ;
                    }   else if("like".equals(queryFormFieldBean.getMatching())){
                        String fieldValueStr = String.valueOf(fieldValue) ;
                        entityWrapper.like(queryFormFieldBean.getFieldName(),fieldValueStr) ;
                    }
                }
            }
        }
    }


    /**
     * 取得分页 配置 -> mybatis-plus
     * @param paginationBean
     * @return
     */
    @Override
    public  RowBounds parsePaginationToRowBounds(AntdvPaginationBean paginationBean) {
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
