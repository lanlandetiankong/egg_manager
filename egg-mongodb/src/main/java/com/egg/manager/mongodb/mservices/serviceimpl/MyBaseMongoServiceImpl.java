package com.egg.manager.mongodb.mservices.serviceimpl;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.MyMongoException;
import com.egg.manager.common.base.query.MongoQueryBean;
import com.egg.manager.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.mongo.mo.BaseModelMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/25
 * \* Time: 0:18
 * \* Description:
 * \
 */
public class MyBaseMongoServiceImpl<R extends MyBaseMongoRepository<T, ID>,T extends BaseModelMO,ID> implements MyBaseMongoService<T,ID> {
    @Autowired
    protected R baseRepository;

    @Override
    public T doInsert(UserAccount loginUser,T t) {
        dealCreateSetLoginUserToMO(loginUser,t);
        return baseRepository.insert(t);
    }

    @Override
    public List<T> doInsert(UserAccount loginUser,Iterable<T> iterables) {
        if(iterables != null){
            for (T t : iterables){
                dealCreateSetLoginUserToMO(loginUser,t);
            }
        }
        return baseRepository.batchInsert(iterables);
    }

    @Override
    public T doUpdateById(UserAccount loginUser,T t) {
        dealUpdateSetLoginUserToMO(loginUser,t);
        return baseRepository.updateById(t,false);
    }

    @Override
    public T doUpdateAllColById(UserAccount loginUser,T t) {
        dealUpdateSetLoginUserToMO(loginUser,t);
        return baseRepository.updateById(t,true);
    }
    @Override
    public Long doFakeDeleteById(UserAccount loginUser,ID id) throws MyMongoException {
        Integer count = 0 ;
        if(id == null){
            throw new MyMongoException("请传入id!");
        }
        Optional<T> optionalT = baseRepository.findById(id);
        T t = optionalT.get();
        if(t == null){
            throw new MyMongoException("不存在的数据!");
        }
        dealUpdateSetLoginUserToMO(loginUser,t);
        t.setStatus(BaseStateEnum.DELETE.getValue());
        baseRepository.updateStatusById(t,BaseStateEnum.DELETE.getValue());
        return new Long(++count) ;
    }

    @Override
    public Long doFakeDelete(UserAccount loginUser,T t) throws MyMongoException{
        Integer count = 0 ;
        if(t == null){
            throw new MyMongoException("不存在的数据!");
        }
        dealUpdateSetLoginUserToMO(loginUser,t);
        t.setStatus(BaseStateEnum.DELETE.getValue());
        baseRepository.delete(t);
        return new Long(++count) ;
    }

    @Override
    public Long doFakeDeleteByIds(UserAccount loginUser,Iterable<ID> iterableList) throws MyMongoException {
        if(iterableList == null){
            throw new MyMongoException("集合为空!");
        }
        return baseRepository.batchChangeStatusByIds(iterableList,BaseStateEnum.DELETE.getValue(),loginUser);
    }

    @Override
    public void doDeleteById(UserAccount loginUser,ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public void doDelete(UserAccount loginUser,T t){
        baseRepository.delete(t);
    }

    @Override
    public void doDeleteAll(UserAccount loginUser,Iterable<? extends T> iterableList) {
        baseRepository.batchDelete(iterableList);
    }

    @Override
    public void doDeleteAll(UserAccount loginUser) throws MyMongoException{
        if(1==1){
            throw new MyMongoException("不允许使用全部删除功能！");
        }
        baseRepository.deleteAll();
    }


    @Override
    public List<T> doFindAll(UserAccount loginUser,Query query) {
        query = query != null ? query : new Query() ;
        return baseRepository.findAll(query);
    }

    @Override
    public List<T> doFindAll(UserAccount loginUser,Query query, Sort sort) {
        query = query != null ? query : new Query() ;
        if(sort == null){
            return baseRepository.findAll(query);
        }   else {
            return baseRepository.findAll(query,sort);
        }
    }

    @Override
    public Page<T> doFindPage(UserAccount loginUser,Pageable pageable) {
        return baseRepository.findPage(pageable);
    }

    @Override
    public List<T> doFindAll(UserAccount loginUser) {
        return baseRepository.findAll();
    }
    @Override
    public List<T> doFindAll(UserAccount loginUser,MongoQueryBean queryBean){
        return this.doFindAll(loginUser,queryBean.getQuery(),queryBean.getSort());
    }

    @Override
    public List<T> doFindAll(UserAccount loginUser,Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public Optional<T> doFindById(UserAccount loginUser,ID id) {
        return baseRepository.findById(id);
    }


    @Override
    public Iterable<T> doFindAllById(UserAccount loginUser,Iterable<ID> iterableList) {
        return baseRepository.findAllById(iterableList);
    }


    @Override
    public Optional<T> doFindOne(UserAccount loginUser,Query query) {
        query = query != null ? query : new Query() ;
        return baseRepository.findOne(query);
    }

    @Override
    public Page<T> doFindPage(UserAccount loginUser,Query query, Pageable pageable) {
        query = query != null ? query : new Query() ;
        return baseRepository.findPage(query,pageable);
    }

    @Override
    public long doCount(UserAccount loginUser) {
        return baseRepository.count();
    }
    @Override
    public long doCount(UserAccount loginUser,Query query) {
        query = query != null ? query : new Query() ;
        return baseRepository.count(query);
    }
    @Override
    public boolean doExists(UserAccount loginUser,Query query) {
        query = query != null ? query : new Query() ;
        return baseRepository.exists(query);
    }
    @Override
    public boolean doExistsById(UserAccount loginUser,ID id) {
        return baseRepository.existsById(id);
    }



    @Override
    public Page<T> doFindPage(UserAccount loginUser,MongoQueryBean queryBean){
        return this.doFindPage(loginUser,queryBean.getQuery(),queryBean.getPageable());
    }


    //private methods
    private void dealUpdateSetLoginUserToMO(UserAccount loginUser,T t){
        if(t != null && loginUser != null){
            t.setLastModifyerId(loginUser.getFid());
            t.setLastModifyerNickName(loginUser.getNickName());
        }
    }

    /**
     * 更新时 设置登录用户字段
     * @param t
     * @param loginUser
     */
    private void dealCreateSetLoginUserToMO(UserAccount loginUser,T t){
        if(t != null && loginUser != null){
            t.setCreateUserId(loginUser.getFid());
            t.setCreateUserNickName(loginUser.getNickName());
            t.setLastModifyerId(loginUser.getFid());
            t.setLastModifyerNickName(loginUser.getNickName());
        }
    }

}
