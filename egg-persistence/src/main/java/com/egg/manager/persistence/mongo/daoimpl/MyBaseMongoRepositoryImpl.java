package com.egg.manager.persistence.mongo.daoimpl;

import com.egg.manager.common.base.exception.MyMongoException;
import com.egg.manager.persistence.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.mongo.mo.BaseModelMO;
import com.google.common.collect.Lists;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/26
 * \* Time: 16:23
 * \* Description:
 * \
 */
@Component
@Repository
public class MyBaseMongoRepositoryImpl<T extends BaseModelMO,ID> implements MyBaseMongoRepository<T,ID> {
    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> tClass ;
    //封装后统一的主键字段名
    private String keyFieldName = "fid" ;

    public Class<T> getTClass() {
        if(tClass != null){
            return tClass ;
        }
        try {
            //适用当前，请勿调整 泛型参数顺序 与 实现接口的顺序,当调整时请务必同时调整该函数。
            //取得当前的第一个[实现接口]
            Class<T> firstInterfaceCls = ((Class) this.getClass().getGenericInterfaces()[0]);
            //取得 当前泛型的第一个参数的值
            String tClassName = ((ParameterizedTypeImpl) firstInterfaceCls.getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
            tClass = (Class<T>)Class.forName(tClassName);
        } catch (ClassNotFoundException e) {
            throw new MyMongoException("找不到对应class!");
        }
        return tClass;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> iterable){
        if(iterable == null){
            throw new RuntimeException("传入的集合不能为空!");
        }
        Iterator<S> iter = iterable.iterator();
        List<S> addList = new ArrayList<>();
        List<S> updateList = new ArrayList<>();
        while (iter.hasNext()){
            S s = iter.next();
            if(StringUtils.isBlank(s.getFid())){
                addList.add(s);
            }   else {
                updateList.add(s);
            }
        }
        if(CollectionUtils.isEmpty(addList) == false){
            this.insert(addList);
        }
        if(CollectionUtils.isEmpty(addList) == false){
            // TODO 更新方法
        }
        return Lists.newArrayList(iterable);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(getTClass());
    }

    @Override
    public List<T> findAll(Sort sort) {
        Query query = (sort == null) ? new Query() : new Query().with(sort);
        return mongoTemplate.find(query,getTClass());
    }

    @Override
    public <S extends T> S insert(S s) {
        mongoTemplate.insert(s);
        return s;
    }

    @Override
    public <S extends T> List<S> insert(Iterable<S> iterable) {
        List<S> list = dealGetListFromIterable(iterable,true);
        if(CollectionUtils.isEmpty(list)){
            throw new MyMongoException("待新增集合为空！");
        }
        mongoTemplate.insertAll(list);
        return list;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        //TODO
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        //TODO
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Query query = (pageable == null) ? new Query() : new Query().with(pageable);
        List<T> list = mongoTemplate.find(query,tClass);
        long total = mongoTemplate.count(query,tClass);
        return new PageImpl(list,pageable,total);
    }

    @Override
    public <S extends T> S save(S s) {
        mongoTemplate.save(s);
        return s;
    }

    @Override
    public Optional<T> findById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id,true);
        return Optional.of(mongoTemplate.findById(id,tClass));
    }

    @Override
    public boolean existsById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id,true);
        return mongoTemplate.findById(id,tClass) != null ;
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> iterable) {
        List<ID> list = dealGetListFromIterable(iterable,true);
        Query query = dealGetQueryWithIds(list,true);
        return mongoTemplate.find(query,tClass);
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(),tClass);
    }

    @Override
    public void deleteById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id,true);
        Query query = dealGetQueryWithId(id,true);
        mongoTemplate.remove(query);
    }

    @Override
    public void delete(T t) {
        dealVerifyTNotNull(t,true);
        DeleteResult result = mongoTemplate.remove(t);
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        //List<? extends T> list = dealGetListFromIterable(iterable,false);
        Query query = dealGetQueryWithIdsFromT(iterable,true);
        mongoTemplate.remove(query,tClass);
    }

    @Override
    public void deleteAll() {
        if(1==1){
            throw new MyMongoException("不允许执行全部删除！");
        }
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }


    private boolean dealVerifyTNotNull(T t,boolean exceptionAble){
        if(t == null && exceptionAble){
            throw new MyMongoException("操作的实体类对象不允许为空!");
        }
        return t == null ;
    }
    /**
     * 判断传入的id是否为空
     * @param id
     * @return
     */
    private boolean dealVerifyIdBlank(ID id,boolean exceptionAble){
        boolean flag = id == null || (id instanceof java.lang.String && StringUtils.isBlank((String)id)) ;
        if(flag && exceptionAble){
            throw new MyMongoException("字段fid不允许为空");
        }
        return flag ;
    }

    /**
     * 将Iterable转化为List
     * @param iterable 带判断的迭代器
     * @param exceptionAble 当迭代器为空或转化后的集合为空时，是否抛出异常
     * @param <P>
     * @return
     */
    private  <P> List<P> dealGetListFromIterable(Iterable<P> iterable,boolean exceptionAble){
        if(iterable == null){
            if(exceptionAble){
                throw new MyMongoException("操作集合不允许为空！");
            }   else {
                return new ArrayList<P>();
            }
        }
        List<P> list = Lists.newArrayList(iterable);
        if((CollectionUtils.isEmpty(list)) && exceptionAble){
            throw new MyMongoException("操作集合不允许为空！");
        }
        return list ;
    }

    private Query dealGetQueryWithId(ID id,boolean exceptionAble){
        dealVerifyIdBlank(id,exceptionAble);
        Criteria criteria = new Criteria();
        criteria.where(keyFieldName).is(id);
        return new Query().addCriteria(criteria);
    }

    private Query dealGetQueryWithIds(Iterable<ID> idIters,boolean exceptionAble){
        List<ID> ids = dealGetListFromIterable(idIters,exceptionAble);
        Criteria criteria = new Criteria();
        criteria.where(keyFieldName).in(ids);
        return new Query().addCriteria(criteria);
    }
    private Query dealGetQueryWithIdsFromT(Iterable<? extends T> idIters,boolean nullAble){
        //exceptionAble为true时，则idIters必须要有项
        List<? extends T> tList = dealGetListFromIterable(idIters,nullAble);
        List<String> idList = Lists.newArrayList() ;
        if(CollectionUtils.isEmpty(tList) == false){
            for(T t : tList){
                if(StringUtils.isNotBlank(t.getFid())){
                    idList.add(t.getFid());
                }
            }
        }
        Criteria criteria = new Criteria();
        criteria.where(keyFieldName).in(idList);
        return new Query().addCriteria(criteria);
    }
}
