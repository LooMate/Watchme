package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.impl.UserTelegramDetails;

public interface UserTelegramDetailsRepo extends UserAbstractRepo<UserTelegramDetails>{

    Long findChatIdByUserTgDetId(Long userTgDetId);

    String findUsernameById(Long userTgDetId);

    Long findUserTgDetByUserId(Long userId);

}
