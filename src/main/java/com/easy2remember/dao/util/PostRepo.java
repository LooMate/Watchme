package com.easy2remember.dao.util;

import com.easy2remember.entity.impl.main.Post;

public interface PostRepo extends PostAbstractRepo<Post> {

    Post[] findAllPostsForToday();

    Post[] findAllByPublisherId(Long pubId);

}
