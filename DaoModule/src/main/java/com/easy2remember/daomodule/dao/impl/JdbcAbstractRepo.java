package com.easy2remember.daomodule.dao.impl;

import com.easy2remember.daomodule.dao.util.AbstractRepo;
import com.easy2remember.entitymodule.entity.util.AbstractEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcAbstractRepo<T extends AbstractEntity> implements AbstractRepo<T> {

    protected JdbcTemplate jdbcTemplate;

    public JdbcAbstractRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(T entity) {
        return 0L;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public T[] findAll() {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int updateById(T updated, Long id) {
        return 0;
    }
}
