package com.easy2remember.daomodule.dao.util;

import com.easy2remember.entitymodule.entity.util.AbstractEntity;

import java.util.Optional;


public interface UserAbstractRepo<T extends AbstractEntity> extends AbstractRepo<T> {

    Optional<T> findByUsername(String name);

}
