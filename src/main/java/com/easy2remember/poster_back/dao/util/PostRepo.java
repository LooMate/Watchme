package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.impl.Post;

public interface PostRepo extends PostAbstractRepo<Post> {

    Post[] findAllPostsForToday();

}
