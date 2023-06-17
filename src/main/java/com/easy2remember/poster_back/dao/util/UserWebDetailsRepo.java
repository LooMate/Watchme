package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.impl.UserWebDetails;

public interface UserWebDetailsRepo extends UserAbstractRepo<UserWebDetails>{
    Long findUserWebDetIdByUserId(Long userId);
}
