package com.egg.manager.persistence.commons.base.query.mongo;

import com.egg.manager.persistence.commons.base.query.BaseQueryBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import lombok.EqualsAndHashCode;

/**
 * @author zhoucj
 * @description数据库查询字段，用作dubbo各个service之间的传输对象
 * @date 2020/10/20
 */
@EqualsAndHashCode(callSuper = true)
public class MongoQueryPage extends BaseQueryBean {

    /**
     * (除HttpServletRequest取得外)添加的查询字段
     */
    private QueryFieldArr query ;
    /**
     * 添加到 HttpServletRequest取得的排序字段前
     */
    private AntdvSortMap sortMap ;
    /**
     * 分页bean
     */
    private MongoQryPage pageConf;

    public MongoQueryPage() {
        this.initPageConf();
        this.initSortMap();
        this.initQuery();
    }
    /**
     * 全参构造-初始化
     */
    public MongoQueryPage(MongoQryPage pageConf,QueryFieldArr query,AntdvSortMap sortMap) {
        this.pageConf = pageConf;
        this.sortMap = sortMap;
        this.query = query;
    }

    /**
     * 分页配置-初始化
     * @return
     */
    private MongoQueryPage initPageConf(){
        this.pageConf =  MongoQryPage.gainDefault();
        return this;
    }
    /**
     * 排序配置-初始化
     * @return
     */
    private MongoQueryPage initSortMap(){
        this.sortMap =  new AntdvSortMap();
        return this;
    }
    /**
     * 查询配置-初始化
     * @return
     */
    private MongoQueryPage initQuery(){
        this.query = new QueryFieldArr();
        return this;
    }

    /**
     * null转初始化
     */
    public MongoQueryPage nullToInit(){
        this.pageConf = (this.pageConf != null) ? pageConf : this.initPageConf().pageConf ;
        this.sortMap = (this.sortMap != null) ? sortMap : this.initSortMap().sortMap ;
        this.query = (this.query != null) ? query : this.initQuery().query ;
        return this ;
    }



    /**
     * 操作 排序Map(没有Setter，要操作必须通过operate进行操作)
     * @return
     */
    public QueryFieldArr operateQuery(){
        return this.query;
    }

    /**
     * 操作 排序Map(没有Setter，要操作必须通过operate进行操作)
     * @return
     */
    public AntdvSortMap operateSortMap(){
        return this.sortMap ;
    }
    /**
     * 操作 分页配置(没有Setter，要操作必须通过operate进行操作)
     * @return
     */
    public MongoQryPage operatePageConf(){
        return this.pageConf;
    }



    /**
     * getter-pageConf
     * @return
     */
    public MongoQryPage getPageConf(){
        return this.pageConf != null ? this.pageConf : this.initPageConf().pageConf ;
    }
    /**
     * getter-sortMap
     * @return
     */
    public AntdvSortMap getSortMap(){
        return this.sortMap != null ? this.sortMap : this.initPageConf().sortMap ;
    }
    /**
     * getter-query
     * @return
     */
    public QueryFieldArr getQuery(){
        return this.query != null ? this.query : this.initQuery().query ;
    }

    /**
     * getter-query
     * @return
     */
    public MongoQueryPage setPageConf(MongoQryPage pageConf){
        this.pageConf = (this.pageConf != null) ? pageConf : this.initPageConf().pageConf ;
        return this ;
    }

}
