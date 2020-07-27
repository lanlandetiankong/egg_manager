package com.egg.manager.persistence.mongo.daoimpl;

import com.egg.manager.common.base.exception.MyMongoException;
import com.egg.manager.common.util.reflex.MyReflexUtil;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.mongo.mo.BaseModelMO;
import com.google.common.collect.Lists;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
@Slf4j
public class MyBaseMongoRepositoryImpl<T extends BaseModelMO<ID>,ID> implements MyBaseMongoRepository<T, ID> {
    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> tClass;
    //封装后统一的主键字段名
    private final String FIELD_NAME_OF_FID = "fid";
    private final String FIELD_NAME_OF_STATUS = "status";

    public Class<T> getTClass() {
        if (tClass != null) {
            return tClass;
        }
        try {
            //适用当前，请勿调整 泛型参数顺序 与 实现接口的顺序,当调整时请务必同时调整该函数。
            //取得当前的第一个[实现接口]
            Class<T> firstInterfaceCls = ((Class) this.getClass().getGenericInterfaces()[0]);
            //取得 当前泛型的第一个参数的值
            String tClassName = ((ParameterizedTypeImpl) firstInterfaceCls.getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
            tClass = (Class<T>) Class.forName(tClassName);
        } catch (ClassNotFoundException e) {
            throw new MyMongoException("找不到对应class!");
        }
        return tClass;
    }


    @Override
    public <S extends T> S insert(S s) {
        mongoTemplate.insert(s);
        return s;
    }

    @Override
    public <S extends T> List<S> batchInsert(Iterable<S> iterable) {
        List<S> list = dealGetListFromIterable(iterable, true);
        if (CollectionUtils.isEmpty(list)) {
            throw new MyMongoException("待新增集合为空！");
        }
        mongoTemplate.insertAll(list);
        return list;
    }

    public <S extends T> S updateById(S s) {
        //验证更新的[文档]不能为空
        dealVerifyTNotNull(s,true);
        //验证id不能为空
        dealGetQueryWithId(s.getFid(),true);
        Query query = dealGetQueryWithId(s.getFid(),true);
        //MO转化为更新对象(忽略null字段)
        Update update = MyReflexUtil.getMOUpdateByObjectWithIgnores(s,true);
        mongoTemplate.updateFirst(query,update,getTClass());
        return s;
    }

    @Override
    public <S extends T> S updateById(S s,boolean isAllColumn) {
        //验证更新的[文档]不能为空
        dealVerifyTNotNull(s,true);
        //验证id不能为空
        dealGetQueryWithId(s.getFid(),true);
        Query query = dealGetQueryWithId(s.getFid(),true);
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = MyReflexUtil.getMOUpdateByObjectWithIgnores(s,!isAllColumn,FIELD_NAME_OF_FID);
        UpdateResult result = mongoTemplate.updateFirst(query,update,getTClass());
        if(result.getModifiedCount() != 1){
            String errmsg = String.format("更新操作数量不匹配，应为%d,实际为%d",1,result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return s;
    }

    @Override
    public <S extends T> long batchUpdateByIds(Iterable<ID> ids,S s,boolean isAllColumn){
        //id迭代器不能为空
        dealGetQueryWithIds(ids,true);
        int size = Lists.newArrayList(ids).size();
        Query query = dealGetQueryWithIds(ids,true);
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = MyReflexUtil.getMOUpdateByObjectWithIgnores(s,!isAllColumn,FIELD_NAME_OF_FID);
        UpdateResult result = mongoTemplate.updateMulti(query,update,getTClass());
        if(result.getModifiedCount() != size){
            String errmsg = String.format("更新操作数量不匹配，应为%d,实际为%d",1,result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return result.getModifiedCount() ;
    }

    @Override
    public <S extends T> S updateStatusById(S s,Short status) {
        //验证更新的[文档]不能为空
        dealVerifyTNotNull(s,true);
        //验证id不能为空
        dealGetQueryWithId(s.getFid(),true);
        Query query = dealGetQueryWithId(s.getFid(),true);
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = new Update().addToSet(FIELD_NAME_OF_STATUS,status);
        UpdateResult result = mongoTemplate.updateFirst(query,update,getTClass());
        if(result.getModifiedCount() != 1){
            String errmsg = String.format("更新操作数量不匹配，应为%d,实际为%d",1,result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return s;
    }

    @Override
    public <U extends UserAccount> long batchChangeStatusByIds(Iterable<ID> ids, Short state,U user){
        //id迭代器 不能为空
        dealGetQueryWithIds(ids,true);
        Query query = dealGetQueryWithIds(ids,true);
        int size = Lists.newArrayList(ids).size();
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = new Update().addToSet(FIELD_NAME_OF_STATUS,state) ;
        UpdateResult result = mongoTemplate.updateMulti(query,update,getTClass());
        if(result.getModifiedCount() != size){
            String errmsg = String.format("更新操作数量不匹配，应为%d,实际为%d",1,result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return result.getModifiedCount() ;
    }




    @Override
    public <S extends T> S save(S s) {
        dealVerifyTNotNull(s, true);
        mongoTemplate.save(s);
        return s;
    }



    @Override
    public void deleteById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id, true);
        Query query = dealGetQueryWithId(id, true);
        mongoTemplate.remove(query);
    }

    @Override
    public void delete(T t) {
        dealVerifyTNotNull(t, true);
        DeleteResult result = mongoTemplate.remove(t);
    }

    @Override
    public void batchDelete(Iterable<? extends T> iterable) {
        //List<? extends T> list = dealGetListFromIterable(iterable,false);
        Query query = dealGetQueryWithIdsFromT(iterable, true);
        mongoTemplate.remove(query, tClass);
    }

    @Override
    public void deleteAll() {
        if (1 == 1) {
            throw new MyMongoException("不允许执行全部删除！");
        }
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public Optional<T> findById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id, true);
        return Optional.of(mongoTemplate.findById(id, tClass));
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> iterable) {
        List<ID> list = dealGetListFromIterable(iterable, true);
        Query query = dealGetQueryWithIds(list, true);
        return mongoTemplate.find(query, tClass);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(getTClass());
    }

    @Override
    public List<T> findAll(Sort sort) {
        Query query = (sort == null) ? new Query() : new Query().with(sort);
        return mongoTemplate.find(query, getTClass());
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
    public Page<T> findPage(Pageable pageable) {
        Query query = (pageable == null) ? new Query() : new Query().with(pageable);
        List<T> list = mongoTemplate.find(query, tClass);
        long total = mongoTemplate.count(query, tClass);
        return new PageImpl(list, pageable, total);
    }

    @Override
    public <S extends T> Page<S> findPage(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), tClass);
    }


    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public boolean existsById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id, true);
        return mongoTemplate.findById(id, tClass) != null;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }


    private boolean dealVerifyTNotNull(T t, boolean exceptionAble) {
        if (t == null && exceptionAble) {
            throw new MyMongoException("操作的实体类对象不允许为空!");
        }
        return t == null;
    }

    /**
     * 判断传入的id是否为空
     *
     * @param id
     * @return
     */
    private boolean dealVerifyIdBlank(ID id, boolean exceptionAble) {
        boolean flag = id == null || (id instanceof java.lang.String && StringUtils.isBlank((String) id));
        if (flag && exceptionAble) {
            throw new MyMongoException("字段fid不允许为空");
        }
        return flag;
    }

    /**
     * 将Iterable转化为List
     *
     * @param iterable      带判断的迭代器
     * @param exceptionAble 当迭代器为空或转化后的集合为空时，是否抛出异常
     * @param <P>
     * @return
     */
    private <P> List<P> dealGetListFromIterable(Iterable<P> iterable, boolean exceptionAble) {
        if (iterable == null) {
            if (exceptionAble) {
                throw new MyMongoException("操作集合不允许为空！");
            } else {
                return new ArrayList<P>();
            }
        }
        List<P> list = Lists.newArrayList(iterable);
        if ((CollectionUtils.isEmpty(list)) && exceptionAble) {
            throw new MyMongoException("操作集合不允许为空！");
        }
        return list;
    }

    /**
     * 判断id是否不为空，并返回一个带有id.eq的Query
     *
     * @param id
     * @param exceptionAble
     * @return
     */
    private Query dealGetQueryWithId(ID id, boolean exceptionAble) {
        dealVerifyIdBlank(id, exceptionAble);
        Criteria criteria = new Criteria();
        criteria.where(FIELD_NAME_OF_FID).is(id);
        Query query = new Query();
        return new Query().addCriteria(criteria);
    }

    /**
     * 批量设置id到in，exceptionAble决定当迭代器为空或长度为0时是否报错
     *
     * @param idIters
     * @param exceptionAble
     * @return
     */
    private Query dealGetQueryWithIds(Iterable<ID> idIters, boolean exceptionAble) {
        List<ID> ids = dealGetListFromIterable(idIters, exceptionAble);
        Criteria criteria = new Criteria();
        criteria.where(FIELD_NAME_OF_FID).in(ids);
        return new Query().addCriteria(criteria);
    }

    /**
     * 根据实体类对象批量设置id到in
     *
     * @param idIters
     * @param nullAble
     * @return
     */
    private Query dealGetQueryWithIdsFromT(Iterable<? extends T> idIters, boolean nullAble) {
        //exceptionAble为true时，则idIters必须要有项
        List<? extends T> tList = dealGetListFromIterable(idIters, nullAble);
        List<ID> idList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(tList) == false) {
            for (T t : tList) {
                if (dealVerifyIdBlank(t.getFid(),false)) {
                    idList.add(t.getFid());
                } else {
                    throw new MyMongoException("存在空对象，无法取得对应id!");
                }
            }
        }
        Criteria criteria = new Criteria();
        criteria.where(FIELD_NAME_OF_FID).in(idList);
        return new Query().addCriteria(criteria);
    }
}
