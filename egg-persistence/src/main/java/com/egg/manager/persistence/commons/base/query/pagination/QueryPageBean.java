package com.egg.manager.persistence.commons.base.query.pagination;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import lombok.Builder;

import java.io.Serializable;

/**
 * @description: 
 * @author zhoucj
 * @date 2020/11/23
 */
@Builder
public class QueryPageBean<T> implements Serializable {
    /**
     * 分页 配置
     */
    private AntdvPage pageConf ;

    /**
     * 查询条件 配置
     */
    private QueryFieldArr query ;
    /**
     * 排序 配置
     */
    private AntdvSortMap sortMap ;

    /**
     * 无参构造-初始化
     */
    public QueryPageBean() {
        this.initPageConf();
        this.initSortMap();
        this.initQuery();
    }
    /**
     * 全参构造-初始化
     */
    public QueryPageBean(AntdvPage pageConf,QueryFieldArr query,AntdvSortMap sortMap) {
        this.pageConf = pageConf;
        this.sortMap = sortMap;
        this.query = query;
    }

    /**
     * 分页配置-初始化
     * @return
     */
    private QueryPageBean initPageConf(){
        this.pageConf =  AntdvPage.gainDefault(Object.class);
        return this;
    }
    /**
     * 排序配置-初始化
     * @return
     */
    private QueryPageBean initSortMap(){
        this.sortMap =  new AntdvSortMap();
        return this;
    }
    /**
     * 查询配置-初始化
     * @return
     */
    private QueryPageBean initQuery(){
        this.query = new QueryFieldArr();
        return this;
    }

    /**
     * null转初始化
     */
    public QueryPageBean nullToInit(){
        this.pageConf = (this.pageConf != null) ? pageConf : this.initPageConf().pageConf ;
        this.sortMap = (this.sortMap != null) ? sortMap : this.initSortMap().sortMap ;
        this.query = (this.query != null) ? query : this.initQuery().query ;
        return this ;
    }

    /**
     * 操作 分页配置(没有Setter，要操作必须通过operate进行操作)
     * @return
     */
    public AntdvPage operatePageConf(){
        return this.pageConf ;
    }

    /**
     * 操作 排序Map(没有Setter，要操作必须通过operate进行操作)
     * @return
     */
    public AntdvSortMap operateSortMap(){
        return this.sortMap ;
    }

    /**
     * 操作 查询过滤(没有Setter，要操作必须通过operate进行操作)
     * @return
     */
    public QueryFieldArr operateQuery(){
        return this.query ;
    }

    /**
     * getter-pageConf
     * @return
     */
    public AntdvPage getPageConf(){
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
     * getter-pageConf
     * @return
     */
    public QueryPageBean setPageConf(AntdvPage pageConf){
        this.pageConf = pageConf != null ? this.pageConf : this.initPageConf().pageConf ;
        return this;
    }

    /**
     * 扩展 - 转化为mybatisplus的Page
     */
    public Page toMpPage() {
        Page pagination = new Page();
        AntdvPage pageConf = this.getPageConf();
        if (pageConf != null) {
            pagination.setCurrent(pageConf.getCurrent());
            pagination.setSize(pageConf.getPageSize());
        }
        return pagination;
    }


}