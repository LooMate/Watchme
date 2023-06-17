package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.util.AbstractEntity;

import java.util.Optional;

public interface AbstractRepo<T extends AbstractEntity> {

    long save(T entity);

    Optional<T> findById(Long id);

    T[] findAll();

    int deleteById(Long id);

    int updateById(T updated, Long id);

}
