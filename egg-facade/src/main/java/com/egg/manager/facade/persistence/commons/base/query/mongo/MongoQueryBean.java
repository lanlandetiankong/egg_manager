package com.egg.manager.facade.persistence.commons.base.query.mongo;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.facade.persistence.commons.base.enums.db.QueryMatchingEnum;
import com.egg.manager.facade.persistence.commons.base.query.BaseQueryBean;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryField;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.querydsl.QPageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zhoucj
 * @description MongoDb 查询的封装bean，但不可用作Dubbo的传输对象，调用 MongoQueryBean.getMongoQueryBeanFromRequest(request,mongoQueryBuffer) 可以将request的查询条件设置到 QueryPageBean 中
 * @date 2020/10/20
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class MongoQueryBean<T> extends BaseQueryBean {
    private static final long serialVersionUID = -3894691493792592241L;
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
    private AntdvPage pageBean;
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

    public MongoQueryBean(List<Criteria> criterias, AntdvPage pageBean, Sort sort) {
        if (CollectionUtils.isNotEmpty(criterias)) {
            this.criteriaList.addAll(criterias);
        }
        this.pageBean = pageBean;
        this.pageRequest = MongoQueryBean.getMgPageFromBean(pageBean);
        this.sort = sort;
    }

    public MongoQueryBean(List<Criteria> criterias, AntdvPage pageBean, Sort sort, List<T> data) {
        if (CollectionUtils.isNotEmpty(criterias)) {
            this.criteriaList.addAll(criterias);
        }
        this.pageBean = pageBean;
        this.pageRequest = MongoQueryBean.getMgPageFromBean(pageBean);
        this.sort = sort;
        this.data = data;
    }

    /**
     * 将QueryFormFieldBean 设置到Mongo Query
     * @param criteriaList
     * @param fieldBean
     * @return
     */
    public static void dealQueryFormFieldBeanToQuery(List<Criteria> criteriaList, QueryField fieldBean) {
        if (fieldBean == null) {
            return;
        }
        if (QueryMatchingEnum.EqualsMatch.equalsValue(fieldBean.getMatching())) {
            //字符串相等查询
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).is(fieldBean.getValue()));
        } else if (QueryMatchingEnum.LikeMatch.equalsValue(fieldBean.getMatching())) {
            //字符串模糊查询
            String value = (fieldBean.getValue() != null) ? String.valueOf(fieldBean.getValue()) : "";
            Pattern pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).regex(pattern));
        } else if (QueryMatchingEnum.NotEqualsMatch.equalsValue(fieldBean.getMatching())) {
            //字符串非等查询
            criteriaList.add(Criteria.where(fieldBean.getFieldName()).ne(fieldBean.getValue()));
        } else {
            log.warn("未被匹配的查询条件->{}", JSONObject.toJSONString(fieldBean));
        }
    }

    /**
     * 封装的分页bean ==>> mongodb 使用的分页对象
     * note: QPageRequest对象不可作为dubbo传输对象
     * @param vpage
     * @return
     */
    public static QPageRequest getMgPageFromBean(AntdvPage vpage) {
        vpage = vpage != null ? vpage : AntdvPage.gainDefault(Object.class);
        return QPageRequest.of(getSuitableCurrent(vpage.getCurrent()), vpage.getPageSize());
    }

    /**
     * 由于mongodb与mysql 对current的起始含义不一致，需要调用该方法协助取得一个正确的 current
     * @param current
     * @return
     */
    private static int getSuitableCurrent(Integer current){
        if(current == null || current <= 0){
            return 0 ;
        }
        //由于mongodb
        return current - 1 ;
    }

    /**
     * mongodb 使用的分页对象 ==>> 封装的分页bean
     * @param page
     * @return
     */
    public static <T> AntdvPage<T> getPageBeanFromPage(Page<T> page) {
        AntdvPage<T> pageBean = new AntdvPage<T>();
        pageBean.setContent(page.getContent());
        pageBean.setTotal(page.getTotalElements());
        return pageBean;
    }


    public MongoQueryBean<T> appendQueryFieldsToQuery(QueryPageBean queryFieldBuffer) {
        if (queryFieldBuffer == null || CollectionUtils.isEmpty(queryFieldBuffer.getQuery())) {
            return this;
        }
        QueryFieldArr queryFieldArr = queryFieldBuffer.getQuery();
        for (QueryField fieldBean : queryFieldArr) {
            MongoQueryBean.dealQueryFormFieldBeanToQuery(this.criteriaList, fieldBean);
        }
        return this;
    }


    public static List<Sort.Order> dealSortBeanToOrder(AntdvSortMap sortMap) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        if (sortMap == null || sortMap.isEmpty()) {
            return sortOrders;
        }
        for (String columnName : sortMap.keySet()) {
            if (Boolean.TRUE.equals(sortMap.get(columnName))) {
                sortOrders.add(Sort.Order.asc(columnName));
            } else {
                sortOrders.add(Sort.Order.desc(columnName));
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

    public AntdvPage getPageBean() {
        return pageBean;
    }

    public void setPageBean(AntdvPage pageBean) {
        this.pageBean = pageBean;
    }

    public Sort getSort() {
        return sort;
    }
}
