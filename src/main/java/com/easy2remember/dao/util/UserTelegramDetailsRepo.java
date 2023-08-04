package com.easy2remember.dao.util;

import com.easy2remember.entity.impl.details.UserTelegramDetails;

import java.util.Optional;

public interface UserTelegramDetailsRepo extends UserAbstractRepo<UserTelegramDetails> {

    Optional<UserTelegramDetails> findByUserId(Long userId);

}
