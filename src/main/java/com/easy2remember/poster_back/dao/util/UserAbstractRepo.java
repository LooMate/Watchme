package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.util.AbstractEntity;

import java.util.Optional;

public interface UserAbstractRepo<T extends AbstractEntity> extends AbstractRepo<T>  {

    Optional<T> findByName(String name);

}
