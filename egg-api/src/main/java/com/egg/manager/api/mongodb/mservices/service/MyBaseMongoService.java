package com.egg.manager.api.mongodb.mservices.service;

import com.egg.manager.common.base.exception.MyMongoException;
import com.egg.manager.common.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.common.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.common.override.org.springframework.data.mongodb.core.query.InheritMongoUpdate;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mongo.mo.MyBaseModelMO;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/25
 * \* Time: 0:18
 * \* Description:
 * \
 */
public interface MyBaseMongoService<T extends MyBaseModelMO, ID> {

    /**
     * 查询一条记录
     *
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doInsert(UserAccount loginUser, T t);

    /**
     * 批量插入
     *
     * @param iterables
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    List<T> doInsert(UserAccount loginUser, Iterable<T> iterables);


    /**
     * 更新项(非null字段)
     *
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doUpdateById(UserAccount loginUser, T t);

    /**
     * 更新项(所有字段)
     *
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doUpdateAllColById(UserAccount loginUser, T t);

    /**
     * 批量更新
     * (如果没在update设置 最后更新时间，那么会自动在update添加 最后更新人信息)
     *
     * @param loginUser          当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @param inheritMongoUpdate
     * @return
     */
    Long doBatchUpdate(UserAccount loginUser, MyMongoQueryBuffer queryBuffer, InheritMongoUpdate inheritMongoUpdate);

    /**
     * 根据id-伪删除
     *
     * @param id
     * @param loginUser
     * @return
     * @throws MyMongoException
     */
    Long doFakeDeleteById(UserAccount loginUser, ID id) throws MyMongoException;

    /**
     * 根据根据实体类-伪删除
     *
     * @param t
     * @param loginUser
     * @return
     * @throws MyMongoException
     */
    Long doFakeDelete(UserAccount loginUser, T t) throws MyMongoException;

    /**
     * 根据id集合伪删除
     *
     * @param loginUser    当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param iterableList
     * @return
     * @throws MyMongoException
     */
    Long doFakeDeleteByIds(UserAccount loginUser, Iterable<ID> iterableList) throws MyMongoException;

    /**
     * 根据id删除对应项
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param id
     */
    void doDeleteById(UserAccount loginUser, ID id);

    /**
     * 根据项删除
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param t
     */
    void doDelete(UserAccount loginUser, T t);

    /**
     * 根据 项集合 删除
     *
     * @param loginUser    当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param iterableList
     */
    void doDeleteAll(UserAccount loginUser, Iterable<? extends T> iterableList);

    /**
     * 删除所有(禁用)
     *
     * @param loginUser 当前登录用户(必要)
     */
    @Deprecated
    void doDeleteAll(@NotNull UserAccount loginUser) throws MyMongoException;

    /**
     * 根据id查询对应的项
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param id
     * @return
     */
    T doFindById(UserAccount loginUser, ID id);


    /**
     * 查询所有记录
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    List<T> doFindAll(UserAccount loginUser);

    /**
     * 查询所有记录
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param sort
     * @return
     */
    List<T> doFindAll(UserAccount loginUser, Sort sort);

    /**
     * 根据id集合查询所有相应的记录
     *
     * @param loginUser    当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param iterableList
     * @return
     */
    Iterable<T> doFindAllById(UserAccount loginUser, Iterable<ID> iterableList);



    /**
     * 根据封装的bean进行查询
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    List<T> doFindAll(UserAccount loginUser, MyMongoQueryBuffer queryBuffer);

    /**
     * 根据查询条件查询记录并排序
     *
     * @param loginUser         当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @param sort
     * @return
     */
    List<T> doFindAll(UserAccount loginUser, MyMongoQueryBuffer queryBuffer, Sort sort);

    /**
     * 分页查询
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param pageBean
     * @return
     */
    MyMongoQueryPageBean<T> doFindPage(UserAccount loginUser, MyMongoQueryPageBean<T> pageBean);
    /**
     * 根据封装的MongoQueryBean进行分页查询
     * @param queryBuffer
     * @return
     */
    MyMongoQueryPageBean<T> doFindPage(UserAccount loginUser, MyMongoQueryBuffer queryBuffer);
    /**
     * 根据条件查询首个项
     *
     * @param loginUser         当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    T doFindOne(UserAccount loginUser, MyMongoQueryBuffer queryBuffer);




    /**
     * 统计个数
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    long doCount(UserAccount loginUser);

    /**
     * 统计筛选条件后的数量
     *
     * @param loginUser         当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    long doCount(UserAccount loginUser, MyMongoQueryBuffer queryBuffer);

    /**
     * 根据条件，判断是否有项存在
     *
     * @param loginUser         当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    boolean doExists(UserAccount loginUser, MyMongoQueryBuffer queryBuffer);

    /**
     * 判断id是否有对应项
     *
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param id
     * @return
     */
    boolean doExistsById(UserAccount loginUser, ID id);

}
