package com.egg.manager.api.exchange.servicesimpl.mongodb;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.commons.base.constant.mongodb.MongoModelFieldConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.exception.MyMongoException;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryBean;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoUpdateBean;
import com.egg.manager.persistence.enhance.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.enhance.db.mongo.repository.MyBaseMongoRepository;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.querydsl.QPageRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
public class MyBaseMgoServiceImpl<R extends MyBaseMongoRepository<T, ID>, T extends MyBaseModelMgo, ID> implements MyBaseMgoService<T, ID> {
    @Autowired
    protected R baseRepository;


    @Override
    public T doInsert(UserAccount loginUser, T t) {
        dealCreateSetLoginUserToMO(loginUser, t);
        return baseRepository.insert(t);
    }

    @Override
    public List<T> doInsert(UserAccount loginUser, Iterable<T> iterables) {
        if (iterables != null) {
            for (T t : iterables) {
                dealCreateSetLoginUserToMO(loginUser, t);
            }
        }
        return baseRepository.batchInsert(iterables);
    }

    @Override
    public T doUpdateById(UserAccount loginUser, T t) {
        dealUpdateSetLoginUserToMO(loginUser, t);
        return baseRepository.updateById(t, false);
    }

    @Override
    public T doUpdateAllColById(UserAccount loginUser, T t) {
        dealUpdateSetLoginUserToMO(loginUser, t);
        return baseRepository.updateById(t, true);
    }

    @Override
    public Long doBatchUpdate(UserAccount loginUser, MyMongoQueryBuffer queryBuffer, MyMongoUpdateBean<T> updateBean) {
        Assert.notNull(updateBean, "updateBean不能为空！");
        Assert.notNull(updateBean.getDocument(), "document不能为空！");
        MongoQueryBean queryBean = (queryBuffer == null) ? new MongoQueryBean<T>() : new MongoQueryBean<T>().appendQueryFieldsToQuery(queryBuffer);
        Query query = getQueryByCriteriaList(null, queryBean.getCriteriaList());
        Document document = updateBean.getDocument();
        Update update = Update.fromDocument(document);
        //如果已经在update指定了最后更新时间，则不再往update添加最后更新时间、最后更新人等信息
        if (document.containsKey(MongoModelFieldConstant.FIELD_LASTMODIFIEDDATE) == false) {
            dealSetModifyInfoToUpdate(loginUser, update);
        }
        return baseRepository.batchUpdate(query, update);
    }

    @Override
    public Long doFakeDeleteById(UserAccount loginUser, ID id) throws MyMongoException {
        Integer count = 0;
        if (id == null) {
            throw new MyMongoException("请传入id!");
        }
        Optional<T> optionalT = baseRepository.findById(id);
        T t = optionalT.get();
        if (t == null) {
            throw new MyMongoException("不存在的数据!");
        }
        dealUpdateSetLoginUserToMO(loginUser, t);
        t.setStatus(BaseStateEnum.DELETE.getValue());
        baseRepository.updateStatusById(t, BaseStateEnum.DELETE.getValue());
        return new Long(++count);
    }

    @Override
    public Long doFakeDelete(UserAccount loginUser, T t) throws MyMongoException {
        Integer count = 0;
        if (t == null) {
            throw new MyMongoException("不存在的数据!");
        }
        dealUpdateSetLoginUserToMO(loginUser, t);
        t.setStatus(BaseStateEnum.DELETE.getValue());
        baseRepository.delete(t);
        return new Long(++count);
    }

    @Override
    public Long doFakeDeleteByIds(UserAccount loginUser, Iterable<ID> iterableList) throws MyMongoException {
        if (iterableList == null) {
            throw new MyMongoException("集合为空!");
        }
        return baseRepository.batchChangeStatusByIds(iterableList, BaseStateEnum.DELETE.getValue(), loginUser);
    }

    @Override
    public void doDeleteById(UserAccount loginUser, ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public void doDelete(UserAccount loginUser, T t) {
        baseRepository.delete(t);
    }

    @Override
    public void doDeleteAll(UserAccount loginUser, Iterable<? extends T> iterableList) {
        baseRepository.batchDelete(iterableList);
    }

    @Override
    public void doDeleteAll(UserAccount loginUser) throws MyMongoException {
        if (1 == 1) {
            throw new MyMongoException("不允许使用全部删除功能！");
        }
        baseRepository.deleteAll();
    }


    @Override
    public List<T> doFindAll(UserAccount loginUser, MyMongoQueryBuffer queryBuffer, Sort sort) {
        MongoQueryBean queryBean = (queryBuffer == null) ? new MongoQueryBean<T>() : new MongoQueryBean<T>().appendQueryFieldsToQuery(queryBuffer);
        Query query = getQueryByCriteriaList(null, queryBean.getCriteriaList());
        return doFindAll(loginUser, query, sort);
    }

    private List<T> doFindAll(UserAccount loginUser, Query query, Sort sort) {
        query = query != null ? query : new Query();
        if (sort == null) {
            return baseRepository.findAll(query);
        } else {
            return baseRepository.findAll(query, sort);
        }
    }


    @Override
    public List<T> doFindAll(UserAccount loginUser) {
        return baseRepository.findAll();
    }

    @Override
    public List<T> doFindAll(UserAccount loginUser, MyMongoQueryBuffer queryBuffer) {
        return this.doFindAll(loginUser, queryBuffer, null);
    }

    @Override
    public List<T> doFindAll(UserAccount loginUser, Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public T doFindById(UserAccount loginUser, ID id) {
        return baseRepository.findById(id).get();
    }


    @Override
    public Iterable<T> doFindAllById(UserAccount loginUser, Iterable<ID> iterableList) {
        return baseRepository.findAllById(iterableList);
    }


    @Override
    public T doFindOne(UserAccount loginUser, MyMongoQueryBuffer queryBuffer) {
        MongoQueryBean queryBean = (queryBuffer == null) ? new MongoQueryBean<T>() : new MongoQueryBean<T>().appendQueryFieldsToQuery(queryBuffer);
        Query query = getQueryByCriteriaList(null, queryBean.getCriteriaList());
        return baseRepository.findOne(query).get();
    }


    @Override
    public long doCount(UserAccount loginUser) {
        return baseRepository.count();
    }

    @Override
    public long doCount(UserAccount loginUser, MyMongoQueryBuffer queryBuffer) {
        MongoQueryBean queryBean = (queryBuffer == null) ? new MongoQueryBean<T>() : new MongoQueryBean<T>().appendQueryFieldsToQuery(queryBuffer);
        Query query = getQueryByCriteriaList(null, queryBean.getCriteriaList());
        return baseRepository.count(query);
    }

    @Override
    public boolean doExists(UserAccount loginUser, MyMongoQueryBuffer queryBuffer) {
        MongoQueryBean queryBean = (queryBuffer == null) ? new MongoQueryBean<T>() : new MongoQueryBean<T>().appendQueryFieldsToQuery(queryBuffer);
        Query query = getQueryByCriteriaList(null, queryBean.getCriteriaList());
        return baseRepository.exists(query);
    }

    @Override
    public boolean doExistsById(UserAccount loginUser, ID id) {
        return baseRepository.existsById(id);
    }


    @Override
    public MyMongoQueryPageBean<T> doFindPage(UserAccount loginUser, MyMongoQueryBuffer queryBuffer) {
        MongoQueryBean queryBean = (queryBuffer == null) ? new MongoQueryBean<T>() : new MongoQueryBean<T>().appendQueryFieldsToQuery(queryBuffer);
        Query query = getQueryByCriteriaList(null, queryBean.getCriteriaList());
        QPageRequest mPageFromBean = MongoQueryBean.<T>getMgPageFromBean(queryBuffer.getPageBean());
        Page<T> page = baseRepository.findPage(query, mPageFromBean);
        return MongoQueryBean.getPageBeanFromPage(page);
    }

    @Override
    public MyMongoQueryPageBean<T> doFindPage(UserAccount loginUser, MyMongoQueryPageBean<T> pageBean) {
        return MongoQueryBean.getPageBeanFromPage(baseRepository.findPage(MongoQueryBean.getMgPageFromBean(pageBean)));
    }


    private void dealUpdateSetLoginUserToMO(UserAccount loginUser, T t) {
        if (t != null && loginUser != null) {
            t.setLastModifyerId(loginUser.getFid());
            t.setLastModifyerNickName(loginUser.getNickName());
        }
    }

    private void dealSetModifyInfoToUpdate(UserAccount loginUser, Update update) {
        if (loginUser != null) {
            update.set(MongoModelFieldConstant.FIELD_LASTMODIFYERID, loginUser.getFid());
            update.set(MongoModelFieldConstant.FIELD_LASTMODIFYERNICKNAME, loginUser.getNickName());
            update.set(MongoModelFieldConstant.FIELD_LASTMODIFIEDDATE, new Date());
        }
    }

    /**
     * 更新时 设置登录用户字段
     * @param t
     * @param loginUser
     */
    private void dealCreateSetLoginUserToMO(UserAccount loginUser, T t) {
        if (t != null && loginUser != null) {
            t.setCreateUserId(loginUser.getFid());
            t.setCreateUserNickName(loginUser.getNickName());
            t.setLastModifyerId(loginUser.getFid());
            t.setLastModifyerNickName(loginUser.getNickName());
        }
    }


    private Query getQueryByCriteriaList(Query query, List<Criteria> criteriaList) {
        query = (query != null) ? query : new Query();
        if (CollectionUtils.isEmpty(criteriaList)) {
            return query;
        }
        for (Criteria criteria : criteriaList) {
            query.addCriteria(criteria);
        }
        return query;
    }
}
