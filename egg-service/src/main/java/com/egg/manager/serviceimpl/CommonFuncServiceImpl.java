package com.egg.manager.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.service.CommonFuncService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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
    public void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, Map<String,Object> queryMap){
        if(queryMap != null && queryMap.isEmpty() == false){
            Iterator<String> queryKeyIter = queryMap.keySet().iterator();
            while (queryKeyIter.hasNext()){
                String queryKey = queryKeyIter.next() ;
                entityWrapper.eq(queryKey,queryMap.get(queryKey)) ;
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
