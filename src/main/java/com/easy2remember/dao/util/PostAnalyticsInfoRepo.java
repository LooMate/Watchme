package com.easy2remember.dao.util;

import com.easy2remember.entity.impl.details.PostAnalyticsInfo;

public interface PostAnalyticsInfoRepo extends PostAbstractRepo<PostAnalyticsInfo> {

    Long findPostAnalyticsInfoIdByPostId(Long postId);
}
