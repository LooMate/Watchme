package com.easy2remember.dao.util;

import com.easy2remember.entity.impl.details.UserWebDetails;

import java.util.Optional;

public interface UserWebDetailsRepo extends UserAbstractRepo<UserWebDetails> {

    Optional<UserWebDetails> findByUserId(Long userId);

}
