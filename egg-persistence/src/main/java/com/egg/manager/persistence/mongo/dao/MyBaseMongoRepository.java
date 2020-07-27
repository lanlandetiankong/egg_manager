package com.egg.manager.persistence.mongo.dao;

import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mongo.mo.BaseModelMO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
public interface MyBaseMongoRepository<T extends BaseModelMO, ID> {
    /**
     * 插入一个[文档]
     *
     * @param s
     * @param <S>
     * @return
     */
    <S extends T> S insert(S s);

    /**
     * 批量插入[文档]
     *
     * @param iterable
     * @param <S>
     * @return
     */
    <S extends T> List<S> batchInsert(Iterable<S> iterable);



    /**
     * 保存[文档]
     *
     * @param s
     * @param <S>
     * @return
     */
    <S extends T> S save(S s);

    /**
     * 根据id更新指定[文档]
     * @param s
     * @param isAllColumn 是否更新所有字段
     * @param <S>
     * @return
     */
    <S extends T> S updateById(S s,boolean isAllColumn);

    /**
     * 批量根据id更新指定[文档]
     * @param ids
     * @param s
     * @param isAllColumn 是否更新所有字段
     * @param <S>
     * @return
     */
    <S extends T> long batchUpdateByIds(Iterable<ID> ids,S s,boolean isAllColumn);


    /**
     * 更新[文档]的Status
     * @param s
     * @param status 修改值
     * @param <S>
     * @return
     */
    <S extends T> S updateStatusById(S s,Short status);

    /**
     * 批量更新[文档]的Status
     * @param ids 要更新的文档ids
     * @param state 修改值
     * @return
     */
    <U extends UserAccount> long batchChangeStatusByIds(Iterable<ID> ids, Short state,U user);


    /**
     * 根据id删除[文档]
     *
     * @param id
     */
    void deleteById(ID id);

    /**
     * 根据MO删除[文档]
     * @param T
     */
    void delete(T T);

    /**
     * 根据MO集合批量删除[文档]
     * @param iterable
     */
    void batchDelete(Iterable<? extends T> iterable);

    /**
     * 删除所有[文档]
     */
    @Deprecated
    void deleteAll();

    /**
     * 根据Example查询一个[文档]
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> Optional<S> findOne(Example<S> example);

    /**
     * 根据id查询对应[文档]，返回Optional
     * @param id
     * @return
     */
    Optional<T> findById(ID id);

    /**
     * 根据id集合查询对应[文档]集合
     * @param iterable
     * @return
     */
    Iterable<T> findAllById(Iterable<ID> iterable);

    /**
     * 查询所有[文档]
     * @return
     */
    List<T> findAll();

    /**
     * 查询所有[文档]并排序
     * @param sort
     * @return
     */
    List<T> findAll(Sort sort);

    /**
     * 根据条件查询[文档]集合
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> List<S> findAll(Example<S> example);

    /**
     * 根据条件查询[文档]集合并排序
     * @param example
     * @param sort
     * @param <S>
     * @return
     */
    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    /**
     * 根据条件分页查询[文档]
     * @param example
     * @param pageable
     * @param <S>
     * @return
     */
    <S extends T> Page<S> findPage(Example<S> example, Pageable pageable);

    /**
     * 分页查询[文档]
     * @param pageable
     * @return
     */
    Page<T> findPage(Pageable pageable);

    /**
     * 统计该集合的[文档]总数
     * @return
     */
    long count();

    /**
     * 统计筛选条件后的[文档]总数
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> long count(Example<S> example);

    /**
     * 判断id是否存在
     * @param id
     * @return
     */
    boolean existsById(ID id);

    /**
     * 根据条件判断是否存在
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> boolean exists(Example<S> example);
}