package com.egg.manager.persistence.commons.base.query.mongo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.commons.base.constant.Constant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoQueryMatchingEnum;
import com.egg.manager.persistence.commons.base.query.BaseQueryBean;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.commons.base.query.form.QueryFieldArr;
import com.egg.manager.persistence.commons.util.str.MyStringUtil;
import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
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
 * @author zhoucj
 * @description MongoDb 查询的封装bean，但不可用作Dubbo的传输对象，调用 MongoQueryBean.getMongoQueryBeanFromRequest(request,mongoQueryBuffer) 可以将request的查询条件设置到 MongoQueryBuffer 中
 * @date 2020/10/20
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class MongoQueryBean<T> extends BaseQueryBean {
    /**
     * 条件集合
     */
    private List<Criteria> criteriaList = new ArrayList<>();
    /**
     * 分页请求
     */
    private QPageRequest pageRequest;
    /**
     * 分页查询bean
     */
    private MongoQueryPageBean pageBean;
    /**
     * 排序Sort
     */
    private Sort sort;
    /**
     * 数据
     */
    private List<T> data;


    public MongoQueryBean() {
    }

    public MongoQueryBean(List<Criteria> criterias, MongoQueryPageBean pageBean, Sort sort) {
        if (CollectionUtils.isNotEmpty(criterias)) {
            this.criteriaList.addAll(criterias);
        }
        this.pageBean = pageBean;
        this.pageRequest = MongoQueryBean.getMgPageFromBean(pageBean);
        this.sort = sort;
    }

    public MongoQueryBean(List<Criteria> criterias, MongoQueryPageBean pageBean, Sort sort, List<T> data) {
        if (CollectionUtils.isNotEmpty(criterias)) {
            this.criteriaList.addAll(criterias);
        }
        this.pageBean = pageBean;
        this.pageRequest = MongoQueryBean.getMgPageFromBean(pageBean);
        this.sort = sort;
        this.data = data;
    }


    public final static int DEFAULT_PAGE = 0;
    public final static int DEFAULT_SIZE = 10;
    public final static String PARAMETER_PAGINATION_OBJ = WebApiConstant.FIELDNAME_PAGINATION_OBJ;
    public final static String PARAMETER_QUERY_OBJ = WebApiConstant.FIELDNAME_QUERY_OBJ;
    public final static String PARAMETER_SORT_OBJ = WebApiConstant.FIELDNAME_SORT_OBJ;
    public static final String ORDER_ASC = "ascend";
    public static final String ORDER_DESC = "descend";


    public static final String MOFIELD_CREATE_TIME = "createTime";
    public static final String MOFIELD_ORDER_NUM = "orderNum";


    public static MongoQueryBuffer getMongoQueryBeanFromRequest(HttpServletRequest request, MongoQueryBuffer queryBuffer) {
        //查询字段
        List<Criteria> criterias = new ArrayList<>();
        criterias.addAll(getMgQueryFilterFromRequest(request, queryBuffer));
        //分页
        MongoQueryPageBean pageBean = getPageBeanFromRequest(request);
        List<MongoSortBean> sortMap = getSortBeansFromRequest(request);
        List<MongoSortBean> frontSortList = queryBuffer.getFrontSortList();
        if (CollectionUtils.isNotEmpty(frontSortList)) {
            sortMap.addAll(0, frontSortList);
        }
        List<MongoSortBean> behindSortList = queryBuffer.getBehindSortList();
        if (CollectionUtils.isNotEmpty(behindSortList)) {
            sortMap.addAll(behindSortList);
        }
        queryBuffer.setPageBean(pageBean);
        queryBuffer.addFrontSortItem(sortMap);
        return queryBuffer;
    }


    /**
     * 从HttpServletRequest取得Query(携带过滤可查询字段)
     * @param request
     * @param queryBuffer 查询配置
     * @return
     */
    public static List<Criteria> getMgQueryFilterFromRequest(HttpServletRequest request, MongoQueryBuffer queryBuffer) {
        boolean isWhiteList = Boolean.FALSE.equals(queryBuffer.getWhiteSetsFlag());
        Set<String> enableFields = (Boolean.FALSE.equals(queryBuffer.getWhiteSetsFlag())) ? enableFields = queryBuffer.getBlackQueryFieldSets() : queryBuffer.getWhiteQueryFieldSets();
        List<Criteria> criterias = new ArrayList<>();
        String queryJson = request.getParameter(PARAMETER_QUERY_OBJ);
        if (StringUtils.isBlank(queryJson) || Constant.JSON_EMPTY_ARRAY.equals(queryJson)) {
            return criterias;
        } else {
            QueryFieldArr fieldBeansTemp = QueryFieldArr.parseFromJson(queryJson);
            if (CollectionUtils.isNotEmpty(fieldBeansTemp)) {
                //如果黑名单/白名单 为空，则将所有查询字段设置到Query
                if (CollectionUtils.isEmpty(enableFields)) {
                    for (QueryField fieldBean : fieldBeansTemp) {
                        //将查询条件设置到Query
                        dealQueryFormFieldBeanToQuery(criterias, fieldBean);
                        queryBuffer.addQueryFieldItem(fieldBean);
                    }
                } else {
                    Set<QueryField> filtedFields = Sets.newHashSet();
                    for (QueryField fieldBean : fieldBeansTemp) {
                        if (enableFields.contains(fieldBean.getFieldName()) == isWhiteList) {
                            //将查询条件设置到Query
                            dealQueryFormFieldBeanToQuery(criterias, fieldBean);
                            queryBuffer.addQueryFieldItem(fieldBean);
                        } else {
                            filtedFields.add(fieldBean);
                        }
                    }
                    log.debug("请求Url:{}，被筛选掉的查询字段有 ==> {}", request.getRequestURL(), JSONArray.toJSONString(filtedFields));
                }
            }
        }
        return criterias;
    }

    /**
     * 将QueryFormFieldBean 设置到Mongo Query
     * @param criteriaList
     * @param fieldBean
     * @return
     */
    public static void dealQueryFormFieldBeanToQuery(List<Criteria> criteriaList, QueryField fieldBean) {
        if (MyMongoQueryMatchingEnum.EqualsMatch.equalsValue(fieldBean.getMatching())) {
            //字符串相等查询
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).is(fieldBean.getValue()));
        } else if (MyMongoQueryMatchingEnum.LikeMatch.equalsValue(fieldBean.getMatching())) {
            //字符串模糊查询
            String value = (fieldBean.getValue() != null) ? String.valueOf(fieldBean.getValue()) : "";
            Pattern pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).regex(pattern));
        } else if (MyMongoQueryMatchingEnum.NotEqualsMatch.equalsValue(fieldBean.getMatching())) {
            //字符串非等查询
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).ne(fieldBean.getValue()));
        }
    }

    /**
     * 从HttpServletRequest取得Mongo分页
     * @param request
     * @return
     */
    public static MongoQueryPageBean getPageBeanFromRequest(HttpServletRequest request) {
        String paginationJson = request.getParameter(PARAMETER_PAGINATION_OBJ);
        MongoQueryPageBean vpage = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            vpage = JSONObject.parseObject(paginationJson, MongoQueryPageBean.class);
        } else {
            vpage = MongoQueryPageBean.gainDefaultPaginationBean();
        }
        dealInitPageBean(vpage);
        return vpage;
    }

    /**
     * 封装的分页bean ==>> mongodb 使用的分页对象
     * note: QPageRequest对象不可作为dubbo传输对象
     * @param vpage
     * @return
     */
    public static QPageRequest getMgPageFromBean(MongoQueryPageBean vpage) {
        vpage = vpage != null ? vpage : MongoQueryPageBean.gainDefaultPaginationBean();
        return QPageRequest.of(vpage.getCurrent(), vpage.getPageSize());
    }


    /**
     * mongodb 使用的分页对象 ==>> 封装的分页bean
     * @param page
     * @return
     */
    public static <T> MongoQueryPageBean<T> getPageBeanFromPage(Page<T> page) {
        MongoQueryPageBean<T> pageBean = new MongoQueryPageBean<T>();
        pageBean.setContent(page.getContent());
        pageBean.setTotal(page.getTotalElements());
        return pageBean;
    }


    /**
     * MongoQueryPageBean 数据初始化
     * @param vpage
     * @return
     */
    private static MongoQueryPageBean dealInitPageBean(MongoQueryPageBean vpage) {
        if (vpage == null) {
            return new MongoQueryPageBean(DEFAULT_PAGE, DEFAULT_SIZE);
        }
        if (vpage.getCurrent() == null || vpage.getCurrent() < 0) {
            vpage.setCurrent(DEFAULT_PAGE);
        } else if (vpage.getCurrent() > 0) {
            vpage.setCurrent(vpage.getCurrent() - 1);
        }
        if (vpage.getPageSize() == null || vpage.getPageSize() <= 0) {
            vpage.setPageSize(DEFAULT_SIZE);
        }
        return vpage;
    }

    /**
     * 取得排序
     * @param request
     * @return
     */
    public static List<MongoSortBean> getSortBeansFromRequest(HttpServletRequest request) {
        String sortObj = request.getParameter(PARAMETER_SORT_OBJ);
        List<MongoSortBean> sortMap = new ArrayList<>();
        if (StringUtils.isNotBlank(sortObj) && Constant.JSON_EMPTY_OBJECT.equals(sortObj) == false) {
            MongoSortBean antdvSortBean = JSONObject.parseObject(sortObj, MongoSortBean.class);
            if (antdvSortBean != null) {
                String fieldName = MyStringUtil.camelToUnderline(antdvSortBean.getField(), false);
                antdvSortBean.setField(fieldName);
                sortMap.add(antdvSortBean);
            }
        }
        return sortMap;
    }


    public MongoQueryBean<T> appendQueryFieldsToQuery(MongoQueryBuffer queryFieldBuffer) {
        if (queryFieldBuffer == null || CollectionUtils.isEmpty(queryFieldBuffer.getQueryFieldArr())) {
            return this;
        }
        QueryFieldArr queryFieldArr = queryFieldBuffer.getQueryFieldArr();
        for (QueryField fieldBean : queryFieldArr) {
            MongoQueryBean.dealQueryFormFieldBeanToQuery(this.criteriaList, fieldBean);
        }
        return this;
    }


    public static List<Sort.Order> dealSortBeanToOrder(List<MongoSortBean> sortMap) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sortMap)) {
            for (MongoSortBean sortBean : sortMap) {
                if (sortBean.getOrderIsAsc()) {
                    sortOrders.add(Sort.Order.asc(sortBean.getField()));
                } else {
                    sortOrders.add(Sort.Order.desc(sortBean.getField()));
                }
            }
        }
        return sortOrders;
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

    public MongoQueryPageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(MongoQueryPageBean pageBean) {
        this.pageBean = pageBean;
    }

    public Sort getSort() {
        return sort;
    }
}
