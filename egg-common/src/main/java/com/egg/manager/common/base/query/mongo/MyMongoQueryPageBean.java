package com.egg.manager.common.base.query.mongo;

import com.egg.manager.common.base.query.MyBaseQueryBean;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MyMongoQueryPageBean<T> extends MyBaseQueryBean{
    private Integer current ;
    private Integer pageSize ;
    private long total ;
    private List<T> content;

    public MyMongoQueryPageBean() {
    }
    public MyMongoQueryPageBean(Integer current, Integer pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }
    public MyMongoQueryPageBean(Integer current, Integer pageSize, int total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static MyMongoQueryPageBean gainLimitPaginationBean(Integer pageSize){
        return new MyMongoQueryPageBean(1,pageSize,0);
    }

    /**
     * 默认分页
     * @return
     */
    public static MyMongoQueryPageBean gainDefaultPaginationBean(){
        return new MyMongoQueryPageBean(1,10,0);
    }






}
