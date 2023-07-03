package com.easy2remember.daomodule.dao.util;

import com.easy2remember.entitymodule.entity.impl.Post;

public interface PostRepo extends PostAbstractRepo<Post> {

    Post[] findAllPostsForToday();

    Post[] findAllByPublisherId(Long pubId);

}
