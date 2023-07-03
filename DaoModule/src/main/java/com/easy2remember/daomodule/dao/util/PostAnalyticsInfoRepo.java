package com.easy2remember.daomodule.dao.util;

import com.easy2remember.entitymodule.entity.impl.PostAnalyticsInfo;

public interface PostAnalyticsInfoRepo extends PostAbstractRepo<PostAnalyticsInfo> {

    Long findPostAnalyticsInfoIdByPostId(Long postId);
}
