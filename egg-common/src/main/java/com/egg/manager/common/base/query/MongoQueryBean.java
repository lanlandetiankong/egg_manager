package com.egg.manager.common.base.query;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.enums.query.mongo.MyMongoQueryMatchingEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.util.str.MyStringUtil;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.querydsl.QPageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/8/1
 * \* Time: 16:37
 * \* Description:
 * \
 */
@Slf4j
public class MongoQueryBean<T> {

    private Query query ;
    private Pageable pageable ;
    private Sort sort ;
    private List<T> data ;


    public MongoQueryBean() {
    }
    public MongoQueryBean(Query query, Pageable pageable, Sort sort) {
        this.query = query;
        this.pageable = pageable;
        this.sort = sort;
    }

    public MongoQueryBean(Query query, Pageable pageable, Sort sort, List<T> data) {
        this.query = query;
        this.pageable = pageable;
        this.sort = sort;
        this.data = data;
    }

    //常量
    public final static int DEFAULT_PAGE = 0 ;
    public final static int DEFAULT_SIZE = 10 ;
    public final static String PARAMETER_paginationObj = "paginationObj" ;
    public final static String PARAMETER_queryObj = "queryObj" ;
    public final static String PARAMETER_sortObj = "sortObj" ;
    public static final String ORDER_ASC = "ascend";
    public static final String ORDER_DESC = "descend";


    public static final String MOFIELD_createTime = "createTime";
    public static final String MOFIELD_orderNum = "orderNum";


    public static MongoQueryBean getMongoQueryBeanFromRequest(HttpServletRequest request){
        return getMongoQueryBeanFromRequest(request,new MyMongoQueryBuffer());
    }

    public static MongoQueryBean getMongoQueryBeanFromRequest(HttpServletRequest request,MyMongoQueryBuffer queryBuffer){
        //查询字段
        Query query = null ;
        if(Boolean.FALSE.equals(queryBuffer.getWhiteSetsFlag())){
            query = getMQueryFilterFromRequest(request,false,queryBuffer.getBlackQueryFieldSets());
        }   else {
            query = getMQueryFilterFromRequest(request,true,queryBuffer.getWhiteQueryFieldSets());
        }
        //分页
        QPageRequest qPageRequest = getMPageFromRequest(request);
        List<Sort.Order> sortOrderList = getMSortsFromRequest(request);
        //前置排序
        if(CollectionUtils.isNotEmpty(queryBuffer.getFrontSortList())){
            sortOrderList.addAll(0,getMongoSortOrder(queryBuffer.getFrontSortList()));
        }
        //后置排序
        if(CollectionUtils.isNotEmpty(queryBuffer.getFrontSortList())){
            sortOrderList.addAll(getMongoSortOrder(queryBuffer.getBehindSortList()));
        }
        query = query != null ? query : new Query() ;
        if(qPageRequest != null){
            query.with(qPageRequest);
        }
        Sort sort  = new Sort(sortOrderList);
        if(CollectionUtils.isNotEmpty(sortOrderList)){
            query.with(sort);
        }
        return new MongoQueryBean(query,qPageRequest,sort) ;
    }





    /**
     * 从HttpServletRequest取得Query(只可查询白名单内的字段)
     * @param request
     * @param whiteFieldList 字段白名单列表
     * @return
     */
    public static Query getMQueryFilterEnableFromRequest(HttpServletRequest request, Set<String> whiteFieldList){
        return getMQueryFilterFromRequest(request,true,whiteFieldList);
    }
    /**
     * 从HttpServletRequest取得Query(黑名单内的字段将被忽略掉)
     * @param request
     * @param blackFieldList 字段黑名单列表
     * @return
     */
    public static Query getMQueryFilterDisableFromRequest(HttpServletRequest request,Set<String> blackFieldList){
        return getMQueryFilterFromRequest(request,false,blackFieldList);
    }
    /**
     * 从HttpServletRequest取得Query(携带过滤可查询字段)
     * @param request
     * @param enableFields 可以作为查询字段的字段列表(当enableFields为空时表示不进行过滤限制)
     * @return
     */
    public static Query getMQueryFilterFromRequest(HttpServletRequest request,boolean isWhiteList,Set<String> enableFields){
        Query query = new Query() ;
        String queryJson = request.getParameter(PARAMETER_queryObj);
        if(StringUtils.isBlank(queryJson) || "[]".equals(queryJson)){
            return query ;
        }   else {
            List<QueryFormFieldBean> fieldBeansTemp = JSONArray.parseArray(queryJson, QueryFormFieldBean.class);
            if (CollectionUtils.isNotEmpty(fieldBeansTemp)) {
                //如果黑名单/白名单 为空，则将所有查询字段设置到Query
                if(CollectionUtils.isEmpty(enableFields)){
                    for (QueryFormFieldBean fieldBean : fieldBeansTemp) {
                        //将查询条件设置到Query
                        dealQueryFormFieldBeanToQuery(query,fieldBean);
                    }
                }   else {
                    Set<QueryFormFieldBean> filtedFields = Sets.newHashSet();
                    for (QueryFormFieldBean fieldBean : fieldBeansTemp) {
                        if(enableFields.contains(fieldBean.getFieldName()) == isWhiteList){
                            //将查询条件设置到Query
                            dealQueryFormFieldBeanToQuery(query,fieldBean);
                        }   else {
                            filtedFields.add(fieldBean);
                        }
                    }
                    log.debug("请求Url:{}，被筛选掉的查询字段有 ==> {}",request.getRequestURL(),JSONArray.toJSONString(filtedFields));
                }
            }
        }
        return query ;
    }

    /**
     * 将QueryFormFieldBean 设置到Mongo Query
     * @param query
     * @param fieldBean
     * @return
     */
    public static void dealQueryFormFieldBeanToQuery(Query query,QueryFormFieldBean fieldBean) {
        query = query != null ? query : new Query() ;
        if(MyMongoQueryMatchingEnum.EqualsMatch.equalsValue(fieldBean.getMatching())){
            //字符串相等查询
            query.addCriteria(Criteria.where(fieldBean.getFieldName()).is(fieldBean.getValue()));
        }   else if(MyMongoQueryMatchingEnum.LikeMatch.equalsValue(fieldBean.getMatching())){
            //字符串模糊查询
            String value = (fieldBean.getValue() != null) ? String.valueOf(fieldBean.getValue()) : "";
            Pattern pattern= Pattern.compile("^.*"+value+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where(fieldBean.getFieldName()).regex(pattern));
        }   else if(MyMongoQueryMatchingEnum.NotEqualsMatch.equalsValue(fieldBean.getMatching())){
            //字符串非等查询
            query.addCriteria(Criteria.where(fieldBean.getFieldName()).ne(fieldBean.getValue()));
        }
    }

    /**
     * 从HttpServletRequest取得Mongo分页
     * @param request
     * @return
     */
    public static QPageRequest getMPageFromRequest(HttpServletRequest request){
        String paginationJson = request.getParameter(PARAMETER_paginationObj) ;
        AntdvPaginationBean paginationBean = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            paginationBean = JSONObject.parseObject(paginationJson, AntdvPaginationBean.class);
        } else {
            paginationBean = AntdvPaginationBean.gainDefaultPaginationBean();
        }
        dealInitPageBean(paginationBean);
        return new QPageRequest(paginationBean.getCurrent(),paginationBean.getPageSize());
    }



    /**
     * AntdvPaginationBean 数据初始化
     * @param paginationBean
     * @return
     */
    private static AntdvPaginationBean dealInitPageBean(AntdvPaginationBean paginationBean){
        if(paginationBean == null){
            return new AntdvPaginationBean(DEFAULT_PAGE,DEFAULT_SIZE);
        }
        if(paginationBean.getCurrent() == null || paginationBean.getCurrent() < 0){
            paginationBean.setCurrent(DEFAULT_PAGE);
        }   else if(paginationBean.getCurrent() > 0){
            paginationBean.setCurrent(paginationBean.getCurrent() - 1);
        }
        if(paginationBean.getPageSize() == null || paginationBean.getPageSize() <= 0){
            paginationBean.setPageSize(DEFAULT_SIZE);
        }
        return paginationBean ;
    }

    /**
     * 取得排序
     * @param request
     * @return
     */
    public static List<Sort.Order> getMSortsFromRequest(HttpServletRequest request) {
        String sortObj = request.getParameter(PARAMETER_sortObj);
        List<AntdvSortBean> sortBeans = new ArrayList<>();
        if (StringUtils.isNotBlank(sortObj) && "{}".equals(sortObj) == false) {
            AntdvSortBean antdvSortBean = JSONObject.parseObject(sortObj, AntdvSortBean.class);
            if (antdvSortBean != null) {
                String fieldName = MyStringUtil.camelToUnderline(antdvSortBean.getField(), false);
                antdvSortBean.setField(fieldName);
                sortBeans.add(antdvSortBean);
            }
        }
        List<Sort.Order> sortOrders = getMongoSortOrder(sortBeans) ;
        return CollectionUtils.isNotEmpty(sortOrders) ? sortOrders : new ArrayList<Sort.Order>();
    }


    public void appendQueryFieldsToQuery(MyMongoQueryBuffer queryFieldBuffer){
        if(queryFieldBuffer == null || CollectionUtils.isEmpty(queryFieldBuffer.getQueryFormFieldBeanList())){
            return ;
        }
        this.query = this.query != null ? this.query : new Query() ;
        List<QueryFormFieldBean> queryFormFieldBeanList = queryFieldBuffer.getQueryFormFieldBeanList();
        for (QueryFormFieldBean fieldBean : queryFormFieldBeanList){
            MongoQueryBean.dealQueryFormFieldBeanToQuery(this.query,fieldBean);
        }
    }


    private static List<Sort.Order> getMongoSortOrder(List<AntdvSortBean> sortBeans){
        List<Sort.Order> sortOrders = new ArrayList<>() ;
        if(CollectionUtils.isNotEmpty(sortBeans)){
            for(AntdvSortBean sortBean : sortBeans){
                if(sortBean.getOrderIsAsc()){
                    sortOrders.add(Sort.Order.asc(sortBean.getField()));
                }   else {
                    sortOrders.add(Sort.Order.desc(sortBean.getField()));
                }
            }
        }
        return sortOrders ;
    }









    //setter、getter
    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public Sort getSort() {
        return sort;
    }
}
