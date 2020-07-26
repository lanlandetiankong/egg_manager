package com.egg.manager.mongodb.mservices.serviceimpl;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.MyMongoException;
import com.egg.manager.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.mongo.mo.BaseModelMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public T doInsert(T t, UserAccount loginUser) {
        dealCreateSetLoginUserToMO(t,loginUser);
        return baseRepository.insert(t);
    }

    @Override
    public List<T> doInsert(Iterable<T> iterables,UserAccount loginUser) {
        if(iterables != null){
            for (T t : iterables){
                dealCreateSetLoginUserToMO(t,loginUser);
            }
        }
        return baseRepository.insert(iterables);
    }

    @Override
    public T doSave(T t,UserAccount loginUser) {
        dealUpdateSetLoginUserToMO(t,loginUser);
        return baseRepository.save(t);
    }
    @Override
    public List<T> doSaveAll(Iterable<T> iterables,UserAccount loginUser) {
        if(iterables != null){
            for (T t : iterables){
                dealUpdateSetLoginUserToMO(t,loginUser);
            }
        }
        return baseRepository.saveAll(iterables);
    }


    @Override
    public Integer doFakeDeleteById(ID id,UserAccount loginUser) throws MyMongoException {
        Integer count = 0 ;
        if(id == null){
            throw new MyMongoException("请传入id!");
        }
        Optional<T> optionalT = baseRepository.findById(id);
        T t = optionalT.get();
        if(t == null){
            throw new MyMongoException("不存在的数据!");
        }
        dealUpdateSetLoginUserToMO(t,loginUser);
        t.setStatus(BaseStateEnum.DELETE.getValue());
        baseRepository.save(t);
        return ++count ;
    }

    @Override
    public Integer doFakeDelete(T t,UserAccount loginUser) throws MyMongoException{
        Integer count = 0 ;
        if(t == null){
            throw new MyMongoException("不存在的数据!");
        }
        dealUpdateSetLoginUserToMO(t,loginUser);
        t.setStatus(BaseStateEnum.DELETE.getValue());
        baseRepository.delete(t);
        return ++count ;
    }

    @Override
    public Integer doFakeDeleteByIds(Iterable<ID> iterableList, UserAccount loginUser) throws MyMongoException {
        if(iterableList == null){
            throw new MyMongoException("集合为空!");
        }
        Iterable<T> tList = baseRepository.findAllById(iterableList);
        Integer count = 0 ;
        if(tList != null){
            for (T t : tList){
                dealUpdateSetLoginUserToMO(t,loginUser);
                t.setStatus(BaseStateEnum.DELETE.getValue());
                count ++ ;
            }
        }
        baseRepository.saveAll(tList);
        return count;
    }

    @Override
    public void doDeleteById(ID id,UserAccount loginUser) {
        baseRepository.deleteById(id);
    }

    @Override
    public void doDelete(T t,UserAccount loginUser){
        baseRepository.delete(t);
    }

    @Override
    public void doDeleteAll(Iterable<? extends T> iterableList, UserAccount loginUser) {
        baseRepository.deleteAll(iterableList);
    }

    @Override
    public void doDeleteAll(UserAccount loginUser) throws MyMongoException{
        if(1==1){
            throw new MyMongoException("不允许使用全部删除功能！");
        }
        baseRepository.deleteAll();
    }


    @Override
    public List<T> doFindAll(Example<T> example) {
        return baseRepository.findAll(example);
    }

    @Override
    public List<T> doFindAll(Example<T> example, Sort sort) {
        return baseRepository.findAll(example,sort);
    }

    @Override
    public Page<T> doFindAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public List<T> doFindAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> doFindAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public Optional<T> doFindById(ID id) {
        return baseRepository.findById(id);
    }


    @Override
    public Iterable<T> doFindAllById(Iterable<ID> iterableList) {
        return baseRepository.findAllById(iterableList);
    }


    @Override
    public Optional<T> doFindOne(Example<T> example) {
        return baseRepository.findOne(example);
    }

    @Override
    public Page<T> doFindAll(Example<T> example, Pageable pageable) {
        return baseRepository.findAll(example,pageable);
    }

    @Override
    public long doCount() {
        return baseRepository.count();
    }
    @Override
    public long doCount(Example<T> example) {
        return baseRepository.count(example);
    }
    @Override
    public boolean doExists(Example<T> example) {
        return baseRepository.exists(example);
    }
    @Override
    public boolean doExistsById(ID id) {
        return baseRepository.existsById(id);
    }









    //private methods
    private void dealUpdateSetLoginUserToMO(T t,UserAccount loginUser){
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
    private void dealCreateSetLoginUserToMO(T t,UserAccount loginUser){
        if(t != null && loginUser != null){
            t.setCreateUserId(loginUser.getFid());
            t.setCreateUserNickName(loginUser.getNickName());
            t.setLastModifyerId(loginUser.getFid());
            t.setLastModifyerNickName(loginUser.getNickName());
        }
    }

}
