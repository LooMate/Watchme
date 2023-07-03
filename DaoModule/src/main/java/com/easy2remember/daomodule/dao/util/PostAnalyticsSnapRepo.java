package com.easy2remember.daomodule.dao.util;

import com.easy2remember.entitymodule.entity.impl.PostAnalyticsSnap;

import java.util.List;

public interface PostAnalyticsSnapRepo extends PostAbstractRepo<PostAnalyticsSnap> {

    /**
     * findAllPostAnalyticsSnapsByPostId finds "snaps" with information about post at certain point of a time
     *
     * @return PostAnalyticsSnap[] returns snaps for the moment of starting collecting data
     */
    PostAnalyticsSnap[] findAllPostAnalyticsSnapsByPostId(List<Long> pasIds);

}
