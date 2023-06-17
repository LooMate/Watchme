package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.impl.TelegramChannelDetails;

public interface TelegramChannelDetailsRepo extends UserAbstractRepo<TelegramChannelDetails>{

    Long[] findAllAdminsOfChannelByChannelId(Long channelId);

    Long findTgChIdDetByUserId(Long userId);

}
