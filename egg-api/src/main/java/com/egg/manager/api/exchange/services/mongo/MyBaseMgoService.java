package com.egg.manager.api.exchange.services.mongo;

import com.egg.manager.persistence.commons.base.exception.MyMongoException;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQryPage;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryPage;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoUpdateBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface MyBaseMgoService<T extends MyBaseModelMgo, ID> {

    /**
     * 查询一条记录
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doInsert(UserAccountEntity loginUser, T t);

    /**
     * 批量插入
     * @param iterables
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    List<T> doInsert(UserAccountEntity loginUser, Iterable<T> iterables);


    /**
     * 更新项(非null字段)
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doUpdateById(UserAccountEntity loginUser, T t);

    /**
     * 更新项(所有字段)
     * @param t
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    T doUpdateAllColById(UserAccountEntity loginUser, T t);

    /**
     * 批量更新
     * (如果没在update设置 最后更新时间，那么会自动在update添加 最后更新人信息)
     * @param loginUser   当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @param updateBean
     * @return
     */
    Long doBatchUpdate(UserAccountEntity loginUser, MongoQueryPage queryBuffer, MyMongoUpdateBean<T> updateBean);

    /**
     * 根据id-伪删除
     * @param id
     * @param loginUser
     * @return
     * @throws MyMongoException
     */
    Long doFakeDeleteById(UserAccountEntity loginUser, ID id) throws MyMongoException;

    /**
     * 根据根据实体类-伪删除
     * @param t
     * @param loginUser
     * @return
     * @throws MyMongoException
     */
    Long doFakeDelete(UserAccountEntity loginUser, T t) throws MyMongoException;

    /**
     * 根据id集合伪删除
     * @param loginUser    当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param iterableList
     * @return
     * @throws MyMongoException
     */
    Long doFakeDeleteByIds(UserAccountEntity loginUser, Iterable<ID> iterableList) throws MyMongoException;

    /**
     * 根据id删除对应项
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param id
     */
    void doDeleteById(UserAccountEntity loginUser, ID id);

    /**
     * 根据项删除
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param t
     */
    void doDelete(UserAccountEntity loginUser, T t);

    /**
     * 根据 项集合 删除
     * @param loginUser    当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param iterableList
     */
    void doDeleteAll(UserAccountEntity loginUser, Iterable<? extends T> iterableList);

    /**
     * 删除所有(禁用)
     * @param loginUser 当前登录用户(必要)
     * @throws MyMongoException
     */
    @Deprecated
    void doDeleteAll(@NotNull UserAccountEntity loginUser) throws MyMongoException;

    /**
     * 根据id查询对应的项
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param id
     * @return
     */
    T doFindById(UserAccountEntity loginUser, ID id);


    /**
     * 查询所有记录
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    List<T> doFindAll(UserAccountEntity loginUser);

    /**
     * 查询所有记录
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param sort
     * @return
     */
    List<T> doFindAll(UserAccountEntity loginUser, Sort sort);

    /**
     * 根据id集合查询所有相应的记录
     * @param loginUser    当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param iterableList
     * @return
     */
    Iterable<T> doFindAllById(UserAccountEntity loginUser, Iterable<ID> iterableList);


    /**
     * 根据封装的bean进行查询
     * @param loginUser   当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    List<T> doFindAll(UserAccountEntity loginUser, MongoQueryPage queryBuffer);

    /**
     * 根据查询条件查询记录并排序
     * @param loginUser   当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @param sort
     * @return
     */
    List<T> doFindAll(UserAccountEntity loginUser, MongoQueryPage queryBuffer, Sort sort);

    /**
     * 分页查询
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param pageBean
     * @return
     */
    MongoQryPage<T> doFindPage(UserAccountEntity loginUser, MongoQryPage<T> pageBean);

    /**
     * 根据封装的MongoQueryBean进行分页查询
     * @param loginUser   当前登录用户
     * @param queryBuffer
     * @return
     */
    MongoQryPage<T> doFindPage(UserAccountEntity loginUser, MongoQueryPage queryBuffer);

    /**
     * 根据条件查询首个项
     * @param loginUser   当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    T doFindOne(UserAccountEntity loginUser, MongoQueryPage queryBuffer);


    /**
     * 统计个数
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @return
     */
    long doCount(UserAccountEntity loginUser);

    /**
     * 统计筛选条件后的数量
     * @param loginUser   当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    long doCount(UserAccountEntity loginUser, MongoQueryPage queryBuffer);

    /**
     * 根据条件，判断是否有项存在
     * @param loginUser   当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param queryBuffer
     * @return
     */
    boolean doExists(UserAccountEntity loginUser, MongoQueryPage queryBuffer);

    /**
     * 判断id是否有对应项
     * @param loginUser 当前登录用户(参数栏非必要，如某些情况确实必要该用户请抛出异常)
     * @param id
     * @return
     */
    boolean doExistsById(UserAccountEntity loginUser, ID id);

}
