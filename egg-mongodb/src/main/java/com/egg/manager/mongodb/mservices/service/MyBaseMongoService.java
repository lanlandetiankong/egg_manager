package com.egg.manager.mongodb.mservices.service;

import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.exception.MyMongoException;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
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
public interface MyBaseMongoService<T,ID> {




    /**
     * 查询一条记录
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doInsert(T t, UserAccount loginUser);

    /**
     * 批量插入
     * @param iterables
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    List<T> doInsert(Iterable<T> iterables, UserAccount loginUser);


    /**
     * 保存项
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doSave(T t, UserAccount loginUser);

    /**
     * 批量保存
     * @param iterables
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    List<T> doSaveAll(Iterable<T> iterables, UserAccount loginUser);

    /**
     * 根据id-伪删除
     * @param id
     * @param loginUser
     * @return
     * @throws MyMongoException
     */
    Integer doFakeDeleteById(ID id,UserAccount loginUser) throws MyMongoException;

    /**
     * 根据根据实体类-伪删除
     * @param t
     * @param loginUser
     * @return
     * @throws MyMongoException
     */
    Integer doFakeDelete(T t,UserAccount loginUser) throws MyMongoException;

    /**
     * 根据id集合伪删除
     * @param iterableList
     * @param loginUser
     * @return
     * @throws MyMongoException
     */
    Integer doFakeDeleteByIds(Iterable<ID> iterableList, UserAccount loginUser) throws MyMongoException;

    /**
     * 根据id删除对应项
     * @param id
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     */
    void doDeleteById(ID id, UserAccount loginUser);

    /**
     * 根据项删除
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     */
    void doDelete(T t, UserAccount loginUser);

    /**
     * 根据 项集合 删除
     * @param iterableList
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     */
    void doDeleteAll(Iterable<? extends T> iterableList, UserAccount loginUser);

    /**
     * 删除所有(禁用)
     * @param loginUser 当前登录用户(必要)
     */
    @Deprecated
    void doDeleteAll(@NotNull UserAccount loginUser) throws MyMongoException;

    /**
     * 根据id查询对应的项(Optional)
     * @param id
     * @return
     */
    Optional<T> doFindById(ID id);


    /**
     * 查询所有记录
     * @return
     */
    List<T> doFindAll();

    /**
     * 查询所有记录
     * @param sort
     * @return
     */
    List<T> doFindAll(Sort sort);

    /**
     * 根据id集合查询所有相应的记录
     * @param iterableList
     * @return
     */
    Iterable<T> doFindAllById(Iterable<ID> iterableList);


    /**
     * 根据查询条件查询记录
     * @param example
     * @return
     */
    List<T> doFindAll(Example<T> example);

    /**
     * 根据查询条件查询记录并排序
     * @param example
     * @param sort
     * @return
     */
    List<T> doFindAll(Example<T> example, Sort sort);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<T> doFindAll(Pageable pageable);
    /**
     * 根据条件查询首个项
     * @param example
     * @return
     */
    Optional<T> doFindOne(Example<T> example);

    /**
     * 根据条件查询对应项并分页
     * @param example
     * @param pageable
     * @return
     */
    Page<T> doFindAll(Example<T> example, Pageable pageable);
    /**
     * 统计个数
     * @return
     */
    long doCount();
    /**
     * 统计筛选条件后的数量
     * @param example
     * @return
     */
    long doCount(Example<T> example);

    /**
     * 根据条件，判断是否有项存在
     * @param example
     * @return
     */
    boolean doExists(Example<T> example);
    /**
     * 判断id是否有对应项
     * @param id
     * @return
     */
    boolean doExistsById(ID id);

}
