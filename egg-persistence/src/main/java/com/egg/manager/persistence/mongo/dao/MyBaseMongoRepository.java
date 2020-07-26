package com.egg.manager.persistence.mongo.dao;

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
    <S extends T> List<S> saveAll(Iterable<S> iterable);

    List<T> findAll();

    List<T> findAll(Sort sort);

    <S extends T> S insert(S s);

    <S extends T> List<S> insert(Iterable<S> iterable);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    Page<T> findAll(Pageable pageable);

    <S extends T> S save(S s);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAllById(Iterable<ID> iterable);

    long count();

    void deleteById(ID id);

    void delete(T T);

    void deleteAll(Iterable<? extends T> iterable);

    void deleteAll();

    <S extends T> Optional<S> findOne(Example<S> example);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends T> long count(Example<S> example);

    <S extends T> boolean exists(Example<S> example);
}
