package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.impl.PostAnalyticsSnap;

public interface PostAnalyticsSnapRepo extends PostAbstractRepo<PostAnalyticsSnap> {

    PostAnalyticsSnap[] findAllPostAnalyticsSnapsByPostId(Long id);

}
