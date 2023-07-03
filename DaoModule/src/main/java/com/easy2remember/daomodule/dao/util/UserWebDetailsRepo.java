package com.easy2remember.daomodule.dao.util;

import com.easy2remember.entitymodule.entity.impl.UserWebDetails;

import java.util.Optional;

public interface UserWebDetailsRepo extends UserAbstractRepo<UserWebDetails> {

    Optional<UserWebDetails> findByUserId(Long userId);

}
