package com.easy2remember.daomodule.dao.util;

import com.easy2remember.entitymodule.entity.impl.UserTelegramDetails;

import java.util.Optional;

public interface UserTelegramDetailsRepo extends UserAbstractRepo<UserTelegramDetails> {

    Optional<UserTelegramDetails> findByUserId(Long userId);

}
