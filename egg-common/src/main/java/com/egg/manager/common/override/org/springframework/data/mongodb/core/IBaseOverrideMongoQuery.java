package com.egg.manager.common.override.org.springframework.data.mongodb.core;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

public interface IBaseOverrideMongoQuery<T,S> extends Serializable {
    /**
     * 取得上级bean
     * @return
     */
    S getSuperBean();
    //T getSelfBean(S s);
}
