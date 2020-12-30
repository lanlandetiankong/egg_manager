package com.egg.manager.persistence.commons.base.query.pagination.antdv;

import com.egg.manager.persistence.commons.base.query.pagination.ISortAble;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public class AntdvSortMap extends LinkedHashMap<String, Boolean> implements ISortAble<AntdvSortMap>, Serializable {


    private static final long serialVersionUID = -8079228629308215650L;

    public AntdvSortMap() {
    }

    public boolean getOrderIsAsc() {
        //return Boolean.TRUE.equals(this.ascFlag);
        //todo
        return false;
    }

    @Override
    public AntdvSortMap putDesc(String key) {
        this.put(key, false);
        return this;
    }

    @Override
    public AntdvSortMap putAsc(String key) {
        this.put(key, true);
        return this;
    }

    @Override
    public Boolean checkIsDesc(String key) {
        return Boolean.FALSE.equals(this.get(key));
    }

    @Override
    public Boolean checkIsAsc(String key) {
        return Boolean.TRUE.equals(this.get(key));
    }

    @Override
    public boolean getVal(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        boolean exist = this.containsKey(key);
        if (!exist) {
            return false;
        }
        Boolean val = this.get(key);
        if (val == null) {
            //如果value为null，将强制设置为 正序
            this.put(key, true);
        }
        return this.get(key);
    }


}
