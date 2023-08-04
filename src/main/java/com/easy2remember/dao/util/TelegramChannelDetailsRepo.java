package com.easy2remember.dao.util;

import com.easy2remember.entity.impl.details.TelegramChannelDetails;

import java.util.Optional;

public interface TelegramChannelDetailsRepo extends UserAbstractRepo<TelegramChannelDetails> {

    Optional<TelegramChannelDetails> findByUserId(Long userId);

}
