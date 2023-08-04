package com.easy2remember.dao.util;

import com.easy2remember.entity.util.AbstractEntity;

import java.util.Optional;


public interface UserAbstractRepo<T extends AbstractEntity> extends AbstractRepo<T> {

    Optional<T> findByUsername(String name);

}
