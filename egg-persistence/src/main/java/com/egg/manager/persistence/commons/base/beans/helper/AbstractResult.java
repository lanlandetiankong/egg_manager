package com.egg.manager.persistence.commons.base.beans.helper;

import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: AbstractResult
 * @Author: zhoucj
 * @Date: 2020/11/12 16:12
 */
public abstract class AbstractResult extends HashMap implements BaseResultConstant  {
    public <T> T getTheVal(String key,Class<T> clazz){
        return getTheVal(key,clazz,null);
    }

    public <T> T getTheVal(String key,Class<T> clazz,T ifNullObj){
        if(StringUtils.isBlank(key)){
            return ifNullObj ;
        }
        Object o = this.get(key);
        if(o == null){
            return ifNullObj ;
        }
        return (T) o ;
    }
    /**
     * 添加 更多参数
     * @param key
     * @param value
     */
    public void addMoreAttribute(String key, Object value) {
        Object obj = this.get(MORE_ATTRIBUTE) ;
        if(obj == null || (obj instanceof Map == false)){
            Map<String,Object> map = Maps.newHashMap();
            map.put(key,value);
            this.put(MORE_ATTRIBUTE,map);
        }   else {
            Map<String,Object> map = (Map) obj ;
            map.put(key,value) ;
            this.put(MORE_ATTRIBUTE,map);
        }
    }

    /**
     * setter
     * @param val
     */

    public void putBean(Object val){
        this.put(BEAN,val);
    }


    public void putHasError(Boolean val){
        this.put(HAS_ERROR,val);
    }


    public void putHasWarning(Boolean val){
        this.put(HAS_WARNING,val);
    }


    public void putCount(Long val){
        this.put(COUNT,val);
    }
    public void putCount(Integer val){
        if(val == null){
            this.put(COUNT,null);
        }   else {
            this.put(COUNT,Long.valueOf(val));
        }
    }


    public void putCode(Integer val){
        this.put(CODE,val);
    }

    public void putErrorActionType(String val){
        this.put(ERROR_ACTION_TYPE,val);
    }
    public void putMsg(String val){
        this.put(MSG,val);
    }
    public void putResultList(List val){
        this.put(RESULT_LIST,val);
    }
    public void putAuthorization(String val){
        this.put(AUTHORIZATION,val);
    }
    public void putResultMap(Map val){
        this.put(RESULT_MAP,val);
    }
    public void putEnumList(List val){
        this.put(ENUM_LIST,val);
    }
    public void putEnumDefaultCheckList(List val){
        this.put(ENUM_DEFAULT_CHECK_LIST,val);
    }


    /**
     * 设置分页信息
     * @param paginationBean
     * @param total
     * @param
     */
    public  void settingPage(AntdvPaginationBean paginationBean, Long total) {
        if (paginationBean != null) {
            paginationBean.setTotal(total);
        }
        this.put(PAGINATION_BEAN,paginationBean);
    }
    /**
     * getter
     * @return
     */
    public boolean isHasError(){
        return (Boolean)this.get(HAS_ERROR);
    }
    public String getMsg(){
        return (String)this.get(MSG);
    }
    public List getResultList(){
        return (List)this.get(RESULT_LIST);
    }





}