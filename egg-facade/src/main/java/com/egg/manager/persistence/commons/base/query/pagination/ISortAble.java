package com.egg.manager.persistence.commons.base.query.pagination;

/**
 * @author zhoucj
 * @description:
 * @date 2020/11/18
 */
public interface ISortAble<T> {

    String KEY_ORDER = "order";


    /**
     * 返回 倒序
     * @param key
     * @return
     */
    T putDesc(String key);

    /**
     * 返回 正序
     * @param key
     * @return
     */
    T putAsc(String key);

    /**
     * 判断 key 是否正序
     * @param key
     * @return
     */
    Boolean checkIsDesc(String key);

    /**
     * 判断 key 是否正序
     * @param key
     * @return
     */
    Boolean checkIsAsc(String key);

    /**
     * 取得值
     * @param key
     * @return
     */
    boolean getVal(String key);
}
