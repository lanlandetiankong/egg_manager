package com.egg.manager.common.base.query.mongo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.enums.query.mongo.MyMongoQueryMatchingEnum;
import com.egg.manager.common.base.query.MyBaseQueryBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyStringUtil;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.querydsl.QPageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * \* note: MongoDb 查询的封装bean，但不可用作Dubbo的传输对象
 *   调用 MongoQueryBean.getMongoQueryBeanFromRequest(request,mongoQueryBuffer) 可以将request的查询条件设置到 MyMongoQueryBuffer 中
 * \* User: zhouchengjie
 * \* Date: 2020/8/1
 * \* Time: 16:37
 * \* Description:
 * \
 */
@Slf4j
public class MongoQueryBean<T> extends MyBaseQueryBean {
    /**
     * 条件集合
     */
    private List<Criteria> criteriaList = new ArrayList<>() ;
    /**
     * 分页请求
     */
    private QPageRequest pageRequest ;
    /**
     * 分页查询bean
     */
    private MyMongoQueryPageBean  pageBean ;
    /**
     * 排序Sort
     */
    private Sort sort ;
    /**
     * 数据
     */
    private List<T> data ;


    public MongoQueryBean() {
    }
    public MongoQueryBean(List<Criteria> criterias, MyMongoQueryPageBean  pageBean, Sort sort) {
        if(CollectionUtils.isNotEmpty(criterias)){
            this.criteriaList.addAll(criterias) ;
        }
        this.pageBean = pageBean;
        this.pageRequest = MongoQueryBean.getMPageFromBean(pageBean);
        this.sort = sort;
    }

    public MongoQueryBean(List<Criteria> criterias, MyMongoQueryPageBean  pageBean, Sort sort, List<T> data) {
        if(CollectionUtils.isNotEmpty(criterias)){
            this.criteriaList.addAll(criterias) ;
        }
        this.pageBean = pageBean;
        this.pageRequest = MongoQueryBean.getMPageFromBean(pageBean);
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




    public static MyMongoQueryBuffer getMongoQueryBeanFromRequest(HttpServletRequest request,MyMongoQueryBuffer queryBuffer){
        //查询字段
        List<Criteria> criterias = new ArrayList<>();
        criterias.addAll(getMQueryFilterFromRequest(request,queryBuffer));
        //分页
        MyMongoQueryPageBean pageBean = getPageBeanFromRequest(request);
        List<MyMongoSortBean> sortBeans= getSortBeansFromRequest(request);
        List<MyMongoSortBean> frontSortList = queryBuffer.getFrontSortList();
        if(CollectionUtils.isNotEmpty(frontSortList)){
            sortBeans.addAll(0,frontSortList);
        }
        List<MyMongoSortBean> behindSortList = queryBuffer.getBehindSortList();
        if(CollectionUtils.isNotEmpty(behindSortList)){
            sortBeans.addAll(behindSortList);
        }
        queryBuffer.setPageBean(pageBean);
        queryBuffer.addFrontSortItem(sortBeans);
        return queryBuffer ;
    }


    /**
     * 从HttpServletRequest取得Query(携带过滤可查询字段)
     * @param request
     * @param queryBuffer 查询配置
     * @return
     */
    public static List<Criteria> getMQueryFilterFromRequest(HttpServletRequest request,MyMongoQueryBuffer queryBuffer){
        boolean isWhiteList =  Boolean.FALSE.equals(queryBuffer.getWhiteSetsFlag());
        Set<String> enableFields = (Boolean.FALSE.equals(queryBuffer.getWhiteSetsFlag())) ? enableFields = queryBuffer.getBlackQueryFieldSets() : queryBuffer.getWhiteQueryFieldSets();
        List<Criteria> criterias = new ArrayList<>() ;
        String queryJson = request.getParameter(PARAMETER_queryObj);
        if(StringUtils.isBlank(queryJson) || "[]".equals(queryJson)){
            return criterias ;
        }   else {
            List<QueryFormFieldBean> fieldBeansTemp = JSONArray.parseArray(queryJson, QueryFormFieldBean.class);
            if (CollectionUtils.isNotEmpty(fieldBeansTemp)) {
                //如果黑名单/白名单 为空，则将所有查询字段设置到Query
                if(CollectionUtils.isEmpty(enableFields)){
                    for (QueryFormFieldBean fieldBean : fieldBeansTemp) {
                        //将查询条件设置到Query
                        dealQueryFormFieldBeanToQuery(criterias,fieldBean);
                        queryBuffer.addQueryFieldItem(fieldBean);
                    }
                }   else {
                    Set<QueryFormFieldBean> filtedFields = Sets.newHashSet();
                    for (QueryFormFieldBean fieldBean : fieldBeansTemp) {
                        if(enableFields.contains(fieldBean.getFieldName()) == isWhiteList){
                            //将查询条件设置到Query
                            dealQueryFormFieldBeanToQuery(criterias,fieldBean);
                            queryBuffer.addQueryFieldItem(fieldBean);
                        }   else {
                            filtedFields.add(fieldBean);
                        }
                    }
                    log.debug("请求Url:{}，被筛选掉的查询字段有 ==> {}",request.getRequestURL(),JSONArray.toJSONString(filtedFields));
                }
            }
        }
        return criterias ;
    }

    /**
     * 将QueryFormFieldBean 设置到Mongo Query
     * @param criteriaList
     * @param fieldBean
     * @return
     */
    public static void dealQueryFormFieldBeanToQuery(List<Criteria> criteriaList, QueryFormFieldBean fieldBean) {
        if(MyMongoQueryMatchingEnum.EqualsMatch.equalsValue(fieldBean.getMatching())){
            //字符串相等查询
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).is(fieldBean.getValue()));
        }   else if(MyMongoQueryMatchingEnum.LikeMatch.equalsValue(fieldBean.getMatching())){
            //字符串模糊查询
            String value = (fieldBean.getValue() != null) ? String.valueOf(fieldBean.getValue()) : "";
            Pattern pattern= Pattern.compile("^.*"+value+".*$", Pattern.CASE_INSENSITIVE);
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).regex(pattern));
        }   else if(MyMongoQueryMatchingEnum.NotEqualsMatch.equalsValue(fieldBean.getMatching())){
            //字符串非等查询
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).ne(fieldBean.getValue()));
        }
    }

    /**
     * 从HttpServletRequest取得Mongo分页
     * @param request
     * @return
     */
    public static MyMongoQueryPageBean getPageBeanFromRequest(HttpServletRequest request){
        String paginationJson = request.getParameter(PARAMETER_paginationObj) ;
        MyMongoQueryPageBean paginationBean = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            paginationBean = JSONObject.parseObject(paginationJson, MyMongoQueryPageBean.class);
        } else {
            paginationBean = MyMongoQueryPageBean.gainDefaultPaginationBean();
        }
        dealInitPageBean(paginationBean);
        return paginationBean ;
    }

    /**
     * 封装的分页bean ==>> mongodb 使用的分页对象
     * note: QPageRequest对象不可作为dubbo传输对象
     * @param paginationBean
     * @return
     */
    public static QPageRequest getMPageFromBean(MyMongoQueryPageBean paginationBean){
        paginationBean = paginationBean != null ? paginationBean : MyMongoQueryPageBean.gainDefaultPaginationBean();
        return new QPageRequest(paginationBean.getCurrent(),paginationBean.getPageSize());
    }



    /**
     * mongodb 使用的分页对象 ==>> 封装的分页bean
     * @param page
     * @return
     */
    public static <T> MyMongoQueryPageBean<T> getPageBeanFromPage(Page<T> page){
        MyMongoQueryPageBean<T> pageBean = new MyMongoQueryPageBean<T>();
        pageBean.setContent(page.getContent());
        pageBean.setTotal(page.getTotalElements());
        return pageBean;
    }


    /**
     * MyMongoQueryPageBean 数据初始化
     * @param paginationBean
     * @return
     */
    private static MyMongoQueryPageBean dealInitPageBean(MyMongoQueryPageBean paginationBean){
        if(paginationBean == null){
            return new MyMongoQueryPageBean(DEFAULT_PAGE,DEFAULT_SIZE);
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
    public static List<MyMongoSortBean> getSortBeansFromRequest(HttpServletRequest request) {
        String sortObj = request.getParameter(PARAMETER_sortObj);
        List<MyMongoSortBean> sortBeans = new ArrayList<>();
        if (StringUtils.isNotBlank(sortObj) && "{}".equals(sortObj) == false) {
            MyMongoSortBean antdvSortBean = JSONObject.parseObject(sortObj, MyMongoSortBean.class);
            if (antdvSortBean != null) {
                String fieldName = MyStringUtil.camelToUnderline(antdvSortBean.getField(), false);
                antdvSortBean.setField(fieldName);
                sortBeans.add(antdvSortBean);
            }
        }
        return sortBeans;
    }


    public MongoQueryBean<T> appendQueryFieldsToQuery(MyMongoQueryBuffer queryFieldBuffer){
        if(queryFieldBuffer == null || CollectionUtils.isEmpty(queryFieldBuffer.getQueryFormFieldBeanList())){
            return this ;
        }
        List<QueryFormFieldBean> queryFormFieldBeanList = queryFieldBuffer.getQueryFormFieldBeanList();
        for (QueryFormFieldBean fieldBean : queryFormFieldBeanList){
            MongoQueryBean.dealQueryFormFieldBeanToQuery(this.criteriaList,fieldBean);
        }
        return this ;
    }


    public static List<Sort.Order> dealSortBeanToOrder(List<MyMongoSortBean> sortBeans){
        List<Sort.Order> sortOrders = new ArrayList<>() ;
        if(CollectionUtils.isNotEmpty(sortBeans)){
            for(MyMongoSortBean sortBean : sortBeans){
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


    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public QPageRequest getPageRequest() {
        return pageRequest;
    }

    public MyMongoQueryPageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(MyMongoQueryPageBean pageBean) {
        this.pageBean = pageBean;
    }

    public Sort getSort() {
        return sort;
    }
}
