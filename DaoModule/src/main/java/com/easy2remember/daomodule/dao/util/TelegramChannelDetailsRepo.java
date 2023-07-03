package com.easy2remember.daomodule.dao.util;

import com.easy2remember.entitymodule.entity.impl.TelegramChannelDetails;

import java.util.Optional;

public interface TelegramChannelDetailsRepo extends UserAbstractRepo<TelegramChannelDetails> {

    Optional<TelegramChannelDetails> findByUserId(Long userId);

}
