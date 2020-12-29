package com.egg.manager.facade.persistence.exchange.db.mongo.repositoryimpl;

import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.commons.base.constant.db.MongoFieldConstant;
import com.egg.manager.facade.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.facade.persistence.commons.base.exception.MyMongoException;
import com.egg.manager.facade.persistence.commons.util.basic.jvm.reflex.MyReflexUtil;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.facade.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import com.google.common.collect.Lists;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Component
@Repository
@Slf4j
public class MyBaseMongoRepositoryImpl<T extends MyBaseModelMgo<ID>, ID> implements MyBaseMongoRepository<T, ID> {
    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> tClass;

    /**
     * 单个更新数量
     */
    private final long singleUpdateMaxSize = 1L;

    public Class<T> getTypeClass() {
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
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.reflexClsNotFound());
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
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.emptyCollection());
        }
        mongoTemplate.insertAll(list);
        return list;
    }

    public <S extends T> S updateById(S s) {
        //验证更新的[文档]不能为空
        dealVerifyTypeNotNull(s, true);
        //验证id不能为空
        dealGetQueryWithId(s.getFid(), true);
        Query query = dealGetQueryWithId(s.getFid(), true);
        //MO转化为更新对象(忽略null字段)
        Update update = MyReflexUtil.getMoUpdateByObjectWithIgnores(s, true);
        mongoTemplate.updateFirst(query, update, getTypeClass());
        return s;
    }

    @Override
    public <S extends T> S updateById(S s, boolean isAllColumn) {
        //验证更新的[文档]不能为空
        dealVerifyTypeNotNull(s, true);
        //验证id不能为空
        dealGetQueryWithId(s.getFid(), true);
        Query query = dealGetQueryWithId(s.getFid(), true);
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = MyReflexUtil.getMoUpdateByObjectWithIgnores(s, !isAllColumn, MongoFieldConstant.FIELD_FID);
        UpdateResult result = mongoTemplate.updateFirst(query, update, getTypeClass());
        if (result.getModifiedCount() != singleUpdateMaxSize) {
            String errmsg = BaseRstMsgConstant.ErrorMsg.updateQuantityDoesNotMatch(singleUpdateMaxSize, result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return s;
    }

    @Override
    public <S extends T> long batchUpdateByIds(Iterable<ID> ids, S s, boolean isAllColumn) {
        //id迭代器不能为空
        dealGetQueryWithIds(ids, true);
        int idSize = Lists.newArrayList(ids).size();
        Query query = dealGetQueryWithIds(ids, true);
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = MyReflexUtil.getMoUpdateByObjectWithIgnores(s, !isAllColumn, MongoFieldConstant.FIELD_FID);
        UpdateResult result = mongoTemplate.updateMulti(query, update, getTypeClass());
        if (result.getModifiedCount() != idSize) {
            String errmsg = BaseRstMsgConstant.ErrorMsg.updateQuantityDoesNotMatch(singleUpdateMaxSize, result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return result.getModifiedCount();
    }

    @Override
    public long batchUpdate(Query query, Update update) {
        UpdateResult result = mongoTemplate.updateMulti(query, update, getTypeClass());
        return result.getModifiedCount();
    }

    @Override
    public <S extends T> S updateStatusById(S s, Short status) {
        //验证更新的[文档]不能为空
        dealVerifyTypeNotNull(s, true);
        //验证id不能为空
        dealGetQueryWithId(s.getFid(), true);
        Query query = dealGetQueryWithId(s.getFid(), true);
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = new Update().set(MongoFieldConstant.FIELD_STATUS, status);
        UpdateResult result = mongoTemplate.updateFirst(query, update, getTypeClass());
        if (result.getModifiedCount() != singleUpdateMaxSize) {
            String errmsg = BaseRstMsgConstant.ErrorMsg.updateQuantityDoesNotMatch(singleUpdateMaxSize, result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return s;
    }

    @Override
    public <S extends T> S logicDeleteById(S s) {
        //验证更新的[文档]不能为空
        dealVerifyTypeNotNull(s, true);
        //验证id不能为空
        dealGetQueryWithId(s.getFid(), true);
        Query query = dealGetQueryWithId(s.getFid(), true);
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = new Update().set(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Open.getValue());
        UpdateResult result = mongoTemplate.updateFirst(query, update, getTypeClass());
        if (result.getModifiedCount() != singleUpdateMaxSize) {
            String errmsg = BaseRstMsgConstant.ErrorMsg.updateQuantityDoesNotMatch(singleUpdateMaxSize, result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return s;
    }

    @Override
    public <U extends EmUserAccountEntity> long batchChangeStatusByIds(Iterable<ID> ids, Short status, U user) {
        //id迭代器 不能为空
        Query query = dealGetQueryWithIds(ids, true);
        int size = Lists.newArrayList(ids).size();
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = new Update().set(MongoFieldConstant.FIELD_STATUS, status);
        UpdateResult result = mongoTemplate.updateMulti(query, update, getTypeClass());
        if (result.getModifiedCount() != size) {
            String errmsg = BaseRstMsgConstant.ErrorMsg.updateQuantityDoesNotMatch(singleUpdateMaxSize, result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return result.getModifiedCount();
    }

    @Override
    public <U extends EmUserAccountEntity> long batchLogicDelete(Iterable<ID> ids, U user) {
        //id迭代器 不能为空
        Query query = dealGetQueryWithIds(ids, true);
        int size = Lists.newArrayList(ids).size();
        //MO转化为更新对象(不忽略null字段,忽略fid)
        Update update = new Update().set(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Open.getValue());
        UpdateResult result = mongoTemplate.updateMulti(query, update, getTypeClass());
        if (result.getModifiedCount() != size) {
            String errmsg = BaseRstMsgConstant.ErrorMsg.deleteQuantityDoesNotMatch(singleUpdateMaxSize, result.getModifiedCount());
            log.error(errmsg);
            throw new MyMongoException(errmsg);
        }
        return result.getModifiedCount();
    }


    @Override
    public <S extends T> S save(S s) {
        dealVerifyTypeNotNull(s, true);
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
        dealVerifyTypeNotNull(t, true);
        DeleteResult result = mongoTemplate.remove(t);
    }

    @Override
    public void batchDelete(Iterable<? extends T> iterable) {
        //List<? extends T> list = dealGetListFromIterable(iterable,false);
        Query query = dealGetQueryWithIdsFromT(iterable, true);
        mongoTemplate.remove(query, getTypeClass());
    }

    @Override
    public void deleteAll() {
        if (1 == 1) {
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.notAllowDeleteAllData());
        }
    }

    @Override
    public Optional<T> findOne(Query query) {
        query = query != null ? query : new Query();
        return Optional.of(mongoTemplate.findOne(query, getTypeClass()));
    }

    @Override
    public Optional<T> findById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id, true);
        return Optional.of(mongoTemplate.findById(id, getTypeClass()));
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> iterable) {
        List<ID> list = dealGetListFromIterable(iterable, true);
        Query query = dealGetQueryWithIds(list, true);
        return mongoTemplate.find(query, getTypeClass());
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(getTypeClass());
    }

    @Override
    public List<T> findAll(Sort sort) {
        Query query = (sort == null) ? new Query() : new Query().with(sort);
        return mongoTemplate.find(query, getTypeClass());
    }


    @Override
    public List<T> findAll(Query query) {
        query = query != null ? query : new Query();
        return mongoTemplate.find(query, getTypeClass());
    }

    @Override
    public List<T> findAll(Query query, Sort sort) {
        query = query != null ? query : new Query();
        if (sort != null) {
            query.with(sort);
        }
        return mongoTemplate.find(query, getTypeClass());
    }

    @Override
    public Page<T> findPage(Pageable pageable) {
        if (pageable == null) {
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.paramCannotEmpty("pageable"));
        }
        Query query = (pageable == null) ? new Query() : new Query().with(pageable);
        long total = mongoTemplate.count(query, getTypeClass());
        List<T> list = (total == 0) ? new ArrayList<T>() : mongoTemplate.find(query, getTypeClass());
        return PageableExecutionUtils.getPage(list, pageable, () -> total);
        //return new PageImpl(list, pageable, total);
    }

    @Override
    public Page<T> findPage(Query query, Pageable pageable) {
        if (pageable == null) {
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.paramCannotEmpty("pageable"));
        }
        query = query != null ? query : new Query();
        query.with(pageable);
        long total = mongoTemplate.count(query, getTypeClass());
        List<T> list = (total == 0) ? new ArrayList<T>() : mongoTemplate.find(query, getTypeClass());
        return PageableExecutionUtils.getPage(list, pageable, () -> total);
    }

    @Override
    public Page<T> findPage(Query query, Sort sort, Pageable pageable) {
        if (pageable == null) {
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.paramCannotEmpty("pageable"));
        }
        query = query != null ? query : new Query();
        if (sort != null) {
            query.with(sort);
        }
        query.with(pageable);
        long total = mongoTemplate.count(query, getTypeClass());
        List<T> list = (total == 0) ? new ArrayList<T>() : mongoTemplate.find(query, getTypeClass());
        return PageableExecutionUtils.getPage(list, pageable, () -> total);
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), getTypeClass());
    }


    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, getTypeClass());
    }

    @Override
    public boolean existsById(ID id) {
        boolean idFlag = dealVerifyIdBlank(id, true);
        return mongoTemplate.findById(id, getTypeClass()) != null;
    }

    @Override
    public boolean exists(Query query) {
        return mongoTemplate.exists(query, getTypeClass());
    }


    private boolean dealVerifyTypeNotNull(T t, boolean exceptionAble) {
        if (t == null && exceptionAble) {
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.emptyOperationObject());
        }
        return t == null;
    }

    /**
     * 判断传入的id是否为空
     * @param id
     * @return
     */
    private boolean dealVerifyIdBlank(ID id, boolean exceptionAble) {
        boolean flag = id == null || (id instanceof java.lang.String && StringUtils.isBlank((String) id));
        if (flag && exceptionAble) {
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.unknowId());
        }
        return flag;
    }

    /**
     * 将Iterable转化为List
     * @param iterable      带判断的迭代器
     * @param exceptionAble 当迭代器为空或转化后的集合为空时，是否抛出异常
     * @param <P>
     * @return
     */
    private <P> List<P> dealGetListFromIterable(Iterable<P> iterable, boolean exceptionAble) {
        if (iterable == null) {
            if (exceptionAble) {
                throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.emptyCollection());
            } else {
                return new ArrayList<P>();
            }
        }
        List<P> list = Lists.newArrayList(iterable);
        if ((CollectionUtils.isEmpty(list)) && exceptionAble) {
            throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.emptyCollection());
        }
        return list;
    }

    /**
     * 判断id是否不为空，并返回一个带有id.eq的Query
     * @param id
     * @param exceptionAble
     * @return
     */
    private Query dealGetQueryWithId(ID id, boolean exceptionAble) {
        dealVerifyIdBlank(id, exceptionAble);
        Criteria criteria = new Criteria(MongoFieldConstant.FIELD_FID).is(id);
        return new Query().addCriteria(criteria);
    }

    /**
     * 批量设置id到in，exceptionAble决定当迭代器为空或长度为0时是否报错
     * @param idIters
     * @param exceptionAble
     * @return
     */
    private Query dealGetQueryWithIds(Iterable<ID> idIters, boolean exceptionAble) {
        List<ID> ids = dealGetListFromIterable(idIters, exceptionAble);
        Criteria idInCriteria = new Criteria(MongoFieldConstant.FIELD_FID).in(ids);
        return new Query().addCriteria(idInCriteria);
    }

    /**
     * 根据实体类对象批量设置id到in
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
                if (dealVerifyIdBlank(t.getFid(), false)) {
                    idList.add(t.getFid());
                } else {
                    throw new MyMongoException(BaseRstMsgConstant.ErrorMsg.emptyOperationObject());
                }
            }
        }
        Criteria criteria = new Criteria(MongoFieldConstant.FIELD_FID).in(idList);
        return new Query().addCriteria(criteria);
    }
}
